package Frontend.Employee;

import Backend.Car;
import Backend.Client;
import Backend.MainSystem;
import Frontend.Menu.ClientMenuPanel;
import Frontend.Menu.EmployeeMenuPanel;
import Frontend.Menu.MainMenu;
import com.vaadin.navigator.View;
import com.vaadin.server.Page;
import com.vaadin.ui.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

/**
 * Created by Jaron on 25.08.2017.
 */
public class CarsManagement extends VerticalLayout implements View{

    private MainSystem mainSystem = new MainSystem();

    public CarsManagement(){

        //Grid Layout
        GridLayout grid = new GridLayout(3, 1);
        grid.setWidth("100%");
        grid.setColumnExpandRatio(0,1);
        grid.setColumnExpandRatio(1,3);
        grid.setColumnExpandRatio(2,1);

        List<Car> carsData = null;

        try {
            carsData = mainSystem.getCarsData();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        assert carsData != null;

        //region --------------------START MANAGEMENT CAR DATA - TAB 1------------------------

        //Table - car data

        Grid<Car> carDataGrid = new Grid<>();

        carDataGrid.setSizeFull();
        carDataGrid.addStyleName("dataTable");
        carDataGrid.setHeight("100%");

        carDataGrid.addColumn(Car::getClientData).setCaption("Imie i Nazwisko").setResizable(false);
        carDataGrid.addColumn(Car::getBrand).setCaption("Marka").setResizable(false);
        carDataGrid.addColumn(Car::getModel).setCaption("Model").setResizable(false);
        carDataGrid.addColumn(Car::getYear).setCaption("Rok produkcji").setResizable(false);
        carDataGrid.addColumn(Car::getColor).setCaption("Kolor").setResizable(false);

        carDataGrid.setItems(carsData);

        //endregion --------------------END MANAGEMENT CAR DATA - TAB 1------------------------


        //region --------------------START MANAGEMENT CAR DATA - TAB 3------------------------

        ComboBox<Car> carComboBoxDelete = new ComboBox<>("Wybierz Samochód");

        carComboBoxDelete.setItemCaptionGenerator(Car::getClientDataWithCarData);
        carComboBoxDelete.setSizeFull();

        carComboBoxDelete.setItems(carsData);

        //Delete car - car management

        Button carButtonDelete = new Button("Usuń pojazd");

        //Row 1
        HorizontalLayout HLayoutInputsRow1Delete = new HorizontalLayout();

        HLayoutInputsRow1Delete.addComponent(carComboBoxDelete);

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
                Car element = carComboBoxDelete.getValue();
                mainSystem.deleteCarData(
                        element.getId()
                );

                Page.getCurrent().reload();

            } catch (SQLException e) {
                e.printStackTrace();
            }catch (NullPointerException e){
                Notification.show("Wybierz samochód");
            }
        });

        //endregion --------------------END MANAGEMENT CLIENT - TAB 3------------------------

        //Tabs - my data view
        TabSheet carManagementTabs = new TabSheet();

        carManagementTabs.setWidth("100%");
        carManagementTabs.addStyleName("tabsHeader");

        carManagementTabs.addTab(carDataGrid).setCaption("Lista samochodów");
        carManagementTabs.addTab(formLayoutDelete).setCaption("Usunięcie samochodu");

        //Panel - my data
        Panel clientManagementPanel = new Panel("Zarządzanie pojadami", carManagementTabs);
        clientManagementPanel.setWidth("100%");
        clientManagementPanel.setStyleName("dataListHolder");



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
        grid.addComponent(clientManagementPanel);

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

//        if(MainSystem.getUserID() == 0 || !MainSystem.getUserType().equals("employee")){
//            UI.getCurrent().getNavigator().navigateTo("employeeLogin");
//        }

    }
}
