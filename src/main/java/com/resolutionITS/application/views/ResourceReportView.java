package com.resolutionITS.application.views;

import com.resolutionITS.application.entities.ESF;
import com.resolutionITS.application.entities.ResourceReport;
import com.resolutionITS.application.repos.ESFrepo;
import com.resolutionITS.application.repos.ResourceReportRepo;
import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.List;

@SpringView(name = ResourceReportView.VIEW)
public class ResourceReportView  extends VerticalLayout implements View {
    public static final String VIEW = "ResourceReport";


    Grid<ResourceReport> resourceGrid = new Grid<>(ResourceReport.class);
    Grid<ResourceReport> resourceGrid2 = new Grid<>(ResourceReport.class);
    Button runButton = new Button("Run Report");
    @Autowired
    ResourceReportRepo resourceReportRepo;

    @Autowired
    ESFrepo esFrepo;
    List<ESF> esfList;

    List<ResourceReport> resourceList;
    List<ResourceReport> resourceList2;



    @PostConstruct
    void init() {

        runButton.addClickListener(clickEvent -> {
            resourceList = resourceReportRepo.runReport();
            resourceGrid.setItems(resourceList);
            //resourceGrid.addColumn(test);

            resourceList2 = resourceReportRepo.runReport2();
            resourceGrid2.setItems(resourceList2);
        });


       addComponents(resourceGrid ,resourceGrid2, runButton);
    }

}
