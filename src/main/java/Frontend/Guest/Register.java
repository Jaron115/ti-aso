package Frontend.Guest;

import Backend.MainSystem;
import com.vaadin.navigator.View;
import com.vaadin.ui.*;

import java.sql.SQLException;

/**
 * Created by Jaron on 25.08.2017.
 */
public class Register extends VerticalLayout implements View {

    MainSystem mainSystem = new MainSystem();

    TextField clientName;
    TextField clientAdress;
    TextField clientSurname;
    TextField clientLogin;
    TextField clientEmail;
    PasswordField clientPassword;
    Button clientButtonRegister;

    public Register() {

        //Grid Layout
        GridLayout grid = new GridLayout(3, 1);
        grid.setWidth("100%");
        grid.setColumnExpandRatio(0,1);
        grid.setColumnExpandRatio(1,3);
        grid.setColumnExpandRatio(2,1);

        //UI Elements
        clientName = new TextField();
        clientAdress = new TextField();
        clientSurname = new TextField();
        clientLogin = new TextField();
        clientEmail = new TextField();
        clientPassword = new PasswordField();

        //UI Elements - Button
        clientButtonRegister = new Button("Rejestruj");

        //Add Placeholders
        clientName.setPlaceholder("Podaj imię...");
        clientAdress.setPlaceholder("Podaj adres...");
        clientSurname.setPlaceholder("Podaj nazwisko...");
        clientLogin.setPlaceholder("Podaj login...");
        clientEmail.setPlaceholder("Podaj email...");
        clientPassword.setPlaceholder("Podaj hasło...");

        //Styles
        clientName.setWidth("100%");
        clientAdress.setWidth("100%");
        clientSurname.setWidth("100%");
        clientLogin.setWidth("100%");
        clientEmail.setWidth("100%");
        clientPassword.setWidth("100%");

        //Add Horizontal layout - inputs

        //Row 1
        HorizontalLayout HLayoutInputsRow1 = new HorizontalLayout();

        HLayoutInputsRow1.addComponent(clientName);
        HLayoutInputsRow1.addComponent(clientAdress);

        HLayoutInputsRow1.setWidth("100%");

        //Row 2
        HorizontalLayout HLayoutInputsRow2 = new HorizontalLayout();

        HLayoutInputsRow2.addComponent(clientSurname);
        HLayoutInputsRow2.addComponent(clientLogin);

        HLayoutInputsRow2.setWidth("100%");

        //Row 3
        HorizontalLayout HLayoutInputsRow3 = new HorizontalLayout();

        HLayoutInputsRow3.addComponent(clientEmail);
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

        //Panel - test
        Panel testPanel = new Panel("hgegeg");
        testPanel.setWidth("100%");

        //Panel - test
        Panel test2Panel = new Panel("hgesdgsdggeg");
        test2Panel.setWidth("100%");



        grid.addComponent(testPanel);
        grid.addComponent(registerPanel);
        grid.addComponent(test2Panel);
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
                mainSystem.test();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

    }
}
