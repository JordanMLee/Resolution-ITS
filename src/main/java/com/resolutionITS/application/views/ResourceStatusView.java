package com.resolutionITS.application.views;

import com.resolutionITS.application.entities.Request;
import com.resolutionITS.application.entities.ResourceStatus;
import com.resolutionITS.application.repos.RequestRepo;
import com.resolutionITS.application.repos.ResourceStatusRepo;
import com.vaadin.navigator.View;
import com.vaadin.server.VaadinSession;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import javafx.scene.control.SingleSelectionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import javax.annotation.PostConstruct;
import java.sql.Date;
import java.util.List;

@SpringView(name = ResourceStatusView.VIEW)
public class ResourceStatusView extends VerticalLayout implements View {
    public static final String VIEW = "ResourceStatus";

    @Autowired
    private ResourceStatusRepo resourceStatusRepo;

    @Autowired
    private RequestRepo requestRepo;
    private Request action;

    private Grid<ResourceStatus> resourcesInUseGrid;
    private Grid<ResourceStatus> myRequestedResourcesGrid;
    private Grid<ResourceStatus> resourceRequestsGrid;

    List<ResourceStatus> resourcesInUseList;
    List<ResourceStatus> myRequestedResourcesList;
    List<ResourceStatus> resourceRequestsList;

    private Button viewResourceStatusBtn;
    HorizontalLayout btnLayout = new HorizontalLayout();
    HorizontalLayout txtlLayout = new HorizontalLayout();
    private Button deployResourceBtn;
    private Button returnResourceBtn;
    private Button rejectRequestBtn;
    private Button cancelYourRequestBtn;

    private Label label = new Label("");

    private TextField resourceidTxt;
    private TextField incidentidTxt;
    private TextField ownerTxt;
    private TextField startdateTxt;


    SqlParameterSource username;

    SingleSelectionModel<Request> requestSelect;

    @PostConstruct
    void init() {
        addComponent(new Label("Resource Status"));

        viewResourceStatusBtn.addClickListener(clickEvent -> {
            username = new MapSqlParameterSource().addValue("username",
                    VaadinSession.getCurrent().getAttribute("user").toString());

            //bind data
            resourcesInUseList = resourceStatusRepo.getResourcesInUse(username);
            resourcesInUseGrid.setItems(resourcesInUseList);

            myRequestedResourcesList = resourceStatusRepo.getMyRequestedResources(username);
            myRequestedResourcesGrid.setItems(myRequestedResourcesList);

            resourceRequestsList = resourceStatusRepo.getResourceRequests(username);
            resourceRequestsGrid.setItems(resourceRequestsList);
            label.setValue("");
        });

        deployResourceBtn.addClickListener(clickEvent -> {
            if (!resourceidTxt.isEmpty() && !ownerTxt.isEmpty() && !incidentidTxt.isEmpty() && !incidentidTxt.isEmpty()) {
                action = requestRepo.getRequest(Integer.valueOf(resourceidTxt.getValue()), ownerTxt.getValue(),
                        Integer.valueOf(incidentidTxt.getValue()));
                requestRepo.deployResource(action, Date.valueOf(startdateTxt.getValue()));
                label.setValue(action.toString() + " has been selected and will start work on " + startdateTxt.getValue());
            } else {
                label.setValue("Please enter values in all of the above textboxes");
            }
        });

        // oppposite off deploy (above)
        returnResourceBtn.addClickListener(clickEvent -> {
            if (!resourceidTxt.isEmpty()) {
                requestRepo.returnResource(Integer.valueOf(resourceidTxt.getValue()));
                label.setValue("resource returned, click View Resource Status to view update");
            }
        });

        //reject someone else's request for your resources
        rejectRequestBtn.addClickListener(clickEvent -> {
            requestRepo.deleteRequest(Integer.valueOf(resourceidTxt.getValue()));
            label.setValue("deleted request, click View Resource Statis to view update");
        });

        //cancel your request
        cancelYourRequestBtn.addClickListener(clickEvent -> {
            action = requestRepo.getRequest(Integer.valueOf(resourceidTxt.getValue()),
                    VaadinSession.getCurrent().getAttribute("user").toString(),Integer.valueOf(incidentidTxt.getValue()));
           requestRepo.cancelRequest(action);
           label.setValue("Your request was cancelled");
        });




        // add components to UI
        resourceRequestsGrid.setWidth("1000");
        myRequestedResourcesGrid.setWidth("1000");
        resourcesInUseGrid.setWidth("1000");
        txtlLayout.addComponents(resourceidTxt,ownerTxt,incidentidTxt,startdateTxt);
        btnLayout.addComponents(viewResourceStatusBtn,deployResourceBtn,returnResourceBtn,
                rejectRequestBtn,cancelYourRequestBtn);
        addComponents(resourcesInUseGrid,myRequestedResourcesGrid,resourceRequestsGrid,txtlLayout,btnLayout);
        addComponent(label);


    }

    public ResourceStatusView(){
        resourcesInUseGrid = new Grid<>(ResourceStatus.class);
        resourcesInUseGrid.setCaption("Resources in Use");
        myRequestedResourcesGrid = new Grid<>(ResourceStatus.class);
        myRequestedResourcesGrid.setCaption("Resources Requested by me");
        resourceRequestsGrid = new Grid<>(ResourceStatus.class);
        resourceRequestsGrid.setCaption("Resource Requests received by me");



        viewResourceStatusBtn = new Button("View Resource Status");
        deployResourceBtn = new Button("Deploy");
        rejectRequestBtn = new Button("Reject");
        returnResourceBtn = new Button("Return");
        cancelYourRequestBtn = new Button("Cancel");

        resourceidTxt = new TextField("Enter target resource id");
        ownerTxt = new TextField("Copy owner");
        incidentidTxt = new TextField("Copy incidentid");
        startdateTxt = new TextField("Start date: XXXX-XX-XX");



    }

}
