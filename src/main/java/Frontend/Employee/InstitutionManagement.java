package Frontend.Employee;

import Backend.Car;
import Backend.Institution;
import Backend.MainSystem;
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
public class InstitutionManagement extends VerticalLayout implements View {

    private MainSystem mainSystem = new MainSystem();

    public InstitutionManagement(){

        //Grid Layout
        GridLayout grid = new GridLayout(3, 1);
        grid.setWidth("100%");
        grid.setColumnExpandRatio(0,1);
        grid.setColumnExpandRatio(1,3);
        grid.setColumnExpandRatio(2,1);

        List<Institution> institutionsData = null;

        try {
            institutionsData = mainSystem.getInstitutionDataFromDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        assert institutionsData != null;

        //region --------------------START INSTITUTION MANAGEMENT - TAB 1------------------------

        //Table - my cars

        Grid<Institution> institutionDataGrid = new Grid<>();

        institutionDataGrid.setSizeFull();
        institutionDataGrid.addStyleName("dataTable");
        institutionDataGrid.setHeight("100%");

        institutionDataGrid.addColumn(Institution::getName).setCaption("Nazwa placówki").setResizable(false);
        institutionDataGrid.addColumn(Institution::getAddress).setCaption("Opis placówki").setResizable(false);
        institutionDataGrid.addColumn(Institution::getDescription).setCaption("Adres placówki").setResizable(false);

        institutionDataGrid.setItems(institutionsData);

        //endregion --------------------END INSTITUTION MANAGEMENT - TAB 1------------------------

        //region --------------------START INSTITUTION MANAGEMENT - TAB 2------------------------

        //Add institution - management institution

        //UI Elements
        TextField institutionName = new TextField();
        TextField institutionAddress = new TextField();
        TextArea institutionDescription = new TextArea();

        //UI Elements - Button
        Button institutionButtonAdd = new Button("Dodaj placówkę");

        //Add Placeholders
        institutionName.setPlaceholder("Nazwa placówki...");
        institutionAddress.setPlaceholder("Adres placówki...");
        institutionDescription.setPlaceholder("Opis placówki...");

        //Add Horizontal layout - inputs

        //Row 1
        HorizontalLayout HLayoutInputsRow1 = new HorizontalLayout();

        HLayoutInputsRow1.addComponent(institutionName);
        HLayoutInputsRow1.addComponent(institutionAddress);

        HLayoutInputsRow1.setWidth("100%");

        //Row 2
        HorizontalLayout HLayoutInputsRow2 = new HorizontalLayout();

        HLayoutInputsRow2.addComponent(institutionDescription);

        HLayoutInputsRow2.setWidth("100%");

        //Add Horizontal layout - button
        HorizontalLayout HLayoutButton = new HorizontalLayout();

        HLayoutButton.addComponent(institutionButtonAdd);

        HLayoutButton.setWidth("100%");
        HLayoutButton.setHeight("100%");
        HLayoutButton.setComponentAlignment(institutionButtonAdd, Alignment.MIDDLE_CENTER);


        //Form Layout
        FormLayout formLayout = new FormLayout(HLayoutInputsRow1, HLayoutInputsRow2, HLayoutButton);

        //Styles
        institutionName.setWidth("100%");
        institutionAddress.setWidth("100%");
        institutionDescription.setWidth("100%");

        //Listeners
        institutionButtonAdd.addClickListener((Button.ClickListener) clickEvent -> {

            if(!institutionName.isEmpty() && !institutionAddress.isEmpty() && !institutionDescription.isEmpty()){

                try {
                    mainSystem.institutionAdd(
                            institutionName.getValue(),
                            institutionAddress.getValue(),
                            institutionDescription.getValue()
                    );

                    Notification.show("Placówka została dodana");

                    Page.getCurrent().reload();

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else{
                Notification.show("Pola nie mogą zostać puste");
            }
        });

        //endregion --------------------END INSTITUTION MANAGEMENT - TAB 2------------------------

        //region --------------------START INSTITUTION MANAGEMENT - TAB 3------------------------

        //Update institution - management institution

        //ComboBox
        ComboBox<Institution> institutionComboBoxUpdate = new ComboBox<>("Wybierz placówkę");

        institutionComboBoxUpdate.setItemCaptionGenerator(Institution::getName);
        institutionComboBoxUpdate.setSizeFull();

        institutionComboBoxUpdate.setItems(institutionsData);

        //UI Elements
        TextField institutionNameUpdate = new TextField();
        TextField institutionAddressUpdate = new TextField();
        TextArea institutionDescriptionUpdate = new TextArea();

        //UI Elements - Button
        Button institutionButtonUpdate = new Button("Aktualizuj placówkę");

        //Add Placeholders
        institutionNameUpdate.setPlaceholder("Nazwa placówki...");
        institutionAddressUpdate.setPlaceholder("Adres placówki...");
        institutionDescriptionUpdate.setPlaceholder("Opis placówki...");

        //Add Caption
        institutionNameUpdate.setCaption("Nazwa placówki");
        institutionAddressUpdate.setCaption("Adres placówki");
        institutionDescriptionUpdate.setCaption("Opis placówki");

        //Add Horizontal layout - inputs

        //Row 1
        HorizontalLayout HLayoutInputsRow1Update = new HorizontalLayout();

        HLayoutInputsRow1Update.addComponent(institutionComboBoxUpdate);

        HLayoutInputsRow1Update.setWidth("100%");

        //Row 2
        HorizontalLayout HLayoutInputsRow2Update = new HorizontalLayout();

        HLayoutInputsRow2Update.addComponent(institutionNameUpdate);
        HLayoutInputsRow2Update.addComponent(institutionAddressUpdate);

        HLayoutInputsRow2Update.setWidth("100%");

        //Row 3
        HorizontalLayout HLayoutInputsRow3Update = new HorizontalLayout();

        HLayoutInputsRow3Update.addComponent(institutionDescriptionUpdate);

        HLayoutInputsRow3Update.setWidth("100%");

        //Add Horizontal layout - button
        HorizontalLayout HLayoutButtonUpdate = new HorizontalLayout();

        HLayoutButtonUpdate.addComponent(institutionButtonUpdate);

        HLayoutButtonUpdate.setWidth("100%");
        HLayoutButtonUpdate.setHeight("100%");
        HLayoutButtonUpdate.setComponentAlignment(institutionButtonUpdate, Alignment.MIDDLE_CENTER);


        //Form Layout
        FormLayout formLayoutUpdate = new FormLayout(HLayoutInputsRow1Update, HLayoutInputsRow2Update, HLayoutInputsRow3Update, HLayoutButtonUpdate);


        //Styles
        institutionNameUpdate.setWidth("100%");
        institutionAddressUpdate.setWidth("100%");
        institutionDescriptionUpdate.setWidth("100%");

        //Listeners
        institutionButtonUpdate.addClickListener((Button.ClickListener) clickEvent -> {
            Institution element = institutionComboBoxUpdate.getValue();

            try {
                if(!institutionNameUpdate.isEmpty() && !institutionAddressUpdate.isEmpty() && !institutionDescriptionUpdate.isEmpty()){
                    mainSystem.institutionUpdate(
                            element.getId(),
                            institutionNameUpdate.getValue(),
                            institutionAddressUpdate.getValue(),
                            institutionDescriptionUpdate.getValue()
                    );

                    Notification.show("Placówka zaktualizowana");

                    Page.getCurrent().reload();
                } else{
                    Notification.show("Uzupełnij wszystkie pola");
                }
            } catch (NullPointerException e){
                Notification.show("Wybierz placówkę");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        institutionComboBoxUpdate.addValueChangeListener((HasValue.ValueChangeEvent<Institution> event) -> {
            Institution element = institutionComboBoxUpdate.getValue();

            institutionNameUpdate.setValue(element.getName());
            institutionAddressUpdate.setValue(element.getAddress());
            institutionDescriptionUpdate.setValue(element.getDescription());

        });

        //endregion --------------------END INSTITUTION MANAGEMENT - TAB 3------------------------

        //region --------------------START INSTITUTION MANAGEMENT - TAB 4------------------------

        //Delete car - my cars

        //ComboBox
        ComboBox<Institution> institutionComboBoxDelete = new ComboBox<>("Wybierz placówke");

        institutionComboBoxDelete.setItemCaptionGenerator(Institution::getName);
        institutionComboBoxDelete.setSizeFull();

        institutionComboBoxDelete.setItems(institutionsData);

        Button institutionButtonDelete = new Button("Usuń Placówkę");

        //Row 1
        HorizontalLayout HLayoutInputsRow1Delete = new HorizontalLayout();

        HLayoutInputsRow1Delete.addComponent(institutionComboBoxDelete);

        HLayoutInputsRow1Delete.setWidth("100%");

        //Add Horizontal layout - button
        HorizontalLayout HLayoutButtonDelete = new HorizontalLayout();

        HLayoutButtonDelete.addComponent(institutionButtonDelete);

        HLayoutButtonDelete.setWidth("100%");
        HLayoutButtonDelete.setHeight("100%");
        HLayoutButtonDelete.setComponentAlignment(institutionButtonDelete, Alignment.MIDDLE_CENTER);

        //Form Layout
        FormLayout formLayoutDelete = new FormLayout(HLayoutInputsRow1Delete, HLayoutButtonDelete);

        institutionButtonDelete.addClickListener((Button.ClickListener) clickEvent -> {
            Institution element = institutionComboBoxDelete.getValue();

            try {
                mainSystem.institutionDelete(
                        element.getId()
                );

                Notification.show("Placówka usunięta");

                Page.getCurrent().reload();
            } catch (NullPointerException e){
                Notification.show("Wybierz placówkę");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        //endregion --------------------END MY CAR - TAB 4------------------------

        //Tabs - my repairs view
        TabSheet institutionTabs = new TabSheet();

        institutionTabs.setWidth("100%");
        institutionTabs.addStyleName("tabsHeader");

        institutionTabs.addTab(institutionDataGrid).setCaption("Lista placówek");
        institutionTabs.addTab(formLayout).setCaption("Dodanie placówki");
        institutionTabs.addTab(formLayoutUpdate).setCaption("Aktualizacja placówki");
        institutionTabs.addTab(formLayoutDelete).setCaption("Usunięcie placówki");

        //Panel - my repairs
        Panel institutionListPanel = new Panel("Moje pojazdy", institutionTabs);
        institutionListPanel.setWidth("100%");
        institutionListPanel.setStyleName("dataListHolder");



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
        grid.addComponent(institutionListPanel);

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
