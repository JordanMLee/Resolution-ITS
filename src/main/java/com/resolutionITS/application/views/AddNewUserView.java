package com.resolutionITS.application.views;

import com.resolutionITS.application.JPArepo.UserRepository;
import com.resolutionITS.application.entities.Users;
import com.resolutionITS.application.entities.Users2;
import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

@SpringView(name = AddNewUserView.VIEW)
public class AddNewUserView extends VerticalLayout implements View {
    public static final String VIEW = "AddNewUser";

    Label header = new Label("Add New User");
    private Button button;
    private Button delButton;

    @Autowired
    private UserRepository userRepository;

    public AddNewUserView() {
        button = new Button("add user");
        delButton = new Button("delete user");
    }


    @PostConstruct
    void init() {
        addComponent(button);
        addComponent(delButton);
        button.addClickListener((Button.ClickListener) clickEvent -> {
            Notification.show("button-clicked");
            createUser();

        });

        delButton.addClickListener((Button.ClickListener) clickEvet -> {
            deleteUser();
        });

    }

    private void deleteUser() {
        Users2 user = new Users2("testUser");
//        userRepository.delete(user);
    }

    private void createUser() {

        // Insert a new user
//        Users2 newUser = new Users2("testUser");
        Users newUser = new Users("jared", "jlee", "password");
        userRepository.save(newUser);
        Notification.show(newUser.toString() + " added to db");

    }


}
