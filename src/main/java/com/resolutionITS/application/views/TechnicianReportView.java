package com.resolutionITS.application.views;

import com.resolutionITS.application.entities.Skill;
import com.resolutionITS.application.entities.TechnicianReport;
import com.resolutionITS.application.repos.Skillrepo;
import com.resolutionITS.application.repos.TechnicianReportRepo;
import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.List;

@SpringView(name = TechnicianReportView.VIEW)
public class TechnicianReportView extends VerticalLayout implements View {
    public static final String VIEW = "TechnicianReport";


    Grid<TechnicianReport> resourceGrid = new Grid<>(TechnicianReport.class);
    Grid<TechnicianReport> resourceGrid2 = new Grid<>(TechnicianReport.class);
    Button runButton = new Button("Run Report");
    @Autowired
    TechnicianReportRepo technicianReportRepo;

    @Autowired
    Skillrepo skillrepo;
    List<Skill> skillList;

    List<TechnicianReport> resourceList;
    List<TechnicianReport> resourceList2;


    @PostConstruct
    void init() {

        runButton.addClickListener(clickEvent -> {
            resourceList = technicianReportRepo.runReport();
            resourceGrid.setItems(resourceList);
            //resourceGrid.addColumn(test);

            resourceList2 = technicianReportRepo.runReport2();
            resourceGrid2.setItems(resourceList2);
        });


        addComponents(resourceGrid, resourceGrid2, runButton);
    }

}
