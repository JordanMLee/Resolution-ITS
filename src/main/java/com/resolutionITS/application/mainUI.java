package com.resolutionITS.application;

import com.resolutionITS.application.views.*;
import com.vaadin.annotations.Theme;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewDisplay;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.annotation.SpringViewDisplay;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;


/*
 * Contains code for app landing page
 * */

@Theme("valo")
@SpringUI
@SpringViewDisplay
public class mainUI extends UI implements ViewDisplay {
    public Label currentUser = new Label("");
    private Panel springViewDisplay;

    private VerticalLayout layout;

    @Override
    protected void init(VaadinRequest vaadinRequest) {

        final VerticalLayout root = new VerticalLayout();
        final HorizontalLayout top = new HorizontalLayout();

        root.setSizeUndefined();
        root.setMargin(true);

        setContent(root);

        final CssLayout navigationBar = new CssLayout();

        navigationBar.addStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
//        navigationBar.addComponent(navigationButton("Add New technician",AddNewTechnicianView.VIEW));
        navigationBar.addComponent(navigationButton("Add New Issue", AddNewIssueView.VIEW));
        navigationBar.addComponent(navigationButton("Search for Technicians", SearchForTechnicians.VIEW));
        navigationBar.addComponent(navigationButton("Technician Status", TechnicianStatusView.VIEW));
        navigationBar.addComponent(navigationButton("Technician Report", TechnicianReportView.VIEW));
        navigationBar.addComponent(navigationButton("Add New User", AddNewUserView.VIEW));
        navigationBar.addComponent(navigationButton("Logout", LoginView.VIEW));
        top.addComponents(navigationBar, currentUser);
        root.addComponent(top);


//        root.setComponentAlignment(top,Alignment.TOP_CENTER);

        springViewDisplay = new Panel();
        springViewDisplay.setSizeFull();

        root.addComponent(springViewDisplay);

//        root.setExpandRatio(springViewDisplay, 1.0f);
        root.setComponentAlignment(springViewDisplay, Alignment.MIDDLE_CENTER);

        //testing vertical layout
//        layout = new VerticalLayout();
//        root.addComponent(layout);
//        root.setComponentAlignment(layout,Alignment.TOP_CENTER);



    }

    private Button navigationButton(String caption, final String viewName) {
        Button button = new Button(caption);
        button.addStyleName(ValoTheme.BUTTON_SMALL);
        button.addClickListener(event -> getUI().getNavigator().navigateTo(viewName));
        return button;
    }

    @Override
    public void showView(View view) {
//        layout.addComponent((Component) view);
//        layout.setComponentAlignment((Component)view, Alignment.TOP_CENTER);


        springViewDisplay.setContent((Component) view);
//        springViewDisplay.setVisible(false);

    }


}
