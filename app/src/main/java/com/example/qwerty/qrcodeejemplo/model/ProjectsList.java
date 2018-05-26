package com.example.qwerty.qrcodeejemplo.model;

import java.util.Date;

public class ProjectsList {

    private String projectID;
    private String projectName, projectDescription, projectStatus;
    private Date projectStartDate, projectFinishDate;

    public ProjectsList(){}

    public  ProjectsList(String projectID, String projectName, String projectStatus, String projectDescription, Date projectStartDate, Date projectFinishDate){
        this.projectID = projectID;
        this.projectName = projectName;
        this.projectStatus = projectStatus;
        this.projectDescription = projectDescription;
        this.projectStartDate = projectStartDate;
        this.projectFinishDate = projectFinishDate;
    }

    public String getProjectID() {
        return projectID;
    }

    public void setProjectID(String projectID) {
        this.projectID = projectID;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectStatus() {
        return projectStatus;
    }

    public void setProjectStatus(String projectStatus) {
        this.projectStatus = projectStatus;
    }

    public String getProjectDescription() {
        return projectDescription;
    }

    public void setProjectDescription(String projectDescription) {
        this.projectDescription = projectDescription;
    }

    public Date getProjectStartDate() {
        return projectStartDate;
    }

    public void setProjectStartDate(Date projectStartDate) {
        this.projectStartDate = projectStartDate;
    }

    public Date getProjectFinishDate() {
        return projectFinishDate;
    }

    public void setProjectFinishDate(Date projectFinishDate) {
        this.projectFinishDate = projectFinishDate;
    }

}
