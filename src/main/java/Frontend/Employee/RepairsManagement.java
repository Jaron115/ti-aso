package Frontend.Employee;

import Backend.Institution;
import Backend.MainSystem;
import Backend.Repair;
import Backend.Service;
import Frontend.Menu.ClientMenuPanel;
import Frontend.Menu.EmployeeMenuPanel;
import Frontend.Menu.MainMenu;
import com.vaadin.data.HasValue;
import com.vaadin.navigator.View;
import com.vaadin.server.Page;
import com.vaadin.ui.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

/**
 * Created by Jaron on 25.08.2017.
 */
public class RepairsManagement extends VerticalLayout implements View {

    private MainSystem mainSystem = new MainSystem();

    public RepairsManagement(){

        //Grid Layout
        GridLayout grid = new GridLayout(3, 1);
        grid.setWidth("100%");
        grid.setColumnExpandRatio(0,1);
        grid.setColumnExpandRatio(1,3);
        grid.setColumnExpandRatio(2,1);

        List<Repair> repairsData = null;

        try {
            repairsData = mainSystem.getRepairDataFromDatabaseAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        assert repairsData != null;

        //region --------------------START REPAIRS MANAGEMENT - TAB 1------------------------

        //Table

        Grid<Repair> repairDataGrid = new Grid<>();

        repairDataGrid.setSizeFull();
        repairDataGrid.addStyleName("dataTable");
        repairDataGrid.setHeight("100%");

        repairDataGrid.addColumn(Repair::getClientData).setCaption("Imie i nazwisko").setResizable(false);
        repairDataGrid.addColumn(Repair::getServiceName).setCaption("Usługa").setResizable(false);
        repairDataGrid.addColumn(Repair::getCarData).setCaption("Samochód").setResizable(false);
        repairDataGrid.addColumn(Repair::getInstitutionName).setCaption("Placówka").setResizable(false);
        repairDataGrid.addColumn(Repair::getDate).setCaption("Data").setResizable(false);
        repairDataGrid.addColumn(Repair::getStatus).setCaption("Status").setResizable(false);

        repairDataGrid.setItems(repairsData);

        //endregion --------------------END REPAIR MANAGEMENT - TAB 1------------------------

        //region --------------------START REPAIRS MANAGEMENT - TAB 2------------------------

        //Update repair - management repair

        //ComboBox
        ComboBox<Repair> repairComboBoxUpdate = new ComboBox<>("Wybierz naprawę");

        repairComboBoxUpdate.setItemCaptionGenerator(Repair::getRepairDataWithClient);
        repairComboBoxUpdate.setSizeFull();

        repairComboBoxUpdate.setItems(repairsData);

        //UI Elements
        ComboBox<String> repairStatusUpdate = new ComboBox<>("Wybierz status");

        repairStatusUpdate.setSizeFull();

        repairStatusUpdate.setItems("Oczekiwanie", "W trakcie", "Zakończone");


        //UI Elements - Button
        Button repairButtonUpdate = new Button("Aktualizuj naprawe");


        //Add Horizontal layout - inputs

        //Row 1
        HorizontalLayout HLayoutInputsRow1Update = new HorizontalLayout();

        HLayoutInputsRow1Update.addComponent(repairComboBoxUpdate);

        HLayoutInputsRow1Update.setWidth("100%");

        //Row 2
        HorizontalLayout HLayoutInputsRow2Update = new HorizontalLayout();

        HLayoutInputsRow2Update.addComponent(repairStatusUpdate);

        HLayoutInputsRow2Update.setWidth("100%");

        //Add Horizontal layout - button
        HorizontalLayout HLayoutButtonUpdate = new HorizontalLayout();

        HLayoutButtonUpdate.addComponent(repairButtonUpdate);

        HLayoutButtonUpdate.setWidth("100%");
        HLayoutButtonUpdate.setHeight("100%");
        HLayoutButtonUpdate.setComponentAlignment(repairButtonUpdate, Alignment.MIDDLE_CENTER);


        //Form Layout
        FormLayout formLayoutUpdate = new FormLayout(HLayoutInputsRow1Update, HLayoutInputsRow2Update, HLayoutButtonUpdate);


        //Styles

        //Listeners
        repairButtonUpdate.addClickListener((Button.ClickListener) clickEvent -> {
            Repair element = repairComboBoxUpdate.getValue();

            try {

                    mainSystem.repairUpdate(
                            element.getId(),
                            repairStatusUpdate.getValue()
                    );

                    Notification.show("Usługa zaktualizowana");

                    Page.getCurrent().reload();
            } catch (NullPointerException e){
                Notification.show("Wybierz usługę i status");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        repairComboBoxUpdate.addValueChangeListener((HasValue.ValueChangeEvent<Repair> event) -> {
            Repair element = repairComboBoxUpdate.getValue();

            repairStatusUpdate.setValue(element.getStatus());

        });

        //endregion --------------------END REPAIR MANAGEMENT - TAB 2------------------------

        //region --------------------START REPAIRS MANAGEMENT - TAB 3------------------------

        //Delete repair - management repair

        //ComboBox
        ComboBox<Repair> repairComboBoxDelete = new ComboBox<>("Wybierz naprawe");

        repairComboBoxDelete.setItemCaptionGenerator(Repair::getRepairDataWithClient);
        repairComboBoxDelete.setSizeFull();

        repairComboBoxDelete.setItems(repairsData);

        Button repairButtonDelete = new Button("Usuń Naprawę");

        //Row 1
        HorizontalLayout HLayoutInputsRow1Delete = new HorizontalLayout();

        HLayoutInputsRow1Delete.addComponent(repairComboBoxDelete);

        HLayoutInputsRow1Delete.setWidth("100%");

        //Add Horizontal layout - button
        HorizontalLayout HLayoutButtonDelete = new HorizontalLayout();

        HLayoutButtonDelete.addComponent(repairButtonDelete);

        HLayoutButtonDelete.setWidth("100%");
        HLayoutButtonDelete.setHeight("100%");
        HLayoutButtonDelete.setComponentAlignment(repairButtonDelete, Alignment.MIDDLE_CENTER);

        //Form Layout
        FormLayout formLayoutDelete = new FormLayout(HLayoutInputsRow1Delete, HLayoutButtonDelete);

        repairButtonDelete.addClickListener((Button.ClickListener) clickEvent -> {
            Repair element = repairComboBoxDelete.getValue();

            try {
                mainSystem.repairDelete(
                        element.getId()
                );

                Notification.show("Naprawa usunięta");

                Page.getCurrent().reload();
            } catch (NullPointerException e){
                Notification.show("Wybierz naprawę");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        //endregion --------------------END REPAIRS MANAGEMENT - TAB 3------------------------

        //Tabs - my repairs view
        TabSheet repairTabs = new TabSheet();

        repairTabs.setWidth("100%");
        repairTabs.addStyleName("tabsHeader");

        repairTabs.addTab(repairDataGrid).setCaption("Lista napraw");
        repairTabs.addTab(formLayoutUpdate).setCaption("Aktualizacja naprawy");
        repairTabs.addTab(formLayoutDelete).setCaption("Usunięcie naprawy");

        //Panel - my repairs
        Panel serviceListPanel = new Panel("Zarządzanie naprawami", repairTabs);
        serviceListPanel.setWidth("100%");
        serviceListPanel.setStyleName("dataListHolder");



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
        grid.addComponent(serviceListPanel);

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
