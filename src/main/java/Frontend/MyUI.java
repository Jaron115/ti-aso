package Frontend;

import javax.servlet.annotation.WebServlet;

import Frontend.Guest.ClientLogin;
import Frontend.Guest.Index;
import com.vaadin.annotations.StyleSheet;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;

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

        //Index
        navigator.addView("index", new Index());

        //Client login
        navigator.addView("clientLogin", new ClientLogin());

        //Begin login view
        navigator.navigateTo("clientLogin");
    }
 
    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
