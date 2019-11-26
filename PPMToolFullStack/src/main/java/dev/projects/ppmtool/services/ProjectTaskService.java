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

    public ProjectTaskService() {

    }

    public ProjectTask addProjectTast(String projectIdentifier, ProjectTask projectTask){
        try {
            Backlog backlog = backlogRepository.findByProjectIdentifier(projectIdentifier);
            projectTask.setBacklog(backlog);
            Integer backlogSequence = backlog.getPTSequence();
            backlogSequence++;
            backlog.setPTSequence(backlogSequence);

            projectTask.setProjectSequence(projectIdentifier +"-"+backlogSequence);
            projectTask.setProjectIndetrifer(projectIdentifier);

            if(projectTask.getPriority() == 0 || projectTask.getPriority() == null){
                projectTask.setPriority(3);
            }

            if(projectTask.getStatus() == "" || projectTask.getStatus() == null) {
                projectTask.setStatus("TO_DO");
            }

             return projectTaskRepository.save(projectTask);
        } catch (Exception e){
            throw  new ProjectNotFoundException("Project Not Found");
        }
    }

    public Iterable<ProjectTask> findBacklogById(String id) {
        Project project = projectRepository.findByProjectIdentifier(id);

        if(project == null){
            throw  new ProjectNotFoundException("Project With ID: '"+id+"' Does Not Exist");
        }

        return projectTaskRepository.findByProjectIndetriferOrderByPriority(id);
    }


    public ProjectTask findProjectTasktBySequence(String backlog_id, String pt_id){
        Backlog backlog = backlogRepository.findByProjectIdentifier(backlog_id);
        if(backlog == null){
            throw  new ProjectNotFoundException("Project with ID:'"+backlog_id+"' Does Not Exist");
        }

        ProjectTask projectTask = projectTaskRepository.findByProjectSequence(pt_id);

        if(projectTask == null){
            throw new ProjectNotFoundException("Project Task with ID:'"+pt_id+"' Does Not Exsit");
        }

        if(!projectTask.getProjectIndetrifer().equals(backlog_id)){
            throw  new ProjectNotFoundException("Project Task '"+pt_id+"' Does Not Exist In Project:'"+backlog_id+"'");
        }

        return projectTask;
    }

    public ProjectTask updateByProjectSequence(ProjectTask updatedTask, String backlog_id,String pt_id){
        ProjectTask projectTask = findProjectTasktBySequence(backlog_id,pt_id);
        projectTask = updatedTask;
        return projectTaskRepository.save(projectTask);
    }

    public void deleteProjectTastByPSequence( String backlog_id,String pt_id){
        ProjectTask projectTask = findProjectTasktBySequence(backlog_id,pt_id);

        projectTaskRepository.delete(projectTask);
    }


}
