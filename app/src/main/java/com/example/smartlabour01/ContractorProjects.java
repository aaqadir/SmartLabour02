package com.example.smartlabour01;

public class ContractorProjects {
     private String ProjectName,ProjectType,ProjectLocation,ProjectStartDate;
    public ContractorProjects(){}
    ContractorProjects(String ProjectName, String ProjectType, String ProjectLocation, String ProjectStartDate){
        this.ProjectName = ProjectName;
        this.ProjectType = ProjectType;
        this.ProjectLocation = ProjectLocation;
        this.ProjectStartDate = ProjectStartDate;
    }

    String getProjectName() {
        return ProjectName;
    }

    public void setProjectName(String projectName) {
        ProjectName = projectName;
    }

    String getProjectType() {
        return ProjectType;
    }

    public void setProjectType(String projectType) {
        ProjectType = projectType;
    }

    String getProjectLocation() {
        return ProjectLocation;
    }

    public void setProjectLocation(String projectLocation) {
        ProjectLocation = projectLocation;
    }

    String getProjectStartDate() {
        return ProjectStartDate;
    }

    public void setProjectStartDate(String projectStartDate) {
        ProjectStartDate = projectStartDate;
    }
}
