package Frontend.Guest;

import Backend.MainSystem;
import Backend.Service;
import Frontend.Menu.ClientMenuPanel;
import Frontend.Menu.EmployeeMenuPanel;
import Frontend.Menu.MainMenu;
import com.vaadin.navigator.View;
import com.vaadin.ui.*;


import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Created by Jaron on 25.08.2017.
 */
public class ServicesList extends VerticalLayout implements View {

    private MainSystem mainSystem = new MainSystem();

    public ServicesList(){

        //Grid Layout
        GridLayout grid = new GridLayout(3, 1);
        grid.setWidth("100%");
        grid.setColumnExpandRatio(0,1);
        grid.setColumnExpandRatio(1,3);
        grid.setColumnExpandRatio(2,1);


        //Table - service data
        List<Service> serviceData = null;
        Grid<Service> serviceDataGrid;
        try {
            serviceData = mainSystem.getServiceDataFromDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        serviceDataGrid = new Grid<>();

        serviceDataGrid.setSizeFull();
        serviceDataGrid.addStyleName("dataTable");
        serviceDataGrid.setHeight("100%");

        serviceDataGrid.addColumn(Service::getName).setCaption("Nazwa").setResizable(false);
        serviceDataGrid.addColumn(Service::getDescription).setCaption("Opis").setResizable(false);
        serviceDataGrid.addColumn(Service::getPrice).setCaption("Cena").setResizable(false);

        assert serviceData != null;
        serviceDataGrid.setItems(serviceData);



        //Panel - service list
        Panel serviceListPanel = new Panel("Lista usług", serviceDataGrid);
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
        clientMenuPanel.setWidth("100%");
        clientMenuPanel.setStyleName("employeeMenuPanel");


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
    }
}
