package Frontend.Menu;

import Backend.MainSystem;
import com.vaadin.navigator.View;
import com.vaadin.server.Page;
import com.vaadin.ui.Button;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class ClientMenuPanel extends VerticalLayout implements View {

    private MainSystem mainSystem = new MainSystem();

    public ClientMenuPanel(){

    }

    public VerticalLayout ClientMenuLayout(){
        VerticalLayout menuLayout = new VerticalLayout();

        //Button - list placówek
        Button institution = new Button("Lista placówek");

        institution.addClickListener((Button.ClickListener) clickEvent -> {
            UI.getCurrent().getNavigator().navigateTo("clientInstitutionList");
        });

        menuLayout.addComponent(institution);

        //Button - lista usług
        Button serviceList = new Button("Lista usług");

        serviceList.addClickListener((Button.ClickListener) clickEvent -> {
            UI.getCurrent().getNavigator().navigateTo("guestServicesList");
        });

        menuLayout.addComponent(serviceList);

        //Button - moje naprawy
        Button myRepairs = new Button("Moje naprawy");

        myRepairs.addClickListener((Button.ClickListener) clickEvent -> {
            UI.getCurrent().getNavigator().navigateTo("clientMyRepairs");
        });

        menuLayout.addComponent(myRepairs);

        //Button - moje pojazdy
        Button myCars = new Button("Moje pojazdy");

        myCars.addClickListener((Button.ClickListener) clickEvent -> {
            UI.getCurrent().getNavigator().navigateTo("clientMyCars");
        });

        menuLayout.addComponent(myCars);

        //Button - moje dane
        Button myData = new Button("Moje dane");

        myData.addClickListener((Button.ClickListener) clickEvent -> {
            UI.getCurrent().getNavigator().navigateTo("clientMyData");
        });

        menuLayout.addComponent(myData);

        //Button - wylogowanie
        Button logout = new Button("Wylogowanie");

        logout.addClickListener((Button.ClickListener) clickEvent -> {
            mainSystem.userLogout();

            Page.getCurrent().reload();

            UI.getCurrent().getNavigator().navigateTo("clientRegister");
        });

        menuLayout.addComponent(logout);



        return menuLayout;
    }

}
