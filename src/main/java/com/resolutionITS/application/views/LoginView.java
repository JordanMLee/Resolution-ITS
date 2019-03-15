package com.resolutionITS.application.views;

import com.resolutionITS.application.entities.*;
import com.resolutionITS.application.repos.AuthenticationRepo;
import com.vaadin.event.ShortcutAction;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.VaadinSession;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import com.vaadin.ui.declarative.converters.ShortcutKeyMapper;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;

import javax.annotation.PostConstruct;

@SpringView(name= LoginView.VIEW)
public class LoginView extends VerticalLayout implements View {

    public static final String VIEW = "";

    @Autowired
    AuthenticationRepo authenticationRepo;
    private TextField username = new TextField("Username");
    private PasswordField password = new PasswordField("Password");
    private Button login = new Button("Login");


    private Label error = new Label("Login Error");

    private Label welcomeLabel = new Label("");

    private Users user;
    private Company company;


    private Municipality municipality;

    private GovtImpl govt;

//    private VerticalLayout verticalLayout;

    @PostConstruct
    void init() {
//        verticalLayout = new VerticalLayout();
//        verticalLayout.addComponentsAndExpand(username,password,login);
//        addComponent(verticalLayout);
//        setComponentAlignment(verticalLayout,Alignment.TOP_CENTER);

        addComponents(username,password,login);
        setComponentAlignment(username,Alignment.TOP_CENTER);
        setComponentAlignment(password, Alignment.TOP_CENTER);
        setComponentAlignment(login, Alignment.TOP_CENTER);
        error.setVisible(false);
        addComponent(error);
        addComponent(welcomeLabel);

        // the following implements login authentication with database
        // if the username is found but the password is incorrect, then
        // system notifies user, otherwise access is denied
        login.setClickShortcut(ShortcutAction.KeyCode.ENTER); // Bind ENTER key to the login button
        login.addStyleName(ValoTheme.BUTTON_PRIMARY);
        login.addClickListener((Button.ClickListener) clickEvent -> {

            if (!username.isEmpty()) {
                try {

                    user = authenticationRepo.findUserByUsername(username.getValue());
                    if (user.getPassword().equals(password.getValue())) {

                        VaadinSession.getCurrent().setAttribute("user", user.getUsername());

                        //Notification.show(getSession().getAttribute("user").toString());

                        // switch changes welcome banner based on usertype (Table Users)
                        switch (user.getUsertype()) {
                            case "individual":
//                                    welcomeLabel.setValue("Welcome : " + user.getName());
                                // load search for resources
                                Notification.show("Welcome : " + getSession().getAttribute("user").toString());
                                getUI().getNavigator().navigateTo(SearchForResources.VIEW);
                                break;
                            case "company":
                                company = authenticationRepo.findCompany(username.getValue());
                                welcomeLabel.setValue("Welcome: " + user.getName() +
                                        " [Headquarters: " + company.getHq() + " | Employees: "
                                        + company.getEmployeecnt() + " ]");
                                break;
                            case "municipality":
                                municipality = authenticationRepo.findMunicipality(username.getValue());
                                welcomeLabel.setValue("Welcome: " + user.getName() +
                                        " [Category: " + municipality.getCategory() + " ]");
                                break;
                            case "govt":
                                govt = authenticationRepo.findGovt(username.getValue());
                                welcomeLabel.setValue("Welcome: " + user.getName() +
                                        "[Agency Office: " + govt.getAgencyoffice() + " ]");
                                break;

                        }
                    } else {
                        //getViewComponent().setVisible(false);
                        welcomeLabel.setValue("Did you forget your password?");
                    }
                } catch (EmptyResultDataAccessException e) {
                    System.out.println("The username and password were not found in the database\n");
                    welcomeLabel.setValue("ACCESS DENIED");
                }
            }
        }); // end click listener
    }
    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) { }
}
