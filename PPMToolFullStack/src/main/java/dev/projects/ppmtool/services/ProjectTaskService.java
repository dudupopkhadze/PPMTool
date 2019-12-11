package dev.projects.ppmtool.services;

import dev.projects.ppmtool.domain.Backlog;
import dev.projects.ppmtool.domain.Project;
import dev.projects.ppmtool.domain.ProjectTask;
import dev.projects.ppmtool.exceptions.ProjectNotFoundException;
import dev.projects.ppmtool.repositories.BacklogRepository;
import dev.projects.ppmtool.repositories.ProjectRepository;
import dev.projects.ppmtool.repositories.ProjectTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectTaskService {
    @Autowired
    private BacklogRepository backlogRepository;

    @Autowired
    private ProjectTaskRepository projectTaskRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ProjectService projectService;

    public ProjectTaskService() {

    }

    public ProjectTask addProjectTasK(String projectIdentifier, ProjectTask projectTask, String username){


            Backlog backlog = projectService.findProjectByIndentifier(projectIdentifier,username).getBacklog();
            projectTask.setBacklog(backlog);
            Integer backlogSequence = backlog.getPTSequence();
            backlogSequence++;
            backlog.setPTSequence(backlogSequence);

            projectTask.setProjectSequence(projectIdentifier +"-"+backlogSequence);
            projectTask.setProjectIndetrifer(projectIdentifier);

            if(projectTask.getPriority() == null  || projectTask.getPriority() == 0 ){
                projectTask.setPriority(3);
            }

            if(projectTask.getStatus() == "" || projectTask.getStatus() == null) {
                projectTask.setStatus("TO_DO");
            }

             return projectTaskRepository.save(projectTask);
    }

    public Iterable<ProjectTask> findBacklogById(String id, String username) {
        projectService.findProjectByIndentifier(id,username);

        return projectTaskRepository.findByProjectIndetriferOrderByPriority(id);
    }


    public ProjectTask findProjectTaskBySequence(String backlog_id, String pt_id, String username){
        projectService.findProjectByIndentifier(backlog_id,username);

        ProjectTask projectTask = projectTaskRepository.findByProjectSequence(pt_id);

        if(projectTask == null){
            throw new ProjectNotFoundException("Project Task with ID:'"+pt_id+"' Does Not Exsit");
        }

        if(!projectTask.getProjectIndetrifer().equals(backlog_id)){
            throw  new ProjectNotFoundException("Project Task '"+pt_id+"' Does Not Exist In Project:'"+backlog_id+"'");
        }

        return projectTask;
    }

    public ProjectTask updateByProjectSequence(ProjectTask updatedTask, String backlog_id,String pt_id,String username){
        ProjectTask projectTask = findProjectTaskBySequence(backlog_id,pt_id,username); // cheking if valid
        projectTask = updatedTask;
        return projectTaskRepository.save(projectTask);
    }

    public void deleteProjectTastByPSequence( String backlog_id,String pt_id, String username){
        ProjectTask projectTask = findProjectTaskBySequence(backlog_id,pt_id,username);

        projectTaskRepository.delete(projectTask);
    }


}
