package dev.projects.ppmtool.web;

import dev.projects.ppmtool.domain.Backlog;
import dev.projects.ppmtool.domain.Project;
import dev.projects.ppmtool.domain.ProjectTask;
import dev.projects.ppmtool.exceptions.ProjectNotFoundException;
import dev.projects.ppmtool.repositories.BacklogRepository;
import dev.projects.ppmtool.services.MapValidationErrorService;
import dev.projects.ppmtool.services.ProjectTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/backlog")
public class BacklogController {

    @Autowired
    private ProjectTaskService projectTaskService;

    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @Autowired
    private BacklogRepository backlogRepository;

    @PostMapping("/{backlog_id}")
    public ResponseEntity<?> addPTtoBacklog(@Valid @RequestBody ProjectTask projectTask, BindingResult result, @PathVariable String backlog_id){
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationErrorService(result);
        if(errorMap != null) return errorMap;

        ProjectTask projectTask1 = projectTaskService.addProjectTast(backlog_id,projectTask);

        return new ResponseEntity<ProjectTask>(projectTask1, HttpStatus.CREATED);
    }

    @GetMapping("/{backlog_id}")
    public Iterable<ProjectTask> getProjectBacklog(@PathVariable String backlog_id){
        return projectTaskService.findBacklogById(backlog_id);
    }

    @GetMapping("/{backlog_id}/{pt_id}")
    public ResponseEntity<?> getProjectTast(@PathVariable String backlog_id, @PathVariable String pt_id){
        ProjectTask projectTask = projectTaskService.findProjectTasktBySequence(backlog_id,pt_id);
        return new ResponseEntity<ProjectTask>(projectTask,HttpStatus.OK);
    }

    @PatchMapping("/{backlog_id}/{pt_id}")
    public  ResponseEntity<?> updateProjectTast(@Valid @RequestBody ProjectTask updatedTask,BindingResult result,
                                                @PathVariable String backlog_id, @PathVariable  String pt_id){
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationErrorService(result);
        if(errorMap != null) return errorMap;

        ProjectTask updatedProjectTask = projectTaskService.updateByProjectSequence(updatedTask,backlog_id,pt_id);

        return new ResponseEntity<ProjectTask>(updatedProjectTask,HttpStatus.OK);
    }

    @DeleteMapping("/{backlog_id}/{pt_id}")
    public ResponseEntity<?> deleteProjectTast(@PathVariable String backlog_id, @PathVariable  String pt_id){
        projectTaskService.deleteProjectTastByPSequence(backlog_id,pt_id);
        return new ResponseEntity<String>("Project Task With ID:'"+pt_id+"' was Deleted Successfully",HttpStatus.OK);
    }


}
