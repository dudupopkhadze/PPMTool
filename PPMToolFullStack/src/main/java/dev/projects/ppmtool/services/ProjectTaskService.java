package dev.projects.ppmtool.services;

import dev.projects.ppmtool.domain.Backlog;
import dev.projects.ppmtool.domain.ProjectTask;
import dev.projects.ppmtool.repositories.BacklogRepository;
import dev.projects.ppmtool.repositories.ProjectTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectTaskService {
    @Autowired
    private BacklogRepository backlogRepository;

    @Autowired
    private ProjectTaskRepository projectTaskRepository;

    public ProjectTaskService() {

    }

    public ProjectTask addProjectTast(String projectIdentifier, ProjectTask projectTask){
        Backlog backlog = backlogRepository.findByProjectIdentifier(projectIdentifier);
        projectTask.setBacklog(backlog);
        Integer backlogSequence = backlog.getPTSequence();
        backlogSequence++;
        backlog.setPTSequence(backlogSequence);

        projectTask.setProjectSequence(projectIdentifier +"-"+backlogSequence);
        projectTask.setProjectIndetrifer(projectIdentifier);

        if(projectTask.getPriority() == null){
            projectTask.setPriority(3);
        }

        if(projectTask.getStatus() == "" || projectTask.getStatus() == null) {
            projectTask.setStatus("TO_DO");
        }

        return projectTaskRepository.save(projectTask);
    }
}
