package com.resolutionITS.application.views;

import com.resolutionITS.application.JPArepo.UserRepository;
import com.resolutionITS.application.entities.Users;
import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import javax.annotation.PostConstruct;

@SpringView(name = AddNewUserView.VIEW)
public class AddNewUserView extends VerticalLayout implements View {
    public static final String VIEW = "AddNewUser";

    Label header = new Label("Add New User");
    private Button submitButton;
    private Button cancelButton;
    private VerticalLayout verticalLayout;
    private HorizontalLayout horizontalLayout;
    private TextField nameBox;
    private TextField userNameBox;
    private TextField passWordBox;

    @Autowired
    private UserRepository userRepository;

    public AddNewUserView() {
        verticalLayout = new VerticalLayout();

        nameBox = new TextField("Enter your name");
        userNameBox = new TextField("Enter your username");
        passWordBox = new TextField("Enter your password");

        horizontalLayout = new HorizontalLayout();


        submitButton = new Button("Add User");
        cancelButton = new Button("Cancel");
    }


    @PostConstruct
    void init() {
        addComponent(verticalLayout);
        setComponentAlignment(verticalLayout, Alignment.TOP_CENTER);
        verticalLayout.addComponent(new Label("Add New Users"));
        verticalLayout.addComponents(nameBox, userNameBox, passWordBox);
        horizontalLayout.addComponents(cancelButton, submitButton);
        verticalLayout.addComponent(horizontalLayout);

        submitButton.addClickListener((Button.ClickListener) clickEvent -> {

            createUser();

        });

        cancelButton.addClickListener((Button.ClickListener) clickEvent -> {

        });

    }



    private void createUser() {

        // Insert a new user
        if (nameBox.isEmpty() || userNameBox.isEmpty() || passWordBox.isEmpty()) {
            Notification.show("Error, missing data!");
        } else {
            Users newUser = new Users(nameBox.getValue(), userNameBox.getValue(), passWordBox.getValue());

            try {
                userRepository.save(newUser);
                Notification.show(newUser.toString() + " added to db");
            } catch (DataIntegrityViolationException d) {
                Notification.show("Error " + newUser.toString() + " has already registered!");
            }
        }

    }


}
