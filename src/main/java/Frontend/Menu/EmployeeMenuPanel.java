package Frontend.Menu;

import Backend.MainSystem;
import com.vaadin.navigator.View;
import com.vaadin.server.Page;
import com.vaadin.ui.Button;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class EmployeeMenuPanel extends VerticalLayout implements View {

    private MainSystem mainSystem = new MainSystem();

    public EmployeeMenuPanel(){

    }

    public VerticalLayout EmployeenMenuLayout(){
        VerticalLayout menuLayout = new VerticalLayout();

        //Button - zarządzanie klientami
        Button clientManagement = new Button("Zarządzanie klientami");

        clientManagement.addClickListener((Button.ClickListener) clickEvent -> {
            UI.getCurrent().getNavigator().navigateTo("clientManagementEmployee");
        });

        menuLayout.addComponent(clientManagement);

        //Button - zarządzanie placówkami
        Button institutionManagement = new Button("Zarządzanie placówkami");

        institutionManagement.addClickListener((Button.ClickListener) clickEvent -> {
            UI.getCurrent().getNavigator().navigateTo("institutionManagementEmployee");
        });

        menuLayout.addComponent(institutionManagement);

        //Button - zarządzanie pojazdami
        Button carsManagement = new Button("Zarządzanie pojazdami");

        carsManagement.addClickListener((Button.ClickListener) clickEvent -> {
            UI.getCurrent().getNavigator().navigateTo("carsManagementEmployee");
        });

        menuLayout.addComponent(carsManagement);

        //Button - zarządzanie pracownikami
        Button employeeManagement = new Button("Zarządzanie pracownikami");

        employeeManagement.addClickListener((Button.ClickListener) clickEvent -> {
            UI.getCurrent().getNavigator().navigateTo("employeeManagementEmployee");
        });

        menuLayout.addComponent(employeeManagement);

        //Button - zarządzanie usługami
        Button servicesManagement = new Button("Zarządzanie usługami");

        servicesManagement.addClickListener((Button.ClickListener) clickEvent -> {
            UI.getCurrent().getNavigator().navigateTo("servicesManagementEmployee");
        });

        menuLayout.addComponent(servicesManagement);

        //Button - zarządzanie naprawami
        Button repairsManagement = new Button("Zarządzanie naprawami");

        repairsManagement.addClickListener((Button.ClickListener) clickEvent -> {
            UI.getCurrent().getNavigator().navigateTo("repairsManagementEmployee");
        });

        menuLayout.addComponent(repairsManagement);

        //Button - wylogowanie
        Button logout = new Button("Wylogowanie");

        logout.addClickListener((Button.ClickListener) clickEvent -> {
            mainSystem.userLogout();

            Page.getCurrent().reload();

            UI.getCurrent().getNavigator().navigateTo("index");
        });

        menuLayout.addComponent(logout);

        return menuLayout;
    }
}
