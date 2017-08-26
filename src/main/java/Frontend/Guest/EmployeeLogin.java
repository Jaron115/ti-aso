package Frontend.Guest;

import com.vaadin.navigator.View;
import com.vaadin.ui.*;

/**
 * Created by Jaron on 25.08.2017.
 */
public class EmployeeLogin extends VerticalLayout implements View {
    TextField employeeLogin;
    PasswordField employeePassword;
    Button employeeButtonLogIn;

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
}
