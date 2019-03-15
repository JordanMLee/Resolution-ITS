package com.resolutionITS.application.views;

import com.resolutionITS.application.entities.Issue;
import com.resolutionITS.application.entities.Request;
import com.resolutionITS.application.entities.Skill;
import com.resolutionITS.application.entities.technician;
import com.resolutionITS.application.repos.IssueRepo;
import com.resolutionITS.application.repos.RequestRepo;
import com.resolutionITS.application.repos.Skillrepo;
import com.resolutionITS.application.repos.TechnicianRepo;
import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import com.vaadin.ui.renderers.ButtonRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import javax.annotation.PostConstruct;
import java.util.List;

@SpringView(name = SearchForTechnicians.VIEW)
public class SearchForTechnicians extends VerticalLayout implements View {
    public static final String VIEW = "SearchForTechnicians";
    technician technician = new technician();
    Request request = new Request();
    ButtonRenderer buttonRenderer;
    String btnlabel = "Request";
    //keyword search
    SqlParameterSource keyword;
    SqlParameterSource esfSearchWord;
    HorizontalLayout btnLayout;
    @Autowired
    private TechnicianRepo technicianRepo;
    @Autowired
    private Skillrepo esfRepo;
    @Autowired
    private RequestRepo requestRepo;
    @Autowired
    private IssueRepo issueRepo;
    // UI components
    private TextField keywordTxt;
    private NativeSelect<Skill> ESFselect;
    private TextField distanceTxt;
    private Label confLabel;
    private NativeSelect<Issue> issueSelect;
    private Button clearBtn;
    private DateField returnDateField = new DateField("Return Date");
    private TextField technicianIDtxt = new TextField("Enter requested technicianid");
    // search results
    private Grid<technician> technicianResults = new Grid<>(technician.class);
    private Button searchBtn = new Button("Search", e -> searchtechnician());
    private Grid<technician> testGrid = new Grid<>();


    public SearchForTechnicians() {
        btnLayout = new HorizontalLayout();
        buttonRenderer = new ButtonRenderer();
    }

    @PostConstruct
    void init() {
        //keyword.hasValue("truck");
        // GUI
        //VaadinSession.getCurrent().getAttribute("user")
        addComponent(new Label("Search technician"));
        confLabel = new Label();
        addComponent(confLabel);

        keywordTxt = new TextField("Keyword");

        ESFselect = new NativeSelect<>("Skill");
        ESFselect.setItems(esfRepo.findAll());

        distanceTxt = new TextField("Location within");

        issueSelect = new NativeSelect<>("Issue");
        issueSelect.setItems(issueRepo.findAll());

        clearBtn = new Button("Clear");
        clearBtn.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                technicianResults.setVisible(false);
            }
        });

        technicianResults.setVisible(false);
        technicianResults.setWidth("1000");
        technicianResults.addColumn(technician -> {
                    return btnlabel;
                },
                new ButtonRenderer<>(clickEvent -> {
                    confLabel.setValue("");
                    request.setUsername(getSession().getAttribute("user").toString());
                    request.setissueid(Integer.valueOf(issueSelect.getValue().getissueid()));
                    request.setReturndate(java.sql.Date.valueOf(returnDateField.getValue()));
                    request.settechnicianid(Integer.valueOf(technicianIDtxt.getValue()));


                    requestRepo.insertRequest(request);
                    //Notification.show("button clicked"+ technicianResults.getId().toString());
                    Notification.show("technician Requested Successfully");


                })).setCaption("Action");


        // add components to UI
        setWidthUndefined();
        keywordTxt.setWidth("100%");
        ESFselect.setWidth("100%");
        distanceTxt.setWidth("100%");
        issueSelect.setWidth("100%");
        btnLayout.setWidth("100%");
        returnDateField.setWidth("100%");
        technicianIDtxt.setWidth("100%");

        addComponents(keywordTxt, ESFselect, distanceTxt, issueSelect);

        btnLayout.addComponentsAndExpand(clearBtn, searchBtn);
        addComponent(btnLayout);


        addComponent(technicianResults); //add grid
        addComponent(returnDateField);
        addComponent(technicianIDtxt);
    }

    private void updateGrid() {
        if (isValidInput()) {
            if (!keywordTxt.isEmpty() || !ESFselect.isEmpty() || !(issueSelect.isEmpty() || distanceTxt.isEmpty())) {
                keyword = new MapSqlParameterSource().addValue("keyword", keywordTxt.getValue());
                List<technician> reskeywordSerResults = technicianRepo.technicianSearch(
                        (!keywordTxt.getValue().isEmpty()) ? keywordTxt.getValue() : null,
                        (ESFselect.getValue() != null) ? ESFselect.getValue().getskillid() : null,
                        (issueSelect.getValue() != null && distanceTxt.getValue() != null && !issueSelect.isEmpty()) ? issueSelect.getValue() : null,
                        (issueSelect.getValue() != null && distanceTxt.getValue() != null && !issueSelect.isEmpty()) ? Integer.valueOf(distanceTxt.getValue()) : null);

                technicianResults.setItems(reskeywordSerResults);
            }
            // Select ALL technicians
            else {

                List<technician> technicians = technicianRepo.findAllv2();
                technicianResults.setItems(technicians);
            }
        }
    }

    private boolean isValidInput() {
        boolean isValid = true;

        if (!issueSelect.isEmpty() && distanceTxt.isEmpty()) {
            confLabel.setValue("'Located within' distance must be entered to search for technicians located within the issue you selected. Showing all technicians.");
            isValid = false;
        }
        if (!distanceTxt.isEmpty() && distanceTxt.getValue() != null) {
            try {
                Double.valueOf(distanceTxt.getValue());
            } catch (Exception e) {
                confLabel.setValue(confLabel.getValue() + " 'Located within' distance value must be numeric. Showing all technicians.");
                isValid = false;
            }
        }

        return isValid;
    }

    private void searchtechnician() {
        confLabel.setValue("");
        updateGrid();
        technicianResults.setVisible(true);

    }

}
