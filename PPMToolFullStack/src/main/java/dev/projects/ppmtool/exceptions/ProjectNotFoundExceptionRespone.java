package dev.projects.ppmtool.exceptions;

public class ProjectNotFoundExceptionRespone {
    private String ProjectNotFound;

    public ProjectNotFoundExceptionRespone(String projectNotFound) {
        ProjectNotFound = projectNotFound;
    }

    public String getProjectNotFound() {
        return ProjectNotFound;
    }

    public void setProjectNotFound(String projectNotFound) {
        ProjectNotFound = projectNotFound;
    }
}
