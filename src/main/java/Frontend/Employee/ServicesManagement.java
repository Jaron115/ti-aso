package Frontend.Employee;

import Backend.Institution;
import Backend.MainSystem;
import Backend.Service;
import Frontend.Menu.ClientMenuPanel;
import Frontend.Menu.EmployeeMenuPanel;
import Frontend.Menu.MainMenu;
import com.vaadin.data.HasValue;
import com.vaadin.navigator.View;
import com.vaadin.server.Page;
import com.vaadin.ui.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

/**
 * Created by Jaron on 25.08.2017.
 */
public class ServicesManagement extends VerticalLayout implements View {

    private MainSystem mainSystem = new MainSystem();

    public ServicesManagement(){

        //Grid Layout
        GridLayout grid = new GridLayout(3, 1);
        grid.setWidth("100%");
        grid.setColumnExpandRatio(0,1);
        grid.setColumnExpandRatio(1,3);
        grid.setColumnExpandRatio(2,1);

        List<Service> serviceData = null;

        try {
            serviceData = mainSystem.getServiceDataFromDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        assert serviceData != null;

        //region --------------------START SERVICE MANAGEMENT - TAB 1------------------------

        //Table

        Grid<Service> serviceDataGrid = new Grid<>();

        serviceDataGrid.setSizeFull();
        serviceDataGrid.addStyleName("dataTable");
        serviceDataGrid.setHeight("100%");

        serviceDataGrid.addColumn(Service::getName).setCaption("Nazwa usługi").setResizable(false);
        serviceDataGrid.addColumn(Service::getPrice).setCaption("Cena usługi").setResizable(false);
        serviceDataGrid.addColumn(Service::getDescription).setCaption("Opis usługi").setResizable(false);

        serviceDataGrid.setItems(serviceData);

        //endregion --------------------END SERVICE MANAGEMENT - TAB 1------------------------

        //region --------------------START SERVICE MANAGEMENT - TAB 2------------------------

        //Add service - management service

        //UI Elements
        TextField serviceName = new TextField();
        TextField servicePrice = new TextField();
        TextArea serviceDescription = new TextArea();

        //UI Elements - Button
        Button serviceButtonAdd = new Button("Dodaj usługę");

        //Add Placeholders
        serviceName.setPlaceholder("Nazwa usługi...");
        servicePrice.setPlaceholder("Cena usługi...");
        serviceDescription.setPlaceholder("Opis usługi...");

        //Add Horizontal layout - inputs

        //Row 1
        HorizontalLayout HLayoutInputsRow1 = new HorizontalLayout();

        HLayoutInputsRow1.addComponent(serviceName);
        HLayoutInputsRow1.addComponent(servicePrice);

        HLayoutInputsRow1.setWidth("100%");

        //Row 2
        HorizontalLayout HLayoutInputsRow2 = new HorizontalLayout();

        HLayoutInputsRow2.addComponent(serviceDescription);

        HLayoutInputsRow2.setWidth("100%");

        //Add Horizontal layout - button
        HorizontalLayout HLayoutButton = new HorizontalLayout();

        HLayoutButton.addComponent(serviceButtonAdd);

        HLayoutButton.setWidth("100%");
        HLayoutButton.setHeight("100%");
        HLayoutButton.setComponentAlignment(serviceButtonAdd, Alignment.MIDDLE_CENTER);


        //Form Layout
        FormLayout formLayout = new FormLayout(HLayoutInputsRow1, HLayoutInputsRow2, HLayoutButton);

        //Styles
        serviceName.setWidth("100%");
        servicePrice.setWidth("100%");
        serviceDescription.setWidth("100%");

        //Listeners
        serviceButtonAdd.addClickListener((Button.ClickListener) clickEvent -> {

            if(!serviceName.isEmpty() && !servicePrice.isEmpty() && !serviceDescription.isEmpty()){

                try {
                    mainSystem.serviceAdd(
                            serviceName.getValue(),
                            servicePrice.getValue(),
                            serviceDescription.getValue()
                    );

                    Notification.show("Usługa została dodana");

                    Page.getCurrent().reload();

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else{
                Notification.show("Pola nie mogą zostać puste");
            }
        });

        //endregion --------------------END SERVICE MANAGEMENT - TAB 2------------------------

        //region --------------------START SERVICE MANAGEMENT - TAB 3------------------------

        //Update service - management service

        //ComboBox
        ComboBox<Service> serviceComboBoxUpdate = new ComboBox<>("Wybierz usługę");

        serviceComboBoxUpdate.setItemCaptionGenerator(Service::getName);
        serviceComboBoxUpdate.setSizeFull();

        serviceComboBoxUpdate.setItems(serviceData);

        //UI Elements
        TextField serviceNameUpdate = new TextField();
        TextField servicePriceUpdate = new TextField();
        TextArea serviceDescriptionUpdate = new TextArea();

        //UI Elements - Button
        Button serviceButtonUpdate = new Button("Aktualizuj usługę");

        //Add Placeholders
        serviceNameUpdate.setPlaceholder("Nazwa usługi...");
        servicePriceUpdate.setPlaceholder("Cena usługi...");
        serviceDescriptionUpdate.setPlaceholder("Opis usługi...");

        //Add Caption
        serviceNameUpdate.setCaption("Nazwa usługi");
        servicePriceUpdate.setCaption("Cena usługi");
        serviceDescriptionUpdate.setCaption("Opis usługi");

        //Add Horizontal layout - inputs

        //Row 1
        HorizontalLayout HLayoutInputsRow1Update = new HorizontalLayout();

        HLayoutInputsRow1Update.addComponent(serviceComboBoxUpdate);

        HLayoutInputsRow1Update.setWidth("100%");

        //Row 2
        HorizontalLayout HLayoutInputsRow2Update = new HorizontalLayout();

        HLayoutInputsRow2Update.addComponent(serviceNameUpdate);
        HLayoutInputsRow2Update.addComponent(servicePriceUpdate);

        HLayoutInputsRow2Update.setWidth("100%");

        //Row 3
        HorizontalLayout HLayoutInputsRow3Update = new HorizontalLayout();

        HLayoutInputsRow3Update.addComponent(serviceDescriptionUpdate);

        HLayoutInputsRow3Update.setWidth("100%");

        //Add Horizontal layout - button
        HorizontalLayout HLayoutButtonUpdate = new HorizontalLayout();

        HLayoutButtonUpdate.addComponent(serviceButtonUpdate);

        HLayoutButtonUpdate.setWidth("100%");
        HLayoutButtonUpdate.setHeight("100%");
        HLayoutButtonUpdate.setComponentAlignment(serviceButtonUpdate, Alignment.MIDDLE_CENTER);


        //Form Layout
        FormLayout formLayoutUpdate = new FormLayout(HLayoutInputsRow1Update, HLayoutInputsRow2Update, HLayoutInputsRow3Update, HLayoutButtonUpdate);


        //Styles
        serviceNameUpdate.setWidth("100%");
        servicePriceUpdate.setWidth("100%");
        serviceDescriptionUpdate.setWidth("100%");

        //Listeners
        serviceButtonUpdate.addClickListener((Button.ClickListener) clickEvent -> {
            Service element = serviceComboBoxUpdate.getValue();

            try {
                if(!serviceNameUpdate.isEmpty() && !servicePriceUpdate.isEmpty() && !serviceDescriptionUpdate.isEmpty()){
                    mainSystem.serviceUpdate(
                            element.getId(),
                            serviceNameUpdate.getValue(),
                            servicePriceUpdate.getValue(),
                            serviceDescriptionUpdate.getValue()
                    );

                    Notification.show("Usługa zaktualizowana");

                    Page.getCurrent().reload();
                } else{
                    Notification.show("Uzupełnij wszystkie pola");
                }
            } catch (NullPointerException e){
                Notification.show("Wybierz usługę");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        serviceComboBoxUpdate.addValueChangeListener((HasValue.ValueChangeEvent<Service> event) -> {
            Service element = serviceComboBoxUpdate.getValue();

            serviceNameUpdate.setValue(element.getName());
            servicePriceUpdate.setValue(element.getPrice());
            serviceDescriptionUpdate.setValue(element.getDescription());

        });

        //endregion --------------------END SERVICE MANAGEMENT - TAB 3------------------------

        //region --------------------START SERVICE MANAGEMENT - TAB 4------------------------

        //Delete car - my cars

        //ComboBox
        ComboBox<Service> serviceComboBoxDelete = new ComboBox<>("Wybierz usługę");

        serviceComboBoxDelete.setItemCaptionGenerator(Service::getName);
        serviceComboBoxDelete.setSizeFull();

        serviceComboBoxDelete.setItems(serviceData);

        Button serviceButtonDelete = new Button("Usuń Usługę");

        //Row 1
        HorizontalLayout HLayoutInputsRow1Delete = new HorizontalLayout();

        HLayoutInputsRow1Delete.addComponent(serviceComboBoxDelete);

        HLayoutInputsRow1Delete.setWidth("100%");

        //Add Horizontal layout - button
        HorizontalLayout HLayoutButtonDelete = new HorizontalLayout();

        HLayoutButtonDelete.addComponent(serviceButtonDelete);

        HLayoutButtonDelete.setWidth("100%");
        HLayoutButtonDelete.setHeight("100%");
        HLayoutButtonDelete.setComponentAlignment(serviceButtonDelete, Alignment.MIDDLE_CENTER);

        //Form Layout
        FormLayout formLayoutDelete = new FormLayout(HLayoutInputsRow1Delete, HLayoutButtonDelete);

        serviceButtonDelete.addClickListener((Button.ClickListener) clickEvent -> {
            Service element = serviceComboBoxDelete.getValue();

            try {
                mainSystem.serviceDelete(
                        element.getId()
                );

                Notification.show("Usługa usunięta");

                Page.getCurrent().reload();
            } catch (NullPointerException e){
                Notification.show("Wybierz usługę");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        //endregion --------------------END SERVICE MANAGEMENT - TAB 4------------------------

        //Tabs - my repairs view
        TabSheet serviceTabs = new TabSheet();

        serviceTabs.setWidth("100%");
        serviceTabs.addStyleName("tabsHeader");

        serviceTabs.addTab(serviceDataGrid).setCaption("Lista usług");
        serviceTabs.addTab(formLayout).setCaption("Dodanie usługi");
        serviceTabs.addTab(formLayoutUpdate).setCaption("Aktualizacja usługi");
        serviceTabs.addTab(formLayoutDelete).setCaption("Usunięcie usługi");

        //Panel - my repairs
        Panel serviceListPanel = new Panel("Zarządzanie usługami", serviceTabs);
        serviceListPanel.setWidth("100%");
        serviceListPanel.setStyleName("dataListHolder");



        //Panel - guest menu
        MainMenu mainMenu = new MainMenu();
        VerticalLayout menuLayout = mainMenu.MainMenuLayout();
        menuLayout.setSpacing(false);
        Panel mainMenuPanel = new Panel("Menu główne", menuLayout);
        mainMenuPanel.setWidth("100%");
        mainMenuPanel.setStyleName("mainMenuPanel");

        //Panel - client menu
        ClientMenuPanel clientMenu = new ClientMenuPanel();
        VerticalLayout menuLayoutClient = clientMenu.ClientMenuLayout();
        menuLayoutClient.setSpacing(false);
        Panel clientMenuPanel = new Panel("Panel klienta", menuLayoutClient);
        clientMenuPanel.setWidth("100%");
        clientMenuPanel.setStyleName("clientMenuPanel");

        //Panel - employee menu
        EmployeeMenuPanel employeeMenu = new EmployeeMenuPanel();
        VerticalLayout menuLayoutEmployee = employeeMenu.EmployeenMenuLayout();
        menuLayoutEmployee.setSpacing(false);
        Panel employeeMenuPanel = new Panel("Panel pracownika", menuLayoutEmployee);
        employeeMenuPanel.setWidth("100%");
        employeeMenuPanel.setStyleName("employeeMenuPanel");


        grid.addComponent(mainMenuPanel);
        grid.addComponent(serviceListPanel);

        if(Objects.equals(MainSystem.getUserType(), "client")){
            grid.addComponent(clientMenuPanel);
        }
        else if(Objects.equals(MainSystem.getUserType(), "employee")){
            grid.addComponent(employeeMenuPanel);
        }

        grid.setSpacing(true);
        grid.setMargin(true);

        //Add Components
        addComponent(grid);
        setComponentAlignment(grid, Alignment.TOP_LEFT);
        setHeight("100%");
        setWidth("100%");

//        if(MainSystem.getUserID() == 0 || !MainSystem.getUserType().equals("client")){
//            UI.getCurrent().getNavigator().navigateTo("clientLogin");
//        }

    }
}
