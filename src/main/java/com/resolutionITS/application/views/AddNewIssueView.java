package com.resolutionITS.application.views;

import com.resolutionITS.application.entities.Incident;
import com.resolutionITS.application.entities.Incident_declaration;
import com.resolutionITS.application.repos.IssueRepo;
import com.resolutionITS.application.repos.Incident_declarationRepo;
import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;


import javax.annotation.PostConstruct;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Random;

@SpringView(name= AddNewIssueView.VIEW)
public class AddNewIssueView extends VerticalLayout implements View {
    public static final String VIEW = "AddNewIssue";

    @Autowired
    private IssueRepo issueRepo;

    @Autowired
    private Incident_declarationRepo incident_declarationRepo;
    private Incident incident = new Incident();
    private Incident_declaration incident_declaration = new Incident_declaration();

    Label header = new Label("New Issue Info");

    Random randomID = new Random();
    private int incidentID; //
    private DateField dateField;
    private TextField latitudeTxt;
    private TextField longitudeTxt;
    private TextArea descriptionTxt;

    private Label confLabel;

    private int uniqid;
    private String decl;
    private HorizontalLayout horizontalLayout = new HorizontalLayout();

    private Dictionary declTypes = new Dictionary() {
        public int size() {
            return 0;
        }

        public boolean isEmpty() {
            return false;
        }

        public Enumeration keys() {
            return null;
        }

        public Enumeration elements() {
            return null;
        }

        public Object get(Object key) {
            return null;
        }

        public Object put(Object key, Object value) {
            return null;
        }

        public Object remove(Object key) {
            return null;
        }
    };


    private NativeSelect<String> declaration;
    @PostConstruct
    void init() {
        confLabel = new Label();
        // declaration
        declaration = new NativeSelect<>("Declaration");
        declaration.setEmptySelectionAllowed(false);
        declaration.setRequiredIndicatorVisible(true);

        declaration.setItems("MD","EM","FM","FS");
        declTypes.put("MD", "Major Disaster");
        declTypes.put("EM", "Emergency");
        declTypes.put("FM", "Fire Management Assistance");
        declTypes.put("FS", "Major Suppression Authorization ");


        // Date
        dateField = new DateField("Date");
        dateField.setDateFormat("MM-dd-yyyy");
        dateField.setRequiredIndicatorVisible(true);


        // Location
        latitudeTxt = new TextField("Latitude");
        longitudeTxt = new TextField("Longitude");
        latitudeTxt.setRequiredIndicatorVisible(true);
        longitudeTxt.setRequiredIndicatorVisible(true);


        // Description
        descriptionTxt = new TextArea("Description");
        setSizeUndefined();
        confLabel.setWidth("100%");
        declaration.setWidth("100%");
        dateField.setWidth("100%");
        latitudeTxt.setWidth("100%");
        longitudeTxt.setWidth("100%");
        descriptionTxt.setWidth("100%");
        horizontalLayout.setWidth("100%");


        addComponent(header);
        addComponent(declaration);
        addComponent(dateField);
        addComponents(latitudeTxt,longitudeTxt);
        addComponent(descriptionTxt);
        addComponent(confLabel);
        setComponentAlignment(header,Alignment.TOP_CENTER);
        setComponentAlignment(declaration,Alignment.TOP_CENTER);
        setComponentAlignment(dateField,Alignment.TOP_CENTER);
        setComponentAlignment(latitudeTxt,Alignment.TOP_CENTER);
        setComponentAlignment(longitudeTxt,Alignment.TOP_CENTER);
        setComponentAlignment(descriptionTxt,Alignment.TOP_CENTER);


        horizontalLayout.addComponentsAndExpand(new Button("Main Menu",click->{
            getUI().getNavigator().navigateTo("");
        }), new Button("Save", click -> {

            if ( ( incident.getLatitude() < 90 && incident.getLatitude() > -90)
                    && (incident.getLongitude() <180 && incident.getLongitude() > -180) ) {

                incident.setUsername(getSession().getAttribute("user").toString());
                incident.setDescription(descriptionTxt.getValue());
                incident.setIncidentdate(java.sql.Date.valueOf(dateField.getValue()));
                incidentID = issueRepo.selectIncidentID() + 1;
                incident.setIncidentid(incidentID);

                decl = declaration.getValue(); //String
                //incident_declarationRepo takes in a String, typecast to Declarations,
                // returns a String and then converts to integer and back to String before
                //being stored.


//                uniqid = decl + "-" + String.valueOf( Integer.parseInt(incident_declarationRepo.selectuniqID(decl).replaceAll("[^0-9]","")) + 1 );
                uniqid = Integer.parseInt(incident_declarationRepo.selectuniqID(decl).replaceAll("[^0-9]",""));

                incident.setUniqid(uniqid);
                incident.setLatitude(Float.valueOf(latitudeTxt.getValue()));
                incident.setLongitude(Float.valueOf(longitudeTxt.getValue()));

                issueRepo.insert(incident);

                incident_declaration.setDeclaration(decl);
                incident_declaration.setUniqid(uniqid);
                incident_declaration.setIncidentid(incidentID);
                incident_declarationRepo.insert(incident_declaration);
                confLabel.setValue(incident.toString() + " added to springbootdb1");
            }

            else { Notification.show("Invalid location, please use xx.xxxx or xxx.xxxx format");}

        }));

        addComponent(horizontalLayout);
        setComponentAlignment(horizontalLayout,Alignment.TOP_CENTER);

    }



}
