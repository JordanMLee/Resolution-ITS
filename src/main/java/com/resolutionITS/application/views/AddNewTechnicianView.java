package com.resolutionITS.application.views;

import com.resolutionITS.application.entities.Skill;
import com.resolutionITS.application.entities.Time;
import com.resolutionITS.application.entities.technician;
import com.resolutionITS.application.repos.CapabilitiesRepo;
import com.resolutionITS.application.repos.Skillrepo;
import com.resolutionITS.application.repos.TechnicianRepo;
import com.resolutionITS.application.repos.TimeRepo;
import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@SpringView(name = AddNewTechnicianView.VIEW)
public class AddNewTechnicianView extends VerticalLayout implements View {
    public static final String VIEW = "AddNewtechnician";
    // technician to be added
    technician technician = new technician();
    // Input fields
    HorizontalLayout horizontalLayout;
    @Autowired
    private TechnicianRepo technicianRepo;
    @Autowired
    private Skillrepo skillRepo;
    @Autowired
    private TimeRepo timeRepo;
    @Autowired
    private CapabilitiesRepo capabilitiesRepo;
    private TextField owner_txt;
    private TextField technicianName_txt;
    private NativeSelect<Skill> primaryskillSelect;
    private TextArea capabilitiesTextArea;
    private ListSelect<Skill> additionalskillSelectList;
    private TextField model_txt;
    private TextField lat_txt;
    private TextField long_txt;
    private TextField maxdist_txt;
    private TextField costTxt;

    private Button saveBtn;
    private Button mainMenuBtn;
    private Button clearFieldsBtn;
    private Label confLabel;


    public AddNewTechnicianView() {

        mainMenuBtn = new Button("Main Menu");
        clearFieldsBtn = new Button("Clear fields");
        saveBtn = new Button("Save");
        horizontalLayout = new HorizontalLayout();
    }

    @PostConstruct
    void init() {
        // GUI
        addComponent(new Label("Add A New technician"));

        // success label
        confLabel = new Label();

        // owner
        // needs to set to current logged in User
        owner_txt = new TextField("Owner");
        owner_txt.setValue("You");
        owner_txt.setReadOnly(true);
        owner_txt.setEnabled(false);

        // technician name
        technicianName_txt = new TextField("technician Name");
        technicianName_txt.setRequiredIndicatorVisible(true);

        // Primary Skill
        primaryskillSelect = new NativeSelect("Primary Skill");
        primaryskillSelect.setEmptySelectionAllowed(false);
        primaryskillSelect.setRequiredIndicatorVisible(true);
        primaryskillSelect.setItems(skillRepo.findAll());


        // Additional Skill (optional)
        additionalskillSelectList = new ListSelect<>("Additional skills [Optional]");
        additionalskillSelectList.setItems(skillRepo.findAll());
        additionalskillSelectList.setRows(4);

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

        // Time
        costTxt = new TextField("Time");

        // Time per
        NativeSelect<String> perCost = new NativeSelect<>("per Unit");
        perCost.setItems("", "Hour", "Day", "Week");

        saveBtn.addClickListener((Button.ClickListener) clickEvent -> {
            confLabel.setValue("");
            if (isValidInput()) {
                owner_txt.setValue(getSession().getAttribute("user").toString());
                Notification.show(getSession().getAttribute("user").toString());
                technician.setUsername(getSession().getAttribute("user").toString());
                technician.settechnicianname(technicianName_txt.getValue());
                technician.setskillid((short) primaryskillSelect.getValue().getskillid());
                technician.setModel(model_txt.getValue());
                technician.setLatitude(Float.parseFloat(lat_txt.getValue()));
                technician.setLongitude(Float.parseFloat(long_txt.getValue()));
                technician.setMaxdist(maxdist_txt.isEmpty() ? 0 : Short.parseShort(maxdist_txt.getValue()));
                Time time = new Time(perCost.getValue(), Double.valueOf(costTxt.getValue()));


                int technicianid = 0;
                int costid = 0;

                // DB Inserts
                try {
                    costid = timeRepo.insertTime(time);
                    confLabel.setValue(confLabel.getValue() + "================ COST ID ============= " + costid);
                } catch (SQLException e) {
                    confLabel.setValue(confLabel.getValue() + e.getMessage());
                    e.printStackTrace();
                }
                time.setCostid(costid);
                technician.setTime(time);
                try {
                    technicianid = technicianRepo.inserttechnician(technician);
                } catch (SQLException e) {
                    confLabel.setValue(confLabel.getValue() + e.getMessage());
                    e.printStackTrace();
                }

                if (technicianid > 0) {
                    confLabel.setValue(confLabel.getValue() + technician.toString() + " has been entered into springbootdb1");
                    technician.settechnicianid(technicianid);
                } else {
                    confLabel.setValue(confLabel.getValue() + "Error inserting technician to DB.");
                }

                // If has additional Skill;
                if (technicianid > 0 && !additionalskillSelectList.getSelectedItems().isEmpty()) {
                    // get technician ID
                    for (Skill additionalSkill : additionalskillSelectList.getSelectedItems()) {
                        technicianRepo.insertHasskill(technician.gettechnicianid(), (short) additionalSkill.getskillid());
                    }
                }

                // If capabilities have been entered
                List<String> capabilities = parseCapabilities(capabilitiesTextArea);
                if (!capabilities.isEmpty()) {
                    technician.setCapabilites(capabilities);
                    capabilitiesRepo.insertCapabilities(technician);
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
        technicianName_txt.setWidth("100%");
        primaryskillSelect.setWidth("100%");
        model_txt.setWidth("100%");
        costTxt.setWidth("100%");
        perCost.setWidth("100%");
        lat_txt.setWidth("100%");
        long_txt.setWidth("100%");
        maxdist_txt.setWidth("100%");
        additionalskillSelectList.setWidth("100%");
        capabilitiesTextArea.setWidth("100%");
        horizontalLayout.addComponentsAndExpand(saveBtn, mainMenuBtn, clearFieldsBtn);
        horizontalLayout.setWidth("100%");


        addComponent(owner_txt);
        addComponent(technicianName_txt);
        addComponent(primaryskillSelect);
        addComponent(model_txt);
        addComponent(costTxt);
        addComponent(perCost);
        addComponent(lat_txt);
        addComponent(long_txt);
        addComponent(maxdist_txt);
        addComponent(additionalskillSelectList);
        addComponent(capabilitiesTextArea);
        addComponent(confLabel);
        addComponent(horizontalLayout);

    }

    private boolean isValidInput() {
        boolean isValid = true;

        if (technicianName_txt.isEmpty() || primaryskillSelect.isEmpty() || costTxt.isEmpty() ||
                lat_txt.isEmpty() || long_txt.isEmpty()) {
            isValid = false;
            confLabel.setValue(confLabel.getValue() + " Required field(s) is empty.");
        }
        if (!costTxt.isEmpty()) {
            try {
                Double.valueOf(costTxt.getValue());
            } catch (Exception e) {
                confLabel.setValue(confLabel.getValue() + " Time value must be numeric. Exception: " + e.getMessage());
                isValid = false;
            }
        }
        if (!lat_txt.isEmpty()) {
            try {
                Double.valueOf(lat_txt.getValue());
            } catch (Exception e) {
                confLabel.setValue(confLabel.getValue() + " Latitude value must be numeric. Exception: " + e.getMessage());
                isValid = false;
            }
        }
        if (!long_txt.isEmpty()) {
            try {
                Double.valueOf(long_txt.getValue());
            } catch (Exception e) {
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

}
