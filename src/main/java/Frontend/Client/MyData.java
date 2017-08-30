package Frontend.Client;

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
import java.util.Objects;

/**
 * Created by Jaron on 25.08.2017.
 */
public class MyData extends VerticalLayout implements View{

    private MainSystem mainSystem = new MainSystem();

    public MyData(){

        //Grid Layout
        GridLayout grid = new GridLayout(3, 1);
        grid.setWidth("100%");
        grid.setColumnExpandRatio(0,1);
        grid.setColumnExpandRatio(1,3);
        grid.setColumnExpandRatio(2,1);

        Client clientData = null;

        try {
            clientData = mainSystem.getSingleClientData();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        assert clientData != null;

        //region --------------------START MY DATA - TAB 1------------------------

        //Table - my data

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

        //endregion --------------------END MY DATA - TAB 1------------------------

        //region --------------------START MY DATA - TAB 2------------------------

        //Update data - my data

        //UI Elements
        TextField clientName = new TextField();
        TextField clientSurname = new TextField();
        TextField clientAddress = new TextField();
        TextField clientEmail = new TextField();
        TextField clientLogin = new TextField();
        PasswordField clientPassword = new PasswordField();

        //UI Elements - Button
        Button clientButtonMyDataUpdate = new Button("Aktualizuj");

        //Add Placeholders
        clientName.setPlaceholder("Podaj imię...");
        clientSurname.setPlaceholder("Podaj nazwisko...");
        clientAddress.setPlaceholder("Podaj adres...");
        clientEmail.setPlaceholder("Podaj email...");
        clientLogin.setPlaceholder("Podaj login...");
        clientPassword.setPlaceholder("Podaj hasło...");

        //Styles
        clientName.setWidth("100%");
        clientSurname.setWidth("100%");
        clientAddress.setWidth("100%");
        clientEmail.setWidth("100%");
        clientLogin.setWidth("100%");
        clientPassword.setWidth("100%");

        //Add Horizontal layout - inputs

        //Row 1
        HorizontalLayout HLayoutInputsRow1 = new HorizontalLayout();

        HLayoutInputsRow1.addComponent(clientName);
        HLayoutInputsRow1.addComponent(clientSurname);

        HLayoutInputsRow1.setWidth("100%");

        //Row 2
        HorizontalLayout HLayoutInputsRow2 = new HorizontalLayout();

        HLayoutInputsRow2.addComponent(clientAddress);
        HLayoutInputsRow2.addComponent(clientEmail);

        HLayoutInputsRow2.setWidth("100%");

        //Row 3
        HorizontalLayout HLayoutInputsRow3 = new HorizontalLayout();

        HLayoutInputsRow3.addComponent(clientLogin);
        HLayoutInputsRow3.addComponent(clientPassword);

        HLayoutInputsRow3.setWidth("100%");

        //Add Horizontal layout - button
        HorizontalLayout HLayoutButton = new HorizontalLayout();

        HLayoutButton.addComponent(clientButtonMyDataUpdate);

        HLayoutButton.setWidth("100%");
        HLayoutButton.setHeight("100%");
        HLayoutButton.setComponentAlignment(clientButtonMyDataUpdate, Alignment.MIDDLE_CENTER);


        //Form Layout
        FormLayout formLayoutUpdate = new FormLayout(HLayoutInputsRow1, HLayoutInputsRow2, HLayoutInputsRow3, HLayoutButton);

        //Listeners
        clientButtonMyDataUpdate.addClickListener((Button.ClickListener) clickEvent -> {
            if(!clientName.isEmpty() && !clientSurname.isEmpty() && !clientAddress.isEmpty() && !clientEmail.isEmpty() && !clientLogin.isEmpty() && !clientPassword.isEmpty()){
                try {

                    mainSystem.updateCurrentClientData(
                            clientName.getValue(),
                            clientSurname.getValue(),
                            clientAddress.getValue(),
                            clientEmail.getValue(),
                            clientLogin.getValue(),
                            mainSystem.md5Password(clientPassword.getValue())
                    );

                    Notification.show("Dane zaktualizowane");

                    Page.getCurrent().reload();

                } catch (NoSuchAlgorithmException | UnsupportedEncodingException | SQLException e) {
                    e.printStackTrace();
                }
            } else{
                Notification.show("Pola nie mogą zostać puste");
            }
        });

        //endregion --------------------END MY DATA - TAB 2------------------------

        //region --------------------START MY DATA - TAB 3------------------------

        //Delete account - my cars

        Button clientButtonDelete = new Button("Usuń konto");

        //Add Horizontal layout - button
        HorizontalLayout HLayoutButtonDelete = new HorizontalLayout();

        HLayoutButtonDelete.addComponent(clientButtonDelete);

        HLayoutButtonDelete.setWidth("100%");
        HLayoutButtonDelete.setHeight("100%");
//        HLayoutButtonDelete.setComponentAlignment(clientButtonDelete, Alignment.MIDDLE_CENTER);

        //Form Layout
        FormLayout formLayoutDelete = new FormLayout(HLayoutButtonDelete);

        clientButtonDelete.addClickListener((Button.ClickListener) clickEvent -> {
            try {
                mainSystem.deleteCurrentClientData();

                Page.getCurrent().reload();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        //endregion --------------------END MY REPAIRS - TAB 3------------------------

        //Tabs - my data view
        TabSheet myDataTabs = new TabSheet();

        myDataTabs.setWidth("100%");
        myDataTabs.addStyleName("tabsHeader");

        myDataTabs.addTab(clientDataGrid).setCaption("Moje dane");
        myDataTabs.addTab(formLayoutUpdate).setCaption("Aktualizuj dane");
        myDataTabs.addTab(formLayoutDelete).setCaption("Usuń konto");

        //Panel - my data
        Panel institutionListPanel = new Panel("Moje dane", myDataTabs);
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
