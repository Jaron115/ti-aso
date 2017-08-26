package Frontend.Guest;

import Backend.MainSystem;
import Frontend.Menu.ClientMenuPanel;
import Frontend.Menu.EmployeeMenuPanel;
import Frontend.Menu.MainMenu;
import Frontend.MyUI;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.ui.*;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Objects;

/**
 * Created by Jaron on 25.08.2017.
 */
public class Register extends VerticalLayout implements View {

    private MainSystem mainSystem = new MainSystem();

    private TextField clientName;
    private TextField clientSurname;
    private TextField clientAdress;
    private TextField clientEmail;
    private TextField clientLogin;
    private PasswordField clientPassword;
    private Button clientButtonRegister;

    public Register() {

        //Grid Layout
        GridLayout grid = new GridLayout(3, 1);
        grid.setWidth("100%");
        grid.setColumnExpandRatio(0,1);
        grid.setColumnExpandRatio(1,3);
        grid.setColumnExpandRatio(2,1);

        //UI Elements
        clientName = new TextField();
        clientSurname = new TextField();
        clientAdress = new TextField();
        clientEmail = new TextField();
        clientLogin = new TextField();
        clientPassword = new PasswordField();

        //UI Elements - Button
        clientButtonRegister = new Button("Rejestruj");

        //Add Placeholders
        clientName.setPlaceholder("Podaj imię...");
        clientSurname.setPlaceholder("Podaj nazwisko...");
        clientAdress.setPlaceholder("Podaj adres...");
        clientEmail.setPlaceholder("Podaj email...");
        clientLogin.setPlaceholder("Podaj login...");
        clientPassword.setPlaceholder("Podaj hasło...");

        //Styles
        clientName.setWidth("100%");
        clientSurname.setWidth("100%");
        clientAdress.setWidth("100%");
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

        HLayoutInputsRow2.addComponent(clientAdress);
        HLayoutInputsRow2.addComponent(clientEmail);

        HLayoutInputsRow2.setWidth("100%");

        //Row 3
        HorizontalLayout HLayoutInputsRow3 = new HorizontalLayout();

        HLayoutInputsRow3.addComponent(clientLogin);
        HLayoutInputsRow3.addComponent(clientPassword);

        HLayoutInputsRow3.setWidth("100%");

        //Add Horizontal layout - button
        HorizontalLayout HLayoutButton = new HorizontalLayout();

        HLayoutButton.addComponent(clientButtonRegister);

        HLayoutButton.setWidth("100%");
        HLayoutButton.setHeight("100%");
        HLayoutButton.setComponentAlignment(clientButtonRegister, Alignment.MIDDLE_CENTER);


        //Form Layout
        FormLayout formLayout = new FormLayout(HLayoutInputsRow1, HLayoutInputsRow2, HLayoutInputsRow3, HLayoutButton);

        //Panel - login
        Panel registerPanel = new Panel("Rejestracja", formLayout);
        registerPanel.setWidth("100%");
        registerPanel.setStyleName("registerPanel");

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
        grid.addComponent(registerPanel);

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
        clientButtonRegister.addClickListener((Button.ClickListener) clickEvent -> {

            try {
                if(!mainSystem.isAccountExist(clientLogin.getValue())){

                    mainSystem.registerClient(
                            clientName.getValue(),
                            clientSurname.getValue(),
                            clientAdress.getValue(),
                            clientEmail.getValue(),
                            clientLogin.getValue(),
                            mainSystem.md5Password(clientPassword.getValue())
                    );

                    Notification.show("Zostałeś zarejestrowany");

                    getUI().getNavigator().navigateTo("clientLogin");

                } else{
                    Notification.show("Takie konto już istnieje");
                }

            } catch (NoSuchAlgorithmException | UnsupportedEncodingException | SQLException e) {
                e.printStackTrace();
            }
        });

    }


}
