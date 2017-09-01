package Frontend.Employee;

import Backend.Employee;
import Backend.MainSystem;
import Backend.Service;
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
public class EmployeeManagement extends VerticalLayout implements View {

    private MainSystem mainSystem = new MainSystem();

    public EmployeeManagement(){

        //Grid Layout
        GridLayout grid = new GridLayout(3, 1);
        grid.setWidth("100%");
        grid.setColumnExpandRatio(0,1);
        grid.setColumnExpandRatio(1,3);
        grid.setColumnExpandRatio(2,1);

        List<Employee> employeeData = null;

        try {
            employeeData = mainSystem.getEmployeeDataFromDatabaseAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        assert employeeData != null;

        //region --------------------START EMPLOYEE MANAGEMENT - TAB 1------------------------

        //Table

        Grid<Employee> employeeDataGrid = new Grid<>();

        employeeDataGrid.setSizeFull();
        employeeDataGrid.addStyleName("dataTable");
        employeeDataGrid.setHeight("100%");

        employeeDataGrid.addColumn(Employee::getName).setCaption("Imię").setResizable(false);
        employeeDataGrid.addColumn(Employee::getLastName).setCaption("Nazwisko").setResizable(false);
        employeeDataGrid.addColumn(Employee::getAddress).setCaption("Adres").setResizable(false);
        employeeDataGrid.addColumn(Employee::getPosition).setCaption("Stanowisko").setResizable(false);
        employeeDataGrid.addColumn(Employee::getSalary).setCaption("Wynagrodzenie").setResizable(false);
        employeeDataGrid.addColumn(Employee::getLogin).setCaption("Login").setResizable(false);

        employeeDataGrid.setItems(employeeData);

        //endregion --------------------END EMPLOYEE MANAGEMENT - TAB 1------------------------

        //region --------------------START EMPLOYEE MANAGEMENT - TAB 2------------------------

        //Add employee - management employee

        //UI Elements
        TextField employeeName = new TextField();
        TextField employeeLastName = new TextField();
        TextField employeeAddress = new TextField();
        TextField employeePosition = new TextField();
        TextField employeeLogin = new TextField();
        TextField employeePaswword = new TextField();
        TextField employeeSalary = new TextField();

        //UI Elements - Button
        Button employeeButtonAdd = new Button("Dodaj pracownika");

        //Add Placeholders
        employeeName.setPlaceholder("Imię...");
        employeeLastName.setPlaceholder("Nazwisko...");
        employeeAddress.setPlaceholder("Adres...");
        employeePosition.setPlaceholder("Stanowisko...");
        employeeLogin.setPlaceholder("Login...");
        employeePaswword.setPlaceholder("Hasło...");
        employeeSalary.setPlaceholder("Wynagrodzenie...");

        //Add Horizontal layout - inputs

        //Row 1
        HorizontalLayout HLayoutInputsRow1 = new HorizontalLayout();

        HLayoutInputsRow1.addComponent(employeeName);
        HLayoutInputsRow1.addComponent(employeeLastName);

        HLayoutInputsRow1.setWidth("100%");

        //Row 2
        HorizontalLayout HLayoutInputsRow2 = new HorizontalLayout();

        HLayoutInputsRow2.addComponent(employeeAddress);
        HLayoutInputsRow2.addComponent(employeePosition);

        HLayoutInputsRow2.setWidth("100%");

        //Row 3
        HorizontalLayout HLayoutInputsRow3 = new HorizontalLayout();

        HLayoutInputsRow3.addComponent(employeeLogin);
        HLayoutInputsRow3.addComponent(employeePaswword);

        HLayoutInputsRow3.setWidth("100%");

        //Row 4
        HorizontalLayout HLayoutInputsRow4 = new HorizontalLayout();

        HLayoutInputsRow4.addComponent(employeeSalary);

        HLayoutInputsRow4.setWidth("100%");

        //Add Horizontal layout - button
        HorizontalLayout HLayoutButton = new HorizontalLayout();

        HLayoutButton.addComponent(employeeButtonAdd);

        HLayoutButton.setWidth("100%");
        HLayoutButton.setHeight("100%");
        HLayoutButton.setComponentAlignment(employeeButtonAdd, Alignment.MIDDLE_CENTER);


        //Form Layout
        FormLayout formLayout = new FormLayout(HLayoutInputsRow1, HLayoutInputsRow2, HLayoutInputsRow3, HLayoutInputsRow4, HLayoutButton);

        //Styles
        employeeName.setWidth("100%");
        employeeLastName.setWidth("100%");
        employeeAddress.setWidth("100%");
        employeePosition.setWidth("100%");
        employeeLogin.setWidth("100%");
        employeePaswword.setWidth("100%");
        employeeSalary.setWidth("100%");

        //Listeners
        employeeButtonAdd.addClickListener((Button.ClickListener) clickEvent -> {

            if(!employeeName.isEmpty() && !employeeLastName.isEmpty() && !employeeAddress.isEmpty() && !employeePosition.isEmpty() && !employeeLogin.isEmpty() && !employeePaswword.isEmpty() && !employeeSalary.isEmpty()){

                try {
                    mainSystem.addEmployee(
                            employeeName.getValue(),
                            employeeLastName.getValue(),
                            employeeAddress.getValue(),
                            employeePosition.getValue(),
                            employeeLogin.getValue(),
                            employeePaswword.getValue(),
                            employeeSalary.getValue()
                    );

                    Notification.show("Pracownik został dodany");

                    Page.getCurrent().reload();

                } catch (SQLException | UnsupportedEncodingException | NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
            } else{
                Notification.show("Pola nie mogą zostać puste");
            }
        });

        //endregion --------------------END EMPLOYEE MANAGEMENT - TAB 2------------------------

        //region --------------------START EMPLOYEE MANAGEMENT - TAB 3------------------------

        //Update employee - management employee

        //ComboBox
        ComboBox<Employee> employeeComboBoxUpdate = new ComboBox<>("Wybierz pracownika");

        employeeComboBoxUpdate.setItemCaptionGenerator(Employee::getFullName);
        employeeComboBoxUpdate.setSizeFull();

        employeeComboBoxUpdate.setItems(employeeData);

        //UI Elements
        TextField employeeNameUpdate = new TextField();
        TextField employeeLastNameUpdate = new TextField();
        TextField employeeAddressUpdate = new TextField();
        TextField employeePositionUpdate = new TextField();
        TextField employeeLoginUpdate = new TextField();
        TextField employeePaswwordUpdate = new TextField();
        TextField employeeSalaryUpdate = new TextField();

        //UI Elements - Button
        Button employeeButtonUpdate = new Button("Aktualizuj pracownika");

        //Add Placeholders
        employeeNameUpdate.setPlaceholder("Imię...");
        employeeLastNameUpdate.setPlaceholder("Nazwisko...");
        employeeAddressUpdate.setPlaceholder("Adres...");
        employeePositionUpdate.setPlaceholder("Stanowisko...");
        employeeLoginUpdate.setPlaceholder("Login...");
        employeePaswwordUpdate.setPlaceholder("Hasło...");
        employeeSalaryUpdate.setPlaceholder("Wynagrodzenie...");

        //Add Caption
        employeeNameUpdate.setCaption("Imię");
        employeeLastNameUpdate.setCaption("Nazwisko");
        employeeAddressUpdate.setCaption("Adres");
        employeePositionUpdate.setCaption("Stanowisko");
        employeeLoginUpdate.setCaption("Login");
        employeePaswwordUpdate.setCaption("Hasło");
        employeeSalaryUpdate.setCaption("Wynagrodzenie");

        //Add Horizontal layout - inputs

        //Row 1
        HorizontalLayout HLayoutInputsRow1Update = new HorizontalLayout();

        HLayoutInputsRow1Update.addComponent(employeeComboBoxUpdate);

        HLayoutInputsRow1Update.setWidth("100%");

        //Row 2
        HorizontalLayout HLayoutInputsRow2Update = new HorizontalLayout();

        HLayoutInputsRow2Update.addComponent(employeeNameUpdate);
        HLayoutInputsRow2Update.addComponent(employeeLastNameUpdate);

        HLayoutInputsRow2Update.setWidth("100%");

        //Row 3
        HorizontalLayout HLayoutInputsRow3Update = new HorizontalLayout();

        HLayoutInputsRow3Update.addComponent(employeeAddressUpdate);
        HLayoutInputsRow3Update.addComponent(employeePositionUpdate);

        HLayoutInputsRow3Update.setWidth("100%");

        //Row 4
        HorizontalLayout HLayoutInputsRow4Update = new HorizontalLayout();

        HLayoutInputsRow4Update.addComponent(employeeLoginUpdate);
        HLayoutInputsRow4Update.addComponent(employeePaswwordUpdate);

        HLayoutInputsRow4Update.setWidth("100%");

        //Row 5
        HorizontalLayout HLayoutInputsRow5Update = new HorizontalLayout();

        HLayoutInputsRow5Update.addComponent(employeeSalaryUpdate);

        HLayoutInputsRow5Update.setWidth("100%");

        //Add Horizontal layout - button
        HorizontalLayout HLayoutButtonUpdate = new HorizontalLayout();

        HLayoutButtonUpdate.addComponent(employeeButtonUpdate);

        HLayoutButtonUpdate.setWidth("100%");
        HLayoutButtonUpdate.setHeight("100%");
        HLayoutButtonUpdate.setComponentAlignment(employeeButtonUpdate, Alignment.MIDDLE_CENTER);


        //Form Layout
        FormLayout formLayoutUpdate = new FormLayout(HLayoutInputsRow1Update, HLayoutInputsRow2Update, HLayoutInputsRow3Update, HLayoutInputsRow4Update, HLayoutInputsRow5Update, HLayoutButtonUpdate);


        //Styles
        employeeNameUpdate.setWidth("100%");
        employeeLastNameUpdate.setWidth("100%");
        employeeAddressUpdate.setWidth("100%");
        employeePositionUpdate.setWidth("100%");
        employeeLoginUpdate.setWidth("100%");
        employeePaswwordUpdate.setWidth("100%");
        employeeSalaryUpdate.setWidth("100%");

        //Listeners
        employeeButtonUpdate.addClickListener((Button.ClickListener) clickEvent -> {
            Employee element = employeeComboBoxUpdate.getValue();

            try {
                if(!employeeNameUpdate.isEmpty() && !employeeLastNameUpdate.isEmpty() && !employeeAddressUpdate.isEmpty() && !employeePositionUpdate.isEmpty() && !employeeLoginUpdate.isEmpty() && !employeePaswwordUpdate.isEmpty() && !employeeSalaryUpdate.isEmpty()){
                    mainSystem.employeUpdate(
                            element.getId(),
                            employeeNameUpdate.getValue(),
                            employeeLastNameUpdate.getValue(),
                            employeeAddressUpdate.getValue(),
                            employeePositionUpdate.getValue(),
                            employeeLoginUpdate.getValue(),
                            employeePaswwordUpdate.getValue(),
                            employeeSalaryUpdate.getValue()
                    );

                    Notification.show("Usługa zaktualizowana");

                    Page.getCurrent().reload();
                } else{
                    Notification.show("Uzupełnij wszystkie pola");
                }
            } catch (NullPointerException e){
                Notification.show("Wybierz usługę");
            } catch (SQLException | UnsupportedEncodingException | NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        });

        employeeComboBoxUpdate.addValueChangeListener((HasValue.ValueChangeEvent<Employee> event) -> {
            Employee element = employeeComboBoxUpdate.getValue();

            employeeNameUpdate.setValue(element.getName());
            employeeLastNameUpdate.setValue(element.getLastName());
            employeeAddressUpdate.setValue(element.getAddress());
            employeePositionUpdate.setValue(element.getPosition());
            employeeSalaryUpdate.setValue(element.getSalary());
            employeeLoginUpdate.setValue(element.getLogin());
            employeePaswwordUpdate.setValue(element.getPassword());

        });

        //endregion --------------------END EMPLOYEE MANAGEMENT - TAB 3------------------------

        //region --------------------START EMPLOYEE MANAGEMENT - TAB 4------------------------

        //Delete car - my cars

        //ComboBox
        ComboBox<Employee> employeeComboBoxDelete = new ComboBox<>("Wybierz pracownika");

        employeeComboBoxDelete.setItemCaptionGenerator(Employee::getFullName);
        employeeComboBoxDelete.setSizeFull();

        employeeComboBoxDelete.setItems(employeeData);

        Button employeeButtonDelete = new Button("Usuń pracownika");

        //Row 1
        HorizontalLayout HLayoutInputsRow1Delete = new HorizontalLayout();

        HLayoutInputsRow1Delete.addComponent(employeeComboBoxDelete);

        HLayoutInputsRow1Delete.setWidth("100%");

        //Add Horizontal layout - button
        HorizontalLayout HLayoutButtonDelete = new HorizontalLayout();

        HLayoutButtonDelete.addComponent(employeeButtonDelete);

        HLayoutButtonDelete.setWidth("100%");
        HLayoutButtonDelete.setHeight("100%");
        HLayoutButtonDelete.setComponentAlignment(employeeButtonDelete, Alignment.MIDDLE_CENTER);

        //Form Layout
        FormLayout formLayoutDelete = new FormLayout(HLayoutInputsRow1Delete, HLayoutButtonDelete);

        employeeButtonDelete.addClickListener((Button.ClickListener) clickEvent -> {
            Employee element = employeeComboBoxDelete.getValue();

            try {
                mainSystem.employeeDelete(
                        element.getId()
                );

                Notification.show("Pracownik usunięty");

                Page.getCurrent().reload();
            } catch (NullPointerException e){
                Notification.show("Wybierz Pracownika");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        //endregion --------------------END EMPLOYEE MANAGEMENT - TAB 4------------------------

        //Tabs - my repairs view
        TabSheet serviceTabs = new TabSheet();

        serviceTabs.setWidth("100%");
        serviceTabs.addStyleName("tabsHeader");

        serviceTabs.addTab(employeeDataGrid).setCaption("Lista pracowników");
        serviceTabs.addTab(formLayout).setCaption("Dodanie pracownika");
        serviceTabs.addTab(formLayoutUpdate).setCaption("Aktualizacja pracownika");
        serviceTabs.addTab(formLayoutDelete).setCaption("Usunięcie pracownika");

        //Panel - my repairs
        Panel serviceListPanel = new Panel("Zarządzanie pracownikami", serviceTabs);
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

    }
}
