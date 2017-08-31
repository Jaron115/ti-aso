package Frontend.Employee;

import Backend.Client;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by Jaron on 25.08.2017.
 */
public class ClientManagement extends VerticalLayout implements View {

    private MainSystem mainSystem = new MainSystem();

    public ClientManagement(){

        //Grid Layout
        GridLayout grid = new GridLayout(3, 1);
        grid.setWidth("100%");
        grid.setColumnExpandRatio(0,1);
        grid.setColumnExpandRatio(1,3);
        grid.setColumnExpandRatio(2,1);

        List<Client> clientData = null;

        try {
            clientData = mainSystem.getClientData();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        assert clientData != null;

        //region --------------------START MANAGEMENT CLIENT DATA - TAB 1------------------------

        //Table - client data

        Grid<Client> clientDataGrid = new Grid<>();

        clientDataGrid.setSizeFull();
        clientDataGrid.addStyleName("dataTable");
        clientDataGrid.setHeight("100%");

        clientDataGrid.addColumn(Client::getName).setCaption("Imię").setResizable(false);
        clientDataGrid.addColumn(Client::getLastName).setCaption("Nazwisko").setResizable(false);
        clientDataGrid.addColumn(Client::getAddress).setCaption("Adres").setResizable(false);
        clientDataGrid.addColumn(Client::getEmail).setCaption("E-mail").setResizable(false);
        clientDataGrid.addColumn(Client::getLogin).setCaption("Login").setResizable(false);

        clientDataGrid.setItems(clientData);

        //endregion --------------------END MANAGEMENT CLIENT DATA - TAB 1------------------------


        //region --------------------START MANAGEMENT CLIENT DATA - TAB 3------------------------

        ComboBox<Client> clientComboBoxDelete = new ComboBox<>("Wybierz klienta");

        clientComboBoxDelete.setItemCaptionGenerator(Client::getClientData);
        clientComboBoxDelete.setSizeFull();

        clientComboBoxDelete.setItems(clientData);

        //Delete account - client management

        Button clientButtonDelete = new Button("Usuń klienta");

        //Row 1
        HorizontalLayout HLayoutInputsRow1Delete = new HorizontalLayout();

        HLayoutInputsRow1Delete.addComponent(clientComboBoxDelete);

        HLayoutInputsRow1Delete.setWidth("100%");

        //Add Horizontal layout - button
        HorizontalLayout HLayoutButtonDelete = new HorizontalLayout();

        HLayoutButtonDelete.addComponent(clientButtonDelete);

        HLayoutButtonDelete.setWidth("100%");
        HLayoutButtonDelete.setHeight("100%");
        HLayoutButtonDelete.setComponentAlignment(clientButtonDelete, Alignment.MIDDLE_CENTER);

        //Form Layout
        FormLayout formLayoutDelete = new FormLayout(HLayoutInputsRow1Delete, HLayoutButtonDelete);

        clientButtonDelete.addClickListener((Button.ClickListener) clickEvent -> {
            try {
                Client element = clientComboBoxDelete.getValue();
                mainSystem.deleteClientData(
                        element.getId()
                );

                Page.getCurrent().reload();

            } catch (SQLException e) {
                e.printStackTrace();
            }catch (NullPointerException e){
                Notification.show("Wybierz klienta");
            }
        });

        //endregion --------------------END MANAGEMENT CLIENT - TAB 3------------------------

        //Tabs - my data view
        TabSheet myDataTabs = new TabSheet();

        myDataTabs.setWidth("100%");
        myDataTabs.addStyleName("tabsHeader");

        myDataTabs.addTab(clientDataGrid).setCaption("Lista klientów");
        myDataTabs.addTab(formLayoutDelete).setCaption("Usunięcie klienta");

        //Panel - my data
        Panel clientManagementPanel = new Panel("Zarządzanie klientami", myDataTabs);
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
