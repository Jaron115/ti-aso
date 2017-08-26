/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Frontend.Guest;

import Backend.MainSystem;
import Frontend.Menu.ClientMenuPanel;
import Frontend.Menu.EmployeeMenuPanel;
import Frontend.Menu.MainMenu;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.ui.*;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Objects;

/**
 *
 * @author jamesdgabb
 */
public class ClientLogin extends VerticalLayout implements View {

    private MainSystem mainSystem = new MainSystem();

    TextField clientLogin;
    PasswordField clientPassword;
    Button clientButtonLogIn;

    public ClientLogin(){

        //Grid Layout
        GridLayout grid = new GridLayout(3, 1);
        grid.setWidth("100%");
        grid.setColumnExpandRatio(0,1);
        grid.setColumnExpandRatio(1,3);
        grid.setColumnExpandRatio(2,1);

        //UI Elements
        clientLogin = new TextField();
        clientPassword = new PasswordField();

        //UI Elements - Button
        clientButtonLogIn = new Button("Loguj");

        //Add Placeholders
        clientLogin.setPlaceholder("Podaj login...");
        clientPassword.setPlaceholder("Podaj hasło...");

        //Styles
        clientLogin.setWidth("100%");
        clientPassword.setWidth("100%");

        //Add Horizontal layout - inputs
        HorizontalLayout HLayoutInputs = new HorizontalLayout();

        HLayoutInputs.addComponent(clientLogin);
        HLayoutInputs.addComponent(clientPassword);

        HLayoutInputs.setWidth("100%");

        //Add Horizontal layout - button
        HorizontalLayout HLayoutButton = new HorizontalLayout();

        HLayoutButton.addComponent(clientButtonLogIn);

        HLayoutButton.setWidth("100%");
        HLayoutButton.setHeight("100%");
        HLayoutButton.setComponentAlignment(clientButtonLogIn, Alignment.MIDDLE_CENTER);


        //Form Layout
        FormLayout formLayout = new FormLayout(HLayoutInputs, HLayoutButton);

        //Panel - login
        Panel loginPanel = new Panel("Logowanie - Klient", formLayout);
        loginPanel.setWidth("100%");
        loginPanel.setStyleName("clientPanel");

        //Panel - guest menu
        MainMenu mainMenu = new MainMenu();
        VerticalLayout menuLayout = mainMenu.MainMenuLayout();
        Panel mainMenuPanel = new Panel("Menu główne", menuLayout);
        mainMenuPanel.setWidth("100%");
        mainMenuPanel.setStyleName("mainMenuPanel");

        //Panel - client menu
        ClientMenuPanel clientMenu = new ClientMenuPanel();
        VerticalLayout menuLayoutClient = clientMenu.ClientMenuLayout();
        Panel clientMenuPanel = new Panel("Panel klienta", menuLayoutClient);
        clientMenuPanel.setWidth("100%");
        clientMenuPanel.setStyleName("clientMenuPanel");

        //Panel - employee menu
        EmployeeMenuPanel employeeMenu = new EmployeeMenuPanel();
        VerticalLayout menuLayoutEmployee = employeeMenu.EmployeenMenuLayout();
        Panel employeeMenuPanel = new Panel("Panel pracownika", menuLayoutEmployee);
        clientMenuPanel.setWidth("100%");
        clientMenuPanel.setStyleName("employeeMenuPanel");


        grid.addComponent(mainMenuPanel);
        grid.addComponent(loginPanel);

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

        //Listeners
        clientButtonLogIn.addClickListener(clickEvent -> {
            boolean loginStatus = false;

            try {
                loginStatus = mainSystem.clientLogin(clientLogin.getValue(), mainSystem.md5Password(clientPassword.getValue()));
            } catch (SQLException | NoSuchAlgorithmException | UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            if(loginStatus){
                Notification.show("Zalogowano");

                Page.getCurrent().reload();

            } else{
                Notification.show("Nieprawidłowe dane");
            }
        });
    }



    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
       
    }
}
