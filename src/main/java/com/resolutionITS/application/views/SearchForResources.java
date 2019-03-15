package com.resolutionITS.application.views;

import com.resolutionITS.application.entities.*;
import com.resolutionITS.application.repos.ESFrepo;
import com.resolutionITS.application.repos.IssueRepo;
import com.resolutionITS.application.repos.RequestRepo;
import com.resolutionITS.application.repos.ResourceRepo;
import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import com.vaadin.ui.renderers.ButtonRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;


import javax.annotation.PostConstruct;
import java.util.List;

@SpringView(name = SearchForResources.VIEW)
public class SearchForResources extends VerticalLayout implements View {
    public static final String VIEW = "SearchForResources";

    @Autowired
    private ResourceRepo resourceRepo;

    @Autowired
    private ESFrepo esfRepo;

    @Autowired
    private RequestRepo requestRepo;

    @Autowired
    private IssueRepo issueRepo;

    Resource resource = new Resource();

    Request request = new Request();

    ButtonRenderer buttonRenderer ;

    // UI components
    private TextField keywordTxt;
    private NativeSelect<ESF> ESFselect;
    private TextField distanceTxt;
    private Label confLabel;

    private NativeSelect<Incident> incidentSelect;

    private Button clearBtn;
    private Button searchBtn = new Button("Search", e -> searchResource());

    String btnlabel = "Request";
    private DateField returnDateField = new DateField("Return Date");
    private TextField resourceIDtxt = new TextField("Enter requested resourceid");


    // search results
    private Grid<Resource> resourceResults = new Grid<>(Resource.class);

    private Grid<Resource> testGrid = new Grid<>();

    //keyword search
    SqlParameterSource keyword;
    SqlParameterSource esfSearchWord;
    HorizontalLayout btnLayout;


    @PostConstruct
    void init() {
        //keyword.hasValue("truck");
        // GUI
        //VaadinSession.getCurrent().getAttribute("user")
        addComponent(new Label("Search Resource"));
        confLabel = new Label();
        addComponent(confLabel);

        keywordTxt = new TextField("Keyword");

        ESFselect = new NativeSelect<>("ESF");
        ESFselect.setItems(esfRepo.findAll());

        distanceTxt = new TextField("Location within");

        incidentSelect = new NativeSelect<>("Incident");
        incidentSelect.setItems(issueRepo.findAll());

        clearBtn = new Button("Clear");
        clearBtn.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                resourceResults.setVisible(false);
            }
        });
        
        resourceResults.setVisible(false);
        resourceResults.setWidth("1000");
        resourceResults.addColumn(resource -> {
                    return btnlabel;
                },
                new ButtonRenderer<>(clickEvent -> {
                    confLabel.setValue("");
                    request.setUsername(getSession().getAttribute("user").toString());
                    request.setIncidentid(Integer.valueOf(incidentSelect.getValue().getIncidentid()));
                    request.setReturndate(java.sql.Date.valueOf(returnDateField.getValue()));
                    request.setResourceid(Integer.valueOf(resourceIDtxt.getValue()));




                    requestRepo.insertRequest(request);
                    //Notification.show("button clicked"+ resourceResults.getId().toString());
                    Notification.show("Resource Requested Successfully");


                })).setCaption("Action");



        // add components to UI
        setWidthUndefined();
        keywordTxt.setWidth("100%");
        ESFselect.setWidth("100%");
        distanceTxt.setWidth("100%");
        incidentSelect.setWidth("100%");
        btnLayout.setWidth("100%");
        returnDateField.setWidth("100%");
        resourceIDtxt.setWidth("100%");

        addComponents(keywordTxt,ESFselect,distanceTxt,incidentSelect);

        btnLayout.addComponentsAndExpand(clearBtn,searchBtn);
        addComponent(btnLayout);


        addComponent(resourceResults); //add grid
        addComponent(returnDateField);
        addComponent(resourceIDtxt);
    }


    private void updateGrid() {
        if (isValidInput()) {
            if (!keywordTxt.isEmpty() || !ESFselect.isEmpty() || !(incidentSelect.isEmpty() || distanceTxt.isEmpty())) {
                keyword = new MapSqlParameterSource().addValue("keyword", keywordTxt.getValue());
                List<Resource> reskeywordSerResults = resourceRepo.resourceSearch(
                        (!keywordTxt.getValue().isEmpty()) ? keywordTxt.getValue() : null,
                        (ESFselect.getValue() != null) ? ESFselect.getValue().getEsfid() : null,
                        (incidentSelect.getValue() != null && distanceTxt.getValue() != null && !incidentSelect.isEmpty()) ? incidentSelect.getValue() : null,
                        (incidentSelect.getValue() != null && distanceTxt.getValue() != null && !incidentSelect.isEmpty()) ? Integer.valueOf(distanceTxt.getValue()) : null);

                resourceResults.setItems(reskeywordSerResults);
            }
            // Select ALL resources
            else {

                List<Resource> resources = resourceRepo.findAllv2();
                resourceResults.setItems(resources);
            }
        }
    }

    private boolean isValidInput(){
        boolean isValid = true;

        if(!incidentSelect.isEmpty() && distanceTxt.isEmpty()){
            confLabel.setValue("'Located within' distance must be entered to search for resources located within the incident you selected. Showing all resources.");
            isValid = false;
        }
        if(!distanceTxt.isEmpty() && distanceTxt.getValue() != null){
            try {
                Double.valueOf(distanceTxt.getValue());
            }
            catch (Exception e){
                confLabel.setValue(confLabel.getValue()+" 'Located within' distance value must be numeric. Showing all resources.");
                isValid = false;
            }
        }

        return isValid;
    }

    private void searchResource() {
        confLabel.setValue("");
        updateGrid();
        resourceResults.setVisible(true);

    }

    public SearchForResources() {
        btnLayout = new HorizontalLayout();
        buttonRenderer = new ButtonRenderer();
    }

}
