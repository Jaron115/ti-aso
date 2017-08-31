package Frontend.Guest;

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
public class EmployeeLogin extends VerticalLayout implements View {

    private MainSystem mainSystem = new MainSystem();

    private TextField employeeLogin;
    private PasswordField employeePassword;
    private Button employeeButtonLogIn;

    public EmployeeLogin(){

        //Grid Layout
        GridLayout grid = new GridLayout(3, 1);
        grid.setWidth("100%");
        grid.setColumnExpandRatio(0,1);
        grid.setColumnExpandRatio(1,3);
        grid.setColumnExpandRatio(2,1);

        //UI Elements
        employeeLogin = new TextField();
        employeePassword = new PasswordField();

        //UI Elements - Button
        employeeButtonLogIn = new Button("Loguj");

        //Add Placeholders
        employeeLogin.setPlaceholder("Podaj login...");
        employeePassword.setPlaceholder("Podaj hasło...");

        //Styles
        employeeLogin.setWidth("100%");
        employeePassword.setWidth("100%");

        //Add Horizontal layout - inputs
        HorizontalLayout HLayoutInputs = new HorizontalLayout();

        HLayoutInputs.addComponent(employeeLogin);
        HLayoutInputs.addComponent(employeePassword);

        HLayoutInputs.setWidth("100%");

        //Add Horizontal layout - button
        HorizontalLayout HLayoutButton = new HorizontalLayout();

        HLayoutButton.addComponent(employeeButtonLogIn);

        HLayoutButton.setWidth("100%");
        HLayoutButton.setHeight("100%");
        HLayoutButton.setComponentAlignment(employeeButtonLogIn, Alignment.MIDDLE_CENTER);

        //Form Layout
        FormLayout formLayout = new FormLayout(HLayoutInputs, HLayoutButton);

        //Panel - login
        Panel loginPanel = new Panel("Logowanie - Pracownik", formLayout);
        loginPanel.setWidth("100%");
        loginPanel.setStyleName("employeePanel");

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

        employeeButtonLogIn.addClickListener(clickEvent -> {
            boolean loginStatus = false;

            try {
                loginStatus = mainSystem.employeeLogin(employeeLogin.getValue(), mainSystem.md5Password(employeeLogin.getValue()));
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
}
