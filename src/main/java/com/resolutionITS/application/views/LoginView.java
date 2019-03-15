package com.resolutionITS.application.views;

import com.resolutionITS.application.entities.Tier1;
import com.resolutionITS.application.entities.Tier2;
import com.resolutionITS.application.entities.Tier3;
import com.resolutionITS.application.entities.Users;
import com.resolutionITS.application.repos.AuthenticationRepo;
import com.vaadin.event.ShortcutAction;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.VaadinSession;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

@SpringView(name = LoginView.VIEW)
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
    private Tier1 tier1;


    private Tier2 tier2;

    private Tier3 govt;

//    private VerticalLayout verticalLayout;

    @PostConstruct
    void init() {
//        verticalLayout = new VerticalLayout();
//        verticalLayout.addComponentsAndExpand(username,password,login);
//        addComponent(verticalLayout);
//        setComponentAlignment(verticalLayout,Alignment.TOP_CENTER);

        addComponents(username, password, login);
        setComponentAlignment(username, Alignment.TOP_CENTER);
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
//                try {

                    user = authenticationRepo.findUserByUsername(username.getValue());
                    if (user.getPassword().equals(password.getValue())) {

                        VaadinSession.getCurrent().setAttribute("user", user.getUsername());

//                        Notification.show(getSession().getAttribute("user").toString());

                        // switch changes welcome banner based on usertype (Table Users)
                        switch (user.getUsertype()) {

                            case "user":
//                                    welcomeLabel.setValue("Welcome : " + user.getName());
                                // load seSearchForTechnicians
                                Notification.show("Welcome : " + getSession().getAttribute("user").toString());
                                getUI().getNavigator().navigateTo(SearchForTechnicians.VIEW);
                                break;
                            case "tier1":
                                tier1 = authenticationRepo.findtier1(username.getValue());
                                welcomeLabel.setValue("Welcome: " + user.getName() +
                                        " [Headquarters: " + tier1.getHq() + " | Employees: "
                                        + tier1.getEmployeecnt() + " ]");
                                break;
                            case "tier2":
                                tier2 = authenticationRepo.findtier2(username.getValue());
                                welcomeLabel.setValue("Welcome: " + user.getName() +
                                        " [Category: " + tier2.getCategory() + " ]");
                                break;
                            case "tier3":
                                govt = authenticationRepo.findGovt(username.getValue());
                                welcomeLabel.setValue("Welcome: " + user.getName() +
                                        "[Agency Office: " + govt.getAgencyoffice() + " ]");
                                break;

                        }
                    } else {
                        //getViewComponent().setVisible(false);
                        welcomeLabel.setValue("Did you forget your password?");
                    }
//                } catch (EmptyResultDataAccessException e) {
//                    System.out.println("The username and password were not found in the database\n");
//                    welcomeLabel.setValue("ACCESS DENIED");
//                }
            }
        }); // end click listener
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
    }
}
