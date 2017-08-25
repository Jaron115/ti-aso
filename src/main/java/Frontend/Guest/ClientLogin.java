/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Frontend.Guest;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;

/**
 *
 * @author jamesdgabb
 */
public class ClientLogin extends VerticalLayout implements View {

    TextField clientLogin;
    PasswordField clientPassword;
    Button clientButtonLogIn;
    
    public ClientLogin(){

        //Grid Layout
        GridLayout grid = new GridLayout(3, 1);

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

        HLayoutInputs.setSpacing(true);
        HLayoutInputs.setWidth("100%");

        //Add Horizontal layout - button
        HorizontalLayout HLayoutButton = new HorizontalLayout();

        HLayoutButton.addComponent(clientButtonLogIn);

        HLayoutButton.setSpacing(true);
        HLayoutButton.setWidth("100%");
        HLayoutButton.setHeight("100%");
        HLayoutButton.setComponentAlignment(clientButtonLogIn, Alignment.MIDDLE_CENTER);


        //Form Layout
        FormLayout formLayout = new FormLayout(HLayoutInputs, HLayoutButton);
        formLayout.setMargin(true);

        //Panel
        Panel loginPanel = new Panel("Logowanie - Klient", formLayout);
        loginPanel.setWidth("1250");
        loginPanel.setStyleName("clientPanel");

        //Add Components
        addComponent(loginPanel);
        setComponentAlignment(loginPanel, Alignment.TOP_CENTER);
        setHeight("100%");
        setWidth("100%");
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
       
    }
}
