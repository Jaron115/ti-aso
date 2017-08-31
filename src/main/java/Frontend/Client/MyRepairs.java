package Frontend.Client;

import Backend.*;
import Frontend.Menu.ClientMenuPanel;
import Frontend.Menu.EmployeeMenuPanel;
import Frontend.Menu.MainMenu;
import com.vaadin.data.HasValue;
import com.vaadin.navigator.View;
import com.vaadin.server.Page;
import com.vaadin.ui.*;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Created by Jaron on 25.08.2017.
 */
public class MyRepairs extends VerticalLayout implements View {

    private MainSystem mainSystem = new MainSystem();

    public MyRepairs(){

        //Grid Layout
        GridLayout grid = new GridLayout(3, 1);
        grid.setWidth("100%");
        grid.setColumnExpandRatio(0,1);
        grid.setColumnExpandRatio(1,3);
        grid.setColumnExpandRatio(2,1);

        List<Repair> repairData = null;

        try {
            repairData = mainSystem.getRepairDataFromDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        assert repairData != null;

        //region --------------------START MY REPAIR - TAB 1------------------------

        //Table - my repairs

        Grid<Repair> repairDataGrid = new Grid<>();

        repairDataGrid.setSizeFull();
        repairDataGrid.addStyleName("dataTable");
        repairDataGrid.setHeight("100%");

        repairDataGrid.addColumn(Repair::getServiceName).setCaption("Usługa").setResizable(false);
        repairDataGrid.addColumn(Repair::getCarData).setCaption("Samochód").setResizable(false);
        repairDataGrid.addColumn(Repair::getInstitutionName).setCaption("Placówka").setResizable(false);
        repairDataGrid.addColumn(Repair::getDate).setCaption("Data").setResizable(false);
        repairDataGrid.addColumn(Repair::getStatus).setCaption("Status").setResizable(false);

        repairDataGrid.setItems(repairData);

        //endregion --------------------END MY REPAIR - TAB 1------------------------

        //region --------------------START MY REPAIR - TAB 2------------------------

        //Add repair - my repairs


        //ComboBox samochody
        List<Car> carData = null;

        try {
            carData = mainSystem.getCarDataFromDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        assert carData != null;

        //ComboBox Cars
        ComboBox<Car> carComboBoxAddRepair = new ComboBox<>("Wybierz samochód");

        carComboBoxAddRepair.setItemCaptionGenerator(Car::carData);
        carComboBoxAddRepair.setSizeFull();

        carComboBoxAddRepair.setItems(carData);


        //ComboBox uslugi
        List<Service> serviceData = null;

        try {
            serviceData = mainSystem.getServiceDataFromDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        assert serviceData != null;

        //ComboBox Cars
        ComboBox<Service> serviceComboBoxAddRepair = new ComboBox<>("Wybierz usługę");

        serviceComboBoxAddRepair.setItemCaptionGenerator(Service::getName);
        serviceComboBoxAddRepair.setSizeFull();

        serviceComboBoxAddRepair.setItems(serviceData);


        //ComboBox placowki
        List<Institution> institutionData = null;

        try {
            institutionData = mainSystem.getInstitutionDataFromDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        assert institutionData != null;

        //ComboBox Cars
        ComboBox<Institution> institutionComboBoxAddRepair = new ComboBox<>("Wybierz placówkę");

        institutionComboBoxAddRepair.setItemCaptionGenerator(Institution::getName);
        institutionComboBoxAddRepair.setSizeFull();

        institutionComboBoxAddRepair.setItems(institutionData);


        //UI Elements - Button
        Button repairButtonAdd = new Button("Dodaj naprawe");


        //Add Horizontal layout - inputs

        //Row 1
        HorizontalLayout HLayoutInputsRow1 = new HorizontalLayout();

        HLayoutInputsRow1.addComponent(carComboBoxAddRepair);
        HLayoutInputsRow1.addComponent(serviceComboBoxAddRepair);

        HLayoutInputsRow1.setWidth("100%");

        //Row 2
        HorizontalLayout HLayoutInputsRow2 = new HorizontalLayout();

        HLayoutInputsRow2.addComponent(institutionComboBoxAddRepair);

        HLayoutInputsRow2.setWidth("100%");

        //Add Horizontal layout - button
        HorizontalLayout HLayoutButton = new HorizontalLayout();

        HLayoutButton.addComponent(repairButtonAdd);

        HLayoutButton.setWidth("100%");
        HLayoutButton.setHeight("100%");
        HLayoutButton.setComponentAlignment(repairButtonAdd, Alignment.MIDDLE_CENTER);


        //Form Layout
        FormLayout formLayout = new FormLayout(HLayoutInputsRow1, HLayoutInputsRow2, HLayoutButton);


        //Listeners
        repairButtonAdd.addClickListener((Button.ClickListener) clickEvent -> {

            try {
                mainSystem.clientRepair(
                        serviceComboBoxAddRepair.getValue().getId(),
                        carComboBoxAddRepair.getValue().getId(),
                        institutionComboBoxAddRepair.getValue().getId()
                );

                Notification.show("Naprawa została dodana");

                Page.getCurrent().reload();

            } catch (SQLException e) {
                e.printStackTrace();
            } catch (NullPointerException e){
                Notification.show("Wybierz wszystkie dane");
            }
        });

        //endregion --------------------END MY REPAIR - TAB 2------------------------


        //region --------------------START MY REPAIR - TAB 4------------------------

        //Delete repair - my repair

        List<Repair> repairDataDelete = null;

        try {
            repairDataDelete = mainSystem.getDeletableRepairDataFromDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        assert repairDataDelete != null;

        //ComboBox
        ComboBox<Repair> repairComboBoxDelete = new ComboBox<>("Wybierz naprawę");

        repairComboBoxDelete.setItemCaptionGenerator(Repair::getRepairData);
        repairComboBoxDelete.setSizeFull();

        repairComboBoxDelete.setItems(repairDataDelete);

        Button carButtonDelete = new Button("Usuń samochód");

        //Row 1
        HorizontalLayout HLayoutInputsRow1Delete = new HorizontalLayout();

        HLayoutInputsRow1Delete.addComponent(repairComboBoxDelete);

        HLayoutInputsRow1Delete.setWidth("100%");

        //Add Horizontal layout - button
        HorizontalLayout HLayoutButtonDelete = new HorizontalLayout();

        HLayoutButtonDelete.addComponent(carButtonDelete);

        HLayoutButtonDelete.setWidth("100%");
        HLayoutButtonDelete.setHeight("100%");
        HLayoutButtonDelete.setComponentAlignment(carButtonDelete, Alignment.MIDDLE_CENTER);

        //Form Layout
        FormLayout formLayoutDelete = new FormLayout(HLayoutInputsRow1Delete, HLayoutButtonDelete);

        carButtonDelete.addClickListener((Button.ClickListener) clickEvent -> {


            try {
                Repair element = repairComboBoxDelete.getValue();

                mainSystem.clientRepairDelete(
                        element.getId()
                );

                Notification.show("Samochód usunięty");

                Page.getCurrent().reload();
            } catch (NullPointerException e){
                Notification.show("Wybierz samochód");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        //endregion --------------------END MY CAR - TAB 4------------------------

        //Tabs - my repairs view
        TabSheet carsTabs = new TabSheet();

        carsTabs.setWidth("100%");
        carsTabs.addStyleName("tabsHeader");

        carsTabs.addTab(repairDataGrid).setCaption("Moje naprawy");
        carsTabs.addTab(formLayout).setCaption("Dodaj naprawe");
        carsTabs.addTab(formLayoutDelete).setCaption("Anuluj naprawe");

        //Panel - my repairs
        Panel institutionListPanel = new Panel("Moje naprawy", carsTabs);
        institutionListPanel.setWidth("100%");
        institutionListPanel.setStyleName("dataListHolder");



        //Panel - guest menu
        MainMenu mainMenu = new MainMenu();
        VerticalLayout menuLayout = mainMenu.MainMenuLayout();
        menuLayout.setSpacing(false);
        Panel mainMenuPanel = new Panel("Menu główne", menuLayout);
        mainMenuPanel.setWidth("100%");
        mainMenuPanel.setStyleName("mainMenuPanel");

        //Panel - client menu
        ClientMenuPanel clientMenu = new ClientMenuPanel();
        VerticalLayout menuLayoutClient = clientMenu.ClientMenuLayout();
        menuLayoutClient.setSpacing(false);
        Panel clientMenuPanel = new Panel("Panel klienta", menuLayoutClient);
        clientMenuPanel.setWidth("100%");
        clientMenuPanel.setStyleName("clientMenuPanel");

        //Panel - employee menu
        EmployeeMenuPanel employeeMenu = new EmployeeMenuPanel();
        VerticalLayout menuLayoutEmployee = employeeMenu.EmployeenMenuLayout();
        menuLayoutEmployee.setSpacing(false);
        Panel employeeMenuPanel = new Panel("Panel pracownika", menuLayoutEmployee);
        employeeMenuPanel.setWidth("100%");
        employeeMenuPanel.setStyleName("employeeMenuPanel");


        grid.addComponent(mainMenuPanel);
        grid.addComponent(institutionListPanel);

        if(Objects.equals(MainSystem.getUserType(), "client")){
            grid.addComponent(clientMenuPanel);
        }
        else if(Objects.equals(MainSystem.getUserType(), "employee")){
            grid.addComponent(employeeMenuPanel);
        }

        grid.setSpacing(true);
        grid.setMargin(true);

        //Add Components
        addComponent(grid);
        setComponentAlignment(grid, Alignment.TOP_LEFT);
        setHeight("100%");
        setWidth("100%");

//        if(MainSystem.getUserID() == 0 || !MainSystem.getUserType().equals("client")){
//            UI.getCurrent().getNavigator().navigateTo("clientLogin");
//        }
    }

}
