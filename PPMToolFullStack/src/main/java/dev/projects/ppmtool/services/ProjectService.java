package dev.projects.ppmtool.services;

import dev.projects.ppmtool.domain.Backlog;
import dev.projects.ppmtool.domain.Project;
import dev.projects.ppmtool.exceptions.ProjectIdException;
import dev.projects.ppmtool.repositories.BacklogRepository;
import dev.projects.ppmtool.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private BacklogRepository backlogRepository;

    public Project saveOrUpdateProject(Project project){
        try{
            project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());

            if(project.getId()==null){
                Backlog backlog = new Backlog();
                project.setBacklog(backlog);
                backlog.setProject(project);
                backlog.setProjectIdentifier(project.getProjectIdentifier());
            } else {
                project.setBacklog(backlogRepository.findByProjectIdentifier(project.getProjectIdentifier()));
            }

            return  projectRepository.save(project);
        } catch (Exception e){
            throw  new ProjectIdException("Project ID '"+project.getProjectIdentifier().toUpperCase()+"' Already Exists" );
        }
    }

    public Project findProjectByIndentifier(String projectId){
        Project pr = projectRepository.findByProjectIdentifier(projectId.toUpperCase());

        if(pr == null){
            throw  new ProjectIdException("Project ID '"+projectId.toUpperCase()+"' Does Not Exists" );
        }

        return  pr;
    }

    public Iterable<Project> findAllProjects(){
        return projectRepository.findAll();
    }

    public void deleteProjectByIdentifier(String projectId){
        Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());
        if(project == null){
            throw new ProjectIdException("Can not Delete Project With ID '"+projectId.toUpperCase()+"'. Project Does not Exist");
        }
        projectRepository.delete(project);
    }

}




