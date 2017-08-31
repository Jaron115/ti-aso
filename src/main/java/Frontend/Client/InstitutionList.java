package Frontend.Client;

import Backend.Institution;
import Backend.MainSystem;
import Backend.Service;
import Frontend.Menu.ClientMenuPanel;
import Frontend.Menu.EmployeeMenuPanel;
import Frontend.Menu.MainMenu;
import com.vaadin.navigator.View;
import com.vaadin.ui.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

/**
 * Created by Jaron on 25.08.2017.
 */
public class InstitutionList extends VerticalLayout implements View {

    private MainSystem mainSystem = new MainSystem();

    public InstitutionList(){

        //Grid Layout
        GridLayout grid = new GridLayout(3, 1);
        grid.setWidth("100%");
        grid.setColumnExpandRatio(0,1);
        grid.setColumnExpandRatio(1,3);
        grid.setColumnExpandRatio(2,1);


        //Table - institution data
        List<Institution> institutionData = null;
        Grid<Institution> institutionDataGrid;
        try {
            institutionData = mainSystem.getInstitutionDataFromDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        institutionDataGrid = new Grid<>();

        institutionDataGrid.setSizeFull();
        institutionDataGrid.addStyleName("dataTable");
        institutionDataGrid.setHeight("100%");

        institutionDataGrid.addColumn(Institution::getName).setCaption("Nazwa").setResizable(false);
        institutionDataGrid.addColumn(Institution::getDescription).setCaption("Opis").setResizable(false);
        institutionDataGrid.addColumn(Institution::getAddress).setCaption("Adres").setResizable(false);

        assert institutionData != null;
        institutionDataGrid.setItems(institutionData);



        //Panel - institution list
        Panel institutionListPanel = new Panel("Lista placówek", institutionDataGrid);
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
