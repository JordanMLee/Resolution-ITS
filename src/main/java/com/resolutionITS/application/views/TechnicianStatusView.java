package com.resolutionITS.application.views;

import com.resolutionITS.application.entities.Request;
import com.resolutionITS.application.entities.TechnicianStatus;
import com.resolutionITS.application.repos.RequestRepo;
import com.resolutionITS.application.repos.TechnicianStatusRepo;
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

@SpringView(name = TechnicianStatusView.VIEW)
public class TechnicianStatusView extends VerticalLayout implements View {
    public static final String VIEW = "TechnicianStatus";
    List<TechnicianStatus> techniciansInUseList;
    List<TechnicianStatus> myRequestedtechniciansList;
    List<TechnicianStatus> technicianRequestsList;
    HorizontalLayout btnLayout = new HorizontalLayout();
    HorizontalLayout txtlLayout = new HorizontalLayout();
    SqlParameterSource username;
    SingleSelectionModel<Request> requestSelect;
    @Autowired
    private TechnicianStatusRepo technicianStatusRepo;
    @Autowired
    private RequestRepo requestRepo;
    private Request action;
    private Grid<TechnicianStatus> techniciansInUseGrid;
    private Grid<TechnicianStatus> myRequestedtechniciansGrid;
    private Grid<TechnicianStatus> technicianRequestsGrid;
    private Button viewtechnicianStatusBtn;
    private Button deploytechnicianBtn;
    private Button returntechnicianBtn;
    private Button rejectRequestBtn;
    private Button cancelYourRequestBtn;
    private Label label = new Label("");
    private TextField technicianidTxt;
    private TextField incidentidTxt;
    private TextField ownerTxt;
    private TextField startdateTxt;

    public TechnicianStatusView() {
        techniciansInUseGrid = new Grid<>(TechnicianStatus.class);
        techniciansInUseGrid.setCaption("technicians in Use");
        myRequestedtechniciansGrid = new Grid<>(TechnicianStatus.class);
        myRequestedtechniciansGrid.setCaption("technicians Requested by me");
        technicianRequestsGrid = new Grid<>(TechnicianStatus.class);
        technicianRequestsGrid.setCaption("technician Requests received by me");


        viewtechnicianStatusBtn = new Button("View technician Status");
        deploytechnicianBtn = new Button("Deploy");
        rejectRequestBtn = new Button("Reject");
        returntechnicianBtn = new Button("Return");
        cancelYourRequestBtn = new Button("Cancel");

        technicianidTxt = new TextField("Enter target technician id");
        ownerTxt = new TextField("Copy owner");
        incidentidTxt = new TextField("Copy incidentid");
        startdateTxt = new TextField("Start date: XXXX-XX-XX");


    }

    @PostConstruct
    void init() {
        addComponent(new Label("technician Status"));

        viewtechnicianStatusBtn.addClickListener(clickEvent -> {
            username = new MapSqlParameterSource().addValue("username",
                    VaadinSession.getCurrent().getAttribute("user").toString());

            //bind data
            techniciansInUseList = technicianStatusRepo.gettechniciansInUse(username);
            techniciansInUseGrid.setItems(techniciansInUseList);

            myRequestedtechniciansList = technicianStatusRepo.getMyRequestedtechnicians(username);
            myRequestedtechniciansGrid.setItems(myRequestedtechniciansList);

            technicianRequestsList = technicianStatusRepo.gettechnicianRequests(username);
            technicianRequestsGrid.setItems(technicianRequestsList);
            label.setValue("");
        });

        deploytechnicianBtn.addClickListener(clickEvent -> {
            if (!technicianidTxt.isEmpty() && !ownerTxt.isEmpty() && !incidentidTxt.isEmpty() && !incidentidTxt.isEmpty()) {
                action = requestRepo.getRequest(Integer.valueOf(technicianidTxt.getValue()), ownerTxt.getValue(),
                        Integer.valueOf(incidentidTxt.getValue()));
                requestRepo.deploytechnician(action, Date.valueOf(startdateTxt.getValue()));
                label.setValue(action.toString() + " has been selected and will start work on " + startdateTxt.getValue());
            } else {
                label.setValue("Please enter values in all of the above textboxes");
            }
        });

        // oppposite off deploy (above)
        returntechnicianBtn.addClickListener(clickEvent -> {
            if (!technicianidTxt.isEmpty()) {
                requestRepo.returntechnician(Integer.valueOf(technicianidTxt.getValue()));
                label.setValue("technician returned, click View technician Status to view update");
            }
        });

        //reject someone else's request for your technicians
        rejectRequestBtn.addClickListener(clickEvent -> {
            requestRepo.deleteRequest(Integer.valueOf(technicianidTxt.getValue()));
            label.setValue("deleted request, click View technician Statis to view update");
        });

        //cancel your request
        cancelYourRequestBtn.addClickListener(clickEvent -> {
            action = requestRepo.getRequest(Integer.valueOf(technicianidTxt.getValue()),
                    VaadinSession.getCurrent().getAttribute("user").toString(), Integer.valueOf(incidentidTxt.getValue()));
            requestRepo.cancelRequest(action);
            label.setValue("Your request was cancelled");
        });


        // add components to UI
        technicianRequestsGrid.setWidth("1000");
        myRequestedtechniciansGrid.setWidth("1000");
        techniciansInUseGrid.setWidth("1000");
        txtlLayout.addComponents(technicianidTxt, ownerTxt, incidentidTxt, startdateTxt);
        btnLayout.addComponents(viewtechnicianStatusBtn, deploytechnicianBtn, returntechnicianBtn,
                rejectRequestBtn, cancelYourRequestBtn);
        addComponents(techniciansInUseGrid, myRequestedtechniciansGrid, technicianRequestsGrid, txtlLayout, btnLayout);
        addComponent(label);


    }

}
