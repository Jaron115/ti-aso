package Frontend.Client;

import Backend.MainSystem;
import com.vaadin.navigator.View;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

/**
 * Created by Jaron on 25.08.2017.
 */
public class MyRepairs extends VerticalLayout implements View {

    private MainSystem mainSystem = new MainSystem();

    public MyRepairs(){

        if(MainSystem.getUserID() == 0 || !MainSystem.getUserType().equals("client")){
            UI.getCurrent().getNavigator().navigateTo("clientLogin");
        }
    }

}
