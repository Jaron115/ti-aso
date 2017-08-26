package Frontend.Menu;

import Backend.MainSystem;
import com.vaadin.navigator.View;
import com.vaadin.ui.Button;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class MainMenu extends VerticalLayout implements View{

    public MainMenu(){

    }

    public VerticalLayout MainMenuLayout(){
        VerticalLayout menuLayout = new VerticalLayout();

        //Button - strona główna
        Button homepage = new Button("Strona główna");

        homepage.addClickListener((Button.ClickListener) clickEvent -> {
            UI.getCurrent().getNavigator().navigateTo("clientLogin");
        });

        menuLayout.addComponent(homepage);

        //Button - lista usług
        Button serviceList = new Button("Lista usług");

        serviceList.addClickListener((Button.ClickListener) clickEvent -> {
            UI.getCurrent().getNavigator().navigateTo("guestServicesList");
        });

        menuLayout.addComponent(serviceList);

        //Button - panel logowania klienta
        if(MainSystem.getUserID() == 0){
            Button clientLoginPanel = new Button("Panel klienta");

            clientLoginPanel.addClickListener((Button.ClickListener) clickEvent -> {
                UI.getCurrent().getNavigator().navigateTo("clientLogin");
            });

            menuLayout.addComponent(clientLoginPanel);
        }

        //Button - panel logowania pracownika
        if(MainSystem.getUserID() == 0){
            Button employeeLoginPanel = new Button("Panel pracownika");

            employeeLoginPanel.addClickListener((Button.ClickListener) clickEvent -> {
                UI.getCurrent().getNavigator().navigateTo("employeeLogin");
            });

            menuLayout.addComponent(employeeLoginPanel);
        }

        //Button - panel rejestracji klienta
        Button clientRegisterPanel = new Button("Rejestracja");

        clientRegisterPanel.addClickListener((Button.ClickListener) clickEvent -> {
            UI.getCurrent().getNavigator().navigateTo("clientRegister");
        });

        clientRegisterPanel.addStyleName("active");

        menuLayout.addComponent(clientRegisterPanel);

        return menuLayout;
    }
}
