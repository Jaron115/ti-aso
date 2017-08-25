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

        //Panel - test
        Panel testPanel = new Panel("hgegeg");
        testPanel.setWidth("100%");

        //Panel - test
        Panel test2Panel = new Panel("hgesdgsdggeg");
        test2Panel.setWidth("100%");



        grid.addComponent(testPanel);
        grid.addComponent(loginPanel);
        grid.addComponent(test2Panel);
        grid.setSpacing(true);
        grid.setMargin(true);

        //Add Components
        addComponent(grid);
        setComponentAlignment(grid, Alignment.TOP_LEFT);
        setHeight("100%");
        setWidth("100%");
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
       
    }
}
