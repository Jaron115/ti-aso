package Frontend.Client;

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

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

/**
 * Created by Jaron on 25.08.2017.
 */
public class MyCars extends VerticalLayout implements View{

    private MainSystem mainSystem = new MainSystem();

    public MyCars(){

        //Grid Layout
        GridLayout grid = new GridLayout(3, 1);
        grid.setWidth("100%");
        grid.setColumnExpandRatio(0,1);
        grid.setColumnExpandRatio(1,3);
        grid.setColumnExpandRatio(2,1);

        List<Car> carData = null;

        try {
            carData = mainSystem.getCarDataFromDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        assert carData != null;

        //region --------------------START MY CAR - TAB 1------------------------

        //Table - my cars

        Grid<Car> carDataGrid = new Grid<>();

        carDataGrid.setSizeFull();
        carDataGrid.addStyleName("dataTable");
        carDataGrid.setHeight("100%");

        carDataGrid.addColumn(Car::getBrand).setCaption("Marka").setResizable(false);
        carDataGrid.addColumn(Car::getModel).setCaption("Model").setResizable(false);
        carDataGrid.addColumn(Car::getYear).setCaption("Rok produkcji").setResizable(false);
        carDataGrid.addColumn(Car::getColor).setCaption("Kolor").setResizable(false);

        carDataGrid.setItems(carData);

        //endregion --------------------END MY CAR - TAB 1------------------------

        //region --------------------START MY CAR - TAB 2------------------------

        //Add car - my cars

        //UI Elements
        TextField carBrand = new TextField();
        TextField carModel = new TextField();
        TextField carYear = new TextField();
        TextField carColor = new TextField();

        //UI Elements - Button
        Button carButtonAdd = new Button("Dodaj samochód");

        //Add Placeholders
        carBrand.setPlaceholder("Marka...");
        carModel.setPlaceholder("Model...");
        carYear.setPlaceholder("Rok produkcji...");
        carColor.setPlaceholder("Kolor...");

        //Add Horizontal layout - inputs

        //Row 1
        HorizontalLayout HLayoutInputsRow1 = new HorizontalLayout();

        HLayoutInputsRow1.addComponent(carBrand);
        HLayoutInputsRow1.addComponent(carModel);

        HLayoutInputsRow1.setWidth("100%");

        //Row 2
        HorizontalLayout HLayoutInputsRow2 = new HorizontalLayout();

        HLayoutInputsRow2.addComponent(carYear);
        HLayoutInputsRow2.addComponent(carColor);

        HLayoutInputsRow2.setWidth("100%");

        //Add Horizontal layout - button
        HorizontalLayout HLayoutButton = new HorizontalLayout();

        HLayoutButton.addComponent(carButtonAdd);

        HLayoutButton.setWidth("100%");
        HLayoutButton.setHeight("100%");
        HLayoutButton.setComponentAlignment(carButtonAdd, Alignment.MIDDLE_CENTER);


        //Form Layout
        FormLayout formLayout = new FormLayout(HLayoutInputsRow1, HLayoutInputsRow2, HLayoutButton);

        //Styles
        carBrand.setWidth("100%");
        carModel.setWidth("100%");
        carYear.setWidth("100%");
        carColor.setWidth("100%");

        //Listeners
        carButtonAdd.addClickListener((Button.ClickListener) clickEvent -> {

            if(!carBrand.isEmpty() && !carModel.isEmpty() && !carYear.isEmpty() && !carColor.isEmpty()){

                try {
                    mainSystem.clientCar(
                            carBrand.getValue(),
                            carModel.getValue(),
                            carYear.getValue(),
                            carColor.getValue()
                    );

                    Notification.show("Samochód został dodany");

                    Page.getCurrent().reload();

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else{
                Notification.show("Pola nie mogą zostać puste");
            }
        });

        //endregion --------------------END MY CAR - TAB 2------------------------

        //region --------------------START MY CAR - TAB 3------------------------

        //Update car - my cars

        //ComboBox
        ComboBox<Car> carComboBoxUpdate = new ComboBox<>("Wybierz samochód");

        carComboBoxUpdate.setItemCaptionGenerator(Car::carData);
        carComboBoxUpdate.setSizeFull();

        carComboBoxUpdate.setItems(carData);

        //UI Elements
        TextField carBrandUpdate = new TextField();
        TextField carModelUpdate = new TextField();
        TextField carYearUpdate = new TextField();
        TextField carColorUpdate = new TextField();

        //UI Elements - Button
        Button carButtonUpdate = new Button("Aktualizuj samochód");

        //Add Placeholders
        carBrandUpdate.setPlaceholder("Marka...");
        carBrandUpdate.setPlaceholder("Marka...");
        carModelUpdate.setPlaceholder("Model...");
        carYearUpdate.setPlaceholder("Rok produkcji...");
        carColorUpdate.setPlaceholder("Kolor...");

        //Add Caption
        carBrandUpdate.setCaption("Marka");
        carBrandUpdate.setCaption("Marka");
        carModelUpdate.setCaption("Model");
        carYearUpdate.setCaption("Rok produkcji");
        carColorUpdate.setCaption("Kolor");

        //Add Horizontal layout - inputs

        //Row 1
        HorizontalLayout HLayoutInputsRow1Update = new HorizontalLayout();

        HLayoutInputsRow1Update.addComponent(carComboBoxUpdate);

        HLayoutInputsRow1Update.setWidth("100%");

        //Row 2
        HorizontalLayout HLayoutInputsRow2Update = new HorizontalLayout();

        HLayoutInputsRow2Update.addComponent(carBrandUpdate);
        HLayoutInputsRow2Update.addComponent(carModelUpdate);

        HLayoutInputsRow2Update.setWidth("100%");

        //Row 3
        HorizontalLayout HLayoutInputsRow3Update = new HorizontalLayout();

        HLayoutInputsRow3Update.addComponent(carYearUpdate);
        HLayoutInputsRow3Update.addComponent(carColorUpdate);

        HLayoutInputsRow3Update.setWidth("100%");

        //Add Horizontal layout - button
        HorizontalLayout HLayoutButtonUpdate = new HorizontalLayout();

        HLayoutButtonUpdate.addComponent(carButtonUpdate);

        HLayoutButtonUpdate.setWidth("100%");
        HLayoutButtonUpdate.setHeight("100%");
        HLayoutButtonUpdate.setComponentAlignment(carButtonUpdate, Alignment.MIDDLE_CENTER);


        //Form Layout
        FormLayout formLayoutUpdate = new FormLayout(HLayoutInputsRow1Update, HLayoutInputsRow2Update, HLayoutInputsRow3Update, HLayoutButtonUpdate);


        //Styles
        carBrandUpdate.setWidth("100%");
        carModelUpdate.setWidth("100%");
        carYearUpdate.setWidth("100%");
        carColorUpdate.setWidth("100%");

        //Listeners
        carButtonUpdate.addClickListener((Button.ClickListener) clickEvent -> {
            Car element = carComboBoxUpdate.getValue();

            try {
                if(!carBrandUpdate.isEmpty() && !carModelUpdate.isEmpty() && !carYearUpdate.isEmpty() && !carColorUpdate.isEmpty()){
                    mainSystem.clientCarUpdate(
                            element.getId(),
                            carBrandUpdate.getValue(),
                            carModelUpdate.getValue(),
                            carYearUpdate.getValue(),
                            carColorUpdate.getValue()
                    );

                    Notification.show("Samochód zaktualizowany");

                    Page.getCurrent().reload();
                } else{
                    Notification.show("Uzupełnij wszystkie pola");
                }
            } catch (NullPointerException e){
                Notification.show("Wybierz samochód");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        carComboBoxUpdate.addValueChangeListener((HasValue.ValueChangeEvent<Car> event) -> {
           Car element = carComboBoxUpdate.getValue();

           carBrandUpdate.setValue(element.getBrand());
           carModelUpdate.setValue(element.getModel());
           carYearUpdate.setValue(element.getYear());
           carColorUpdate.setValue(element.getColor());

        });

        //endregion --------------------END MY CAR - TAB 3------------------------

        //region --------------------START MY CAR - TAB 4------------------------

        //Delete car - my cars

        //ComboBox
        ComboBox<Car> carComboBoxDelete = new ComboBox<>("Wybierz samochód");

        carComboBoxDelete.setItemCaptionGenerator(Car::carData);
        carComboBoxDelete.setSizeFull();

        carComboBoxDelete.setItems(carData);

        Button carButtonDelete = new Button("Usuń samochód");

        //Row 1
        HorizontalLayout HLayoutInputsRow1Delete = new HorizontalLayout();

        HLayoutInputsRow1Delete.addComponent(carComboBoxDelete);

        HLayoutInputsRow1Delete.setWidth("100%");

        //Add Horizontal layout - button
        HorizontalLayout HLayoutButtonDelete = new HorizontalLayout();

        HLayoutButtonDelete.addComponent(carButtonDelete);

        HLayoutButtonDelete.setWidth("100%");
        HLayoutButtonDelete.setHeight("100%");
        HLayoutButtonDelete.setComponentAlignment(carButtonDelete, Alignment.MIDDLE_CENTER);

        //Form Layout
        FormLayout formLayoutDelete = new FormLayout(HLayoutInputsRow1Delete, HLayoutButtonDelete);

        carButtonDelete.addClickListener((Button.ClickListener) clickEvent -> {
            Car element = carComboBoxDelete.getValue();

            try {
                mainSystem.clientCarDelete(
                        element.getId()
                );

                Notification.show("Samochód usunięty");

                Page.getCurrent().reload();
            } catch (NullPointerException e){
                Notification.show("Wybierz samochód");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        //endregion --------------------END MY CAR - TAB 4------------------------

        //Tabs - my repairs view
        TabSheet carsTabs = new TabSheet();

        carsTabs.setWidth("100%");
        carsTabs.addStyleName("tabsHeader");

        carsTabs.addTab(carDataGrid).setCaption("Moje pojazdy");
        carsTabs.addTab(formLayout).setCaption("Dodaj pojazd");
        carsTabs.addTab(formLayoutUpdate).setCaption("Aktualizuj pojazd");
        carsTabs.addTab(formLayoutDelete).setCaption("Usuń pojazd");

        //Panel - my repairs
        Panel institutionListPanel = new Panel("Moje pojazdy", carsTabs);
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
