package com.resolutionITS.application.views;

import com.resolutionITS.application.entities.Issue;
import com.resolutionITS.application.repos.IssueRepo;
import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@SpringView(name = AddNewIssueView.VIEW)
public class AddNewIssueView extends VerticalLayout implements View {
    public static final String VIEW = "AddNewIssue";
    Label header = new Label("New Issue Info");
    Random randomID = new Random();
    @Autowired
    private IssueRepo issueRepo;
    //    @Autowired
//    private Issue_declarationRepo issue_declarationRepo;
    private Issue issue = new Issue();
    //    private Issue_declaration issue_declaration = new Issue_declaration();
    private int issueID; //
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
        declaration = new NativeSelect<>("Issue Type");
        declaration.setEmptySelectionAllowed(false);
        declaration.setRequiredIndicatorVisible(true);

        declaration.setItems("Software", "Workstation", "Printer", "Network");
        declTypes.put("MD", "Major Disaster");
        declTypes.put("EM", "Emergency");
        declTypes.put("FM", "Fire Management Assistance");
        declTypes.put("FS", "Major Suppression Authorization ");


        // Date
        dateField = new DateField("Date");
        dateField.setDateFormat("MM-dd-yyyy");
        dateField.setRequiredIndicatorVisible(true);


        // Location
        latitudeTxt = new TextField("equipment serial number");
        longitudeTxt = new TextField("item code");
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
        addComponents(latitudeTxt, longitudeTxt);
        addComponent(descriptionTxt);
        addComponent(confLabel);
        setComponentAlignment(header, Alignment.TOP_CENTER);
        setComponentAlignment(declaration, Alignment.TOP_CENTER);
        setComponentAlignment(dateField, Alignment.TOP_CENTER);
        setComponentAlignment(latitudeTxt, Alignment.TOP_CENTER);
        setComponentAlignment(longitudeTxt, Alignment.TOP_CENTER);
        setComponentAlignment(descriptionTxt, Alignment.TOP_CENTER);


        horizontalLayout.addComponentsAndExpand(new Button("Main Menu", click -> {
            getUI().getNavigator().navigateTo("");
        }), new Button("Save", click -> {


            issue.setUsername(getSession().getAttribute("user").toString());
            issue.setDescription(descriptionTxt.getValue());
            issue.setissuedate(java.sql.Date.valueOf(dateField.getValue()));
            issueID = issueRepo.selectissueID() + 1;
            issue.setissueid(issueID);

            decl = declaration.getValue(); //String
            //issue_declarationRepo takes in a String, typecast to Declarations,
            // returns a String and then converts to integer and back to String before
            //being stored.


//                uniqid = decl + "-" + String.valueOf( Integer.parseInt(issue_declarationRepo.selectuniqID(decl).replaceAll("[^0-9]","")) + 1 );

// nextInt is normally exclusive of the top value,
// so add 1 to make it inclusive
            int randomNum = ThreadLocalRandom.current().nextInt(1000, 9999);
//            uniqid = Integer.parseInt(issue_declarationRepo.selectuniqID(decl).replaceAll("[^0-9]", ""));

            issue.setUniqid(randomNum);

            issueRepo.insert(issue);

//            issue_declaration.setDeclaration(decl);
//            issue_declaration.setUniqid(uniqid);
//            issue_declaration.setissueid(issueID);
//            issue_declarationRepo.insert(issue_declaration);
            confLabel.setValue(issue.toString() + " added to database");


        }));

        addComponent(horizontalLayout);
        setComponentAlignment(horizontalLayout, Alignment.TOP_CENTER);

    }


}
