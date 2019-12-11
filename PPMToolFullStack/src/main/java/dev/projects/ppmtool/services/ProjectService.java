package dev.projects.ppmtool.services;

import dev.projects.ppmtool.domain.Backlog;
import dev.projects.ppmtool.domain.Project;
import dev.projects.ppmtool.domain.User;
import dev.projects.ppmtool.exceptions.ProjectIdException;
import dev.projects.ppmtool.exceptions.ProjectNotFoundException;
import dev.projects.ppmtool.repositories.BacklogRepository;
import dev.projects.ppmtool.repositories.ProjectRepository;
import dev.projects.ppmtool.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private BacklogRepository backlogRepository;

    @Autowired
    private UserRepository userRepository;

    public Project saveOrUpdateProject(Project project, String username){

        if(project.getId() != null){
            Project existingProject = projectRepository.findByProjectIdentifier(project.getProjectIdentifier());
            if(existingProject == null){
                throw new ProjectNotFoundException("Project With ID:'"+project.getProjectIdentifier()+
                        "' Can Bot be Updated, Because It Does Not Exist");
            } else if(!existingProject.getProjectLeader().equals(username)){

            }
        }

        try{
            User user = userRepository.findByUsername(username);
            
            project.setUser(user);
            project.setProjectLeader(user.getUsername());
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

    public Project findProjectByIndentifier(String projectId, String username){
        Project pr = projectRepository.findByProjectIdentifier(projectId.toUpperCase());

        if(pr == null){
            throw  new ProjectIdException("Project ID '"+projectId.toUpperCase()+"' Does Not Exists" );
        }

        if(!pr.getProjectLeader().equals(username)){
            throw new ProjectNotFoundException("Project Does Not Exists in Your Account");
        }


        return  pr;
    }

    public Iterable<Project> findAllProjects(String username){
        return projectRepository.findAllByProjectLeader(username);
    }

    public void deleteProjectByIdentifier(String projectId, String username){
        projectRepository.delete(findProjectByIndentifier(projectId,username));
    }

}




