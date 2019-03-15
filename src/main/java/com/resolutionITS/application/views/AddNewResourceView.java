package com.resolutionITS.application.views;

import com.resolutionITS.application.entities.Cost;
import com.resolutionITS.application.entities.ESF;
import com.resolutionITS.application.entities.Resource;
import com.resolutionITS.application.repos.CapabilitiesRepo;
import com.resolutionITS.application.repos.CostRepo;
import com.resolutionITS.application.repos.ESFrepo;
import com.resolutionITS.application.repos.ResourceRepo;
import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@SpringView(name=AddNewResourceView.VIEW)
public class AddNewResourceView extends VerticalLayout implements View {
    public static final String VIEW = "AddNewResource";

    @Autowired
    private ResourceRepo resourceRepo;
    @Autowired
    private ESFrepo esfRepo;
    @Autowired
    private CostRepo costRepo;
    @Autowired
    private CapabilitiesRepo capabilitiesRepo;

    // Resource to be added
    Resource resource = new Resource();

    // Input fields
    HorizontalLayout horizontalLayout;
    private TextField owner_txt;
    private TextField resourceName_txt;
    private NativeSelect<ESF> primaryEsfSelect;
    private TextArea capabilitiesTextArea;
    private ListSelect<ESF> additionalESFSelectList;
    private TextField model_txt;
    private TextField lat_txt;
    private TextField long_txt;
    private TextField maxdist_txt;
    private TextField costTxt;

    private Button saveBtn;
    private Button mainMenuBtn;
    private Button clearFieldsBtn;
    private Label confLabel;


    @PostConstruct
    void init() {
        // GUI
        addComponent(new Label("Add A New Resource"));

        // success label
        confLabel = new Label();

        // owner
        // needs to set to current logged in User
        owner_txt = new TextField("Owner");
        owner_txt.setValue("You");
        owner_txt.setReadOnly(true);
        owner_txt.setEnabled(false);

        // Resource name
        resourceName_txt= new TextField("Resource Name");
        resourceName_txt.setRequiredIndicatorVisible(true);

        // Primary ESF
        primaryEsfSelect = new NativeSelect("Primary ESF");
        primaryEsfSelect.setEmptySelectionAllowed(false);
        primaryEsfSelect.setRequiredIndicatorVisible(true);
        primaryEsfSelect.setItems(esfRepo.findAll());


        // Additional ESF (optional)
        additionalESFSelectList = new ListSelect<>("Additional ESFs [Optional]");
        additionalESFSelectList.setItems(esfRepo.findAll());
        additionalESFSelectList.setRows(4);

        // Model (optional)
        model_txt = new TextField("Model [Optional]");

        // Home location
        lat_txt = new TextField("lat");
        lat_txt.setRequiredIndicatorVisible(true);
        long_txt = new TextField("long");
        long_txt.setRequiredIndicatorVisible(true);

        maxdist_txt = new TextField("maxdist");

        // Capabilities (optional)
        capabilitiesTextArea = new TextArea("Capabilities [Optional]");

        // Cost
        costTxt = new TextField("Cost");

        // Cost per
        NativeSelect<String> perCost = new NativeSelect<>("per Unit");
        perCost.setItems("", "Hour", "Day", "Week");

        saveBtn.addClickListener((Button.ClickListener) clickEvent -> {
            confLabel.setValue("");
            if (isValidInput()) {
                owner_txt.setValue(getSession().getAttribute("user").toString());
                Notification.show(getSession().getAttribute("user").toString());
                resource.setUsername(getSession().getAttribute("user").toString());
                resource.setResourcename(resourceName_txt.getValue());
                resource.setEsfid((short)primaryEsfSelect.getValue().getEsfid());
                resource.setModel(model_txt.getValue());
                resource.setLatitude(Float.parseFloat(lat_txt.getValue()));
                resource.setLongitude(Float.parseFloat(long_txt.getValue()));
                resource.setMaxdist(maxdist_txt.isEmpty() ? 0 : Short.parseShort(maxdist_txt.getValue()));
                Cost cost = new Cost(perCost.getValue(), Double.valueOf(costTxt.getValue()));


                int resourceid = 0;
                int costid = 0;

                // DB Inserts
                try {
                    costid = costRepo.insertCost(cost);
                    confLabel.setValue(confLabel.getValue() + "================ COST ID ============= " + costid);
                } catch (SQLException e) {
                    confLabel.setValue(confLabel.getValue() + e.getMessage());
                    e.printStackTrace();
                }
                cost.setCostid(costid);
                resource.setCost(cost);
                try {
                    resourceid = resourceRepo.insertResource(resource);
                } catch (SQLException e) {
                    confLabel.setValue(confLabel.getValue() + e.getMessage());
                    e.printStackTrace();
                }

                if (resourceid > 0) {
                    confLabel.setValue(confLabel.getValue() + resource.toString() + " has been entered into springbootdb1");
                    resource.setResourceid(resourceid);
                } else {
                    confLabel.setValue(confLabel.getValue() + "Error inserting resource to DB.");
                }

                // If has additional ESF;
                if (resourceid > 0 && !additionalESFSelectList.getSelectedItems().isEmpty()) {
                    // get resource ID
                    for (ESF additionalESF : additionalESFSelectList.getSelectedItems()) {
                        resourceRepo.insertHasESF(resource.getResourceid(), (short) additionalESF.getEsfid());
                    }
                }

                // If capabilities have been entered
                List<String> capabilities = parseCapabilities(capabilitiesTextArea);
                if (!capabilities.isEmpty()){
                    resource.setCapabilites(capabilities);
                    capabilitiesRepo.insertCapabilities(resource);
                }
            }
        });

        // return to Main Menu
        mainMenuBtn.addClickListener((Button.ClickListener) clickEvent -> getUI().getNavigator().navigateTo(""));

        clearFieldsBtn.addClickListener((Button.ClickListener) clickEvent -> {
            removeAllComponents();
            init();
        });

        setWidthUndefined();
        owner_txt.setWidth("100%");
        resourceName_txt.setWidth("100%");
        primaryEsfSelect.setWidth("100%");
        model_txt.setWidth("100%");
        costTxt.setWidth("100%");
        perCost.setWidth("100%");
        lat_txt.setWidth("100%");
        long_txt.setWidth("100%");
        maxdist_txt.setWidth("100%");
        additionalESFSelectList.setWidth("100%");
        capabilitiesTextArea.setWidth("100%");
        horizontalLayout.addComponentsAndExpand(saveBtn,mainMenuBtn,clearFieldsBtn);
        horizontalLayout.setWidth("100%");


        addComponent(owner_txt);
        addComponent(resourceName_txt);
        addComponent(primaryEsfSelect);
        addComponent(model_txt);
        addComponent(costTxt);
        addComponent(perCost);
        addComponent(lat_txt);
        addComponent(long_txt);
        addComponent(maxdist_txt);
        addComponent(additionalESFSelectList);
        addComponent(capabilitiesTextArea);
        addComponent(confLabel);
        addComponent(horizontalLayout);

    }

