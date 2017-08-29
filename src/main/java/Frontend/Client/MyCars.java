package Frontend.Client;

import Backend.Car;
import Backend.Institution;
import Backend.MainSystem;
import Frontend.Menu.ClientMenuPanel;
import Frontend.Menu.EmployeeMenuPanel;
import Frontend.Menu.MainMenu;
import com.vaadin.navigator.View;
import com.vaadin.server.Page;
import com.vaadin.ui.*;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

/**
 * Created by Jaron on 25.08.2017.
 */
public class MyCars extends VerticalLayout implements View{

    private MainSystem mainSystem = new MainSystem();

    public MyCars(){

        //Grid Layout
        GridLayout grid = new GridLayout(3, 1);
        grid.setWidth("100%");
        grid.setColumnExpandRatio(0,1);
        grid.setColumnExpandRatio(1,3);
        grid.setColumnExpandRatio(2,1);

        //region --------------------START MY REPAIRS - TAB 1------------------------

        //Table - my cars
        List<Car> carData = null;
        Grid<Car> carDataGrid;
        try {
            carData = mainSystem.getCarDataFromDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        carDataGrid = new Grid<>();

        carDataGrid.setSizeFull();
        carDataGrid.addStyleName("dataTable");
        carDataGrid.setHeight("100%");

        carDataGrid.addColumn(Car::getBrand).setCaption("Marka").setResizable(false);
        carDataGrid.addColumn(Car::getModel).setCaption("Model").setResizable(false);
        carDataGrid.addColumn(Car::getYear).setCaption("Rok produkcji").setResizable(false);
        carDataGrid.addColumn(Car::getColor).setCaption("Kolor").setResizable(false);

        assert carData != null;
        carDataGrid.setItems(carData);

        //endregion --------------------END MY REPAIRS - TAB 1------------------------

        //region --------------------START MY REPAIRS - TAB 2------------------------

        //Add car - my cars

        //UI Elements
        TextField carBrand = new TextField();
        TextField carModel = new TextField();
        TextField carYear = new TextField();
        TextField carColor = new TextField();

        //UI Elements - Button
        Button carButtonAdd = new Button("Dodaj samochód");

        //Add Placeholders
        carBrand.setPlaceholder("Marka...");
        carModel.setPlaceholder("Model...");
        carYear.setPlaceholder("Rok produkcji...");
        carColor.setPlaceholder("Kolor...");

        //Add Horizontal layout - inputs

        //Row 1
        HorizontalLayout HLayoutInputsRow1 = new HorizontalLayout();

        HLayoutInputsRow1.addComponent(carBrand);
        HLayoutInputsRow1.addComponent(carModel);

        HLayoutInputsRow1.setWidth("100%");

        //Row 2
        HorizontalLayout HLayoutInputsRow2 = new HorizontalLayout();

        HLayoutInputsRow2.addComponent(carYear);
        HLayoutInputsRow2.addComponent(carColor);

        HLayoutInputsRow2.setWidth("100%");

        //Add Horizontal layout - button
        HorizontalLayout HLayoutButton = new HorizontalLayout();

        HLayoutButton.addComponent(carButtonAdd);

        HLayoutButton.setWidth("100%");
        HLayoutButton.setHeight("100%");
        HLayoutButton.setComponentAlignment(carButtonAdd, Alignment.MIDDLE_CENTER);


        //Form Layout
        FormLayout formLayout = new FormLayout(HLayoutInputsRow1, HLayoutInputsRow2, HLayoutButton);

        //Styles
        carBrand.setWidth("100%");
        carModel.setWidth("100%");
        carYear.setWidth("100%");
        carColor.setWidth("100%");

        //Listeners
        carButtonAdd.addClickListener((Button.ClickListener) clickEvent -> {

            if(!carBrand.isEmpty() && !carModel.isEmpty() && !carYear.isEmpty() && !carColor.isEmpty()){

                try {
                    mainSystem.clientCar(
                            carBrand.getValue(),
                            carModel.getValue(),
                            carYear.getValue(),
                            carColor.getValue()
                    );

                    Notification.show("Samochód został dodany");

                    Page.getCurrent().reload();

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else{
                Notification.show("Pola nie mogą zostać puste");
            }
        });

        //endregion --------------------END MY REPAIRS - TAB 2------------------------

        Button button2 = new Button();
        Button button3 = new Button();

        //Tabs - my repairs view
        TabSheet repairsTabs = new TabSheet();

        repairsTabs.setWidth("100%");
        repairsTabs.addStyleName("tabsHeader");

        repairsTabs.addTab(carDataGrid).setCaption("Moje pojazdy");
        repairsTabs.addTab(formLayout).setCaption("Dodaj pojazd");
        repairsTabs.addTab(button2).setCaption("Aktualizuj pojazd");
        repairsTabs.addTab(button3).setCaption("Usuń pojazd");

        //Panel - my repairs
        Panel institutionListPanel = new Panel("Moje pojazdy", repairsTabs);
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
        clientMenuPanel.setWidth("100%");
        clientMenuPanel.setStyleName("employeeMenuPanel");


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

        if(MainSystem.getUserID() == 0 || !MainSystem.getUserType().equals("client")){
            UI.getCurrent().getNavigator().navigateTo("clientLogin");
        }
    }

}
