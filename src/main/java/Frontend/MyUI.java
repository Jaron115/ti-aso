package Frontend;

import javax.servlet.annotation.WebServlet;

import Backend.MainSystem;
import Frontend.Client.*;
import Frontend.Employee.*;
import Frontend.Guest.*;
import com.vaadin.annotations.StyleSheet;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of a html page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("mytheme")
@Widgetset("com.mycompany.vaadin_tutorial.MyAppWidgetset")
@StyleSheet({"https://fonts.googleapis.com/css?family=Lato:400,700,900&subset=latin-ext"})
public class MyUI extends UI {

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        Navigator navigator = new Navigator(this,this);

        /*
        GUEST VIEWS
         */

        //Index
        navigator.addView("index", new Index());

        //Client login
        navigator.addView("clientLogin", new ClientLogin());

        //Employee login
        navigator.addView("employeeLogin", new EmployeeLogin());

        //Client register
        navigator.addView("clientRegister", new Register());

        //Guest service list
        navigator.addView("guestServicesList", new ServicesList());

        /*
        CLIENT VIEWS
         */

        //Client institution list
        navigator.addView("clientInstitutionList", new InstitutionList());

        //Client my repairs
        navigator.addView("clientMyRepairs", new MyRepairs());

        //Client my cars
        navigator.addView("clientMyCars", new MyCars());

        //Client my data
        navigator.addView("clientMyData", new MyData());

        /*
        EMPLOYEE VIEWS
         */

        //Employee management client
        navigator.addView("clientManagementEmployee", new ClientManagement());

        //Employee management institution
        navigator.addView("institutionManagementEmployee", new InstitutionManagement());

        //Employee management cars
        navigator.addView("carsManagementEmployee", new CarsManagement());

        //Employee management employee
        navigator.addView("employeeManagementEmployee", new EmployeeManagement());

        //Employee management services
        navigator.addView("servicesManagementEmployee", new ServicesManagement());

        //Employee management repairs
        navigator.addView("repairsManagementEmployee", new RepairsManagement());

        //Begin login view
        if (navigator.getState().isEmpty()) {
            navigator.navigateTo("clientRegister");
        }
    }


    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