    private boolean isValidInput() {
        boolean isValid = true;

        if(resourceName_txt.isEmpty() || primaryEsfSelect.isEmpty() || costTxt.isEmpty() ||
                lat_txt.isEmpty() || long_txt.isEmpty()) {
            isValid = false;
            confLabel.setValue(confLabel.getValue()+" Required field(s) is empty.");
        }
        if (!costTxt.isEmpty()) {
            try {
                Double.valueOf(costTxt.getValue());
            }
            catch (Exception e){
                confLabel.setValue(confLabel.getValue()+" Cost value must be numeric. Exception: " + e.getMessage());
                isValid = false;
            }
        }
        if (!lat_txt.isEmpty()) {
            try {
                Double.valueOf(lat_txt.getValue());
            }
            catch (Exception e){
                confLabel.setValue(confLabel.getValue()+ " Latitude value must be numeric. Exception: " + e.getMessage());
                isValid = false;
            }
        }
        if (!long_txt.isEmpty()) {
            try {
                Double.valueOf(long_txt.getValue());
            }
            catch (Exception e){
                confLabel.setValue(confLabel.getValue() + " Longitude value must be numeric. Exception: " + e.getMessage());
                isValid = false;
            }
        }

        return isValid;
    }

    private List<String> parseCapabilities(TextArea capabilitesTextArea) {
        List<String> returnParsed = new ArrayList<String>();

        String input = capabilitiesTextArea.getValue();
        if (!input.isEmpty()) {
            for (String capability : input.split("\\r?\\n")) {
                returnParsed.add(capability.trim());
            }
        }
        return returnParsed;
    }

    public AddNewResourceView() {

        mainMenuBtn = new Button("Main Menu");
        clearFieldsBtn = new Button("Clear fields");
        saveBtn = new Button("Save");
        horizontalLayout = new HorizontalLayout();
    }

}
