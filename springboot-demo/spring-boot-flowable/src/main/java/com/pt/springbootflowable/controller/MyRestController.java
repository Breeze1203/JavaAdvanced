package com.pt.springbootflowable.controller;

import com.pt.springbootflowable.service.MyService;
import org.flowable.bpmn.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @ClassName MyRestController
 * @Author pt
 * @Description
 * @Date 2024/12/31 10:32
 **/
@RestController
public class MyRestController {
    @Autowired
    private MyService myService;

    @PostMapping(value="/process")
    public void startProcessInstance(@RequestParam String assignee) {
        myService.startProcess(assignee);
    }

    @GetMapping(value="/tasks", produces=MediaType.APPLICATION_JSON_VALUE)
    public List<org.flowable.task.api.Task> getTasks(@RequestParam String assignee) {
        return myService.getTasks(assignee);
    }

    @GetMapping(value="/task/{taskId}")
    public org.flowable.task.api.Task getTask(@PathVariable String taskId) {
        return myService.getTask(taskId);
    }

    @PostMapping(value="/tasks/{taskId}")
    public void completeTask(@PathVariable String taskId) {
        myService.completeTask(taskId);
    }

    @PostMapping(value="/tasks/{taskId}/complete")
    public void complete(@PathVariable String taskId, @RequestBody(required = false) Map<String, Object> variables) {
        if(variables != null) {
            myService.complete(taskId, variables);
        } else {
            myService.complete(taskId);
        }
    }

    @PostMapping(value="/tasks/{taskId}/claim")
    public void claimTask(@PathVariable String taskId, @RequestParam String userId) {
        myService.claimTask(taskId, userId);
    }

    @PostMapping(value="/tasks/{taskId}/delegate")
    public void delegateTask(@PathVariable String taskId, @RequestParam String userId) {
        myService.delegateTask(taskId, userId);
    }

    @PostMapping(value="/tasks/{taskId}/resolve")
    public void resolveTask(@PathVariable String taskId) {
        myService.resolveTask(taskId);
    }

    @PostMapping(value="/tasks/{taskId}/assignee")
    public void setAssignee(@PathVariable String taskId, @RequestParam String userId) {
        myService.setAssignee(taskId, userId);
    }

    @PostMapping(value="/tasks/{taskId}/owner")
    public void setOwner(@PathVariable String taskId, @RequestParam String userId) {
        myService.setOwner(taskId, userId);
    }

    @DeleteMapping(value="/tasks/{taskId}")
    public void deleteTask(@PathVariable String taskId, @RequestParam String reason) {
        myService.deleteTask(taskId, reason);
    }

    @PostMapping(value="/tasks/{taskId}/comments")
    public void addComment(@PathVariable String taskId, @RequestParam String userId, @RequestParam String message) {
        myService.addTaskComment(taskId, userId, message);
    }

    @GetMapping(value="/tasks/{taskId}/comments")
    public List<org.flowable.engine.task.Comment> getComments(@PathVariable String taskId) {
        return myService.getTaskComments(taskId);
    }

    @PostMapping(value="/tasks/{taskId}/dueDate")
    public void setDueDate(@PathVariable String taskId, @RequestParam Date dueDate) {
        myService.setDueDate(taskId, dueDate);
    }

    @PostMapping(value="/tasks/{taskId}/priority")
    public void setPriority(@PathVariable String taskId, @RequestParam int priority) {
        myService.setPriority(taskId, priority);
    }

    @GetMapping(value="/tasks/{taskId}/variable/{variableName}")
    public Object getVariable(@PathVariable String taskId, @PathVariable String variableName) {
        return myService.getVariable(taskId, variableName);
    }

    @PostMapping(value="/tasks/{taskId}/variable")
    public void setVariable(@PathVariable String taskId, @RequestParam String variableName, @RequestBody Object value) {
        myService.setVariable(taskId, variableName, value);
    }

    @GetMapping(value="/tasks/{taskId}/variables")
    public Map<String, Object> getVariables(@PathVariable String taskId) {
        return myService.getVariables(taskId);
    }

    @PostMapping(value="/tasks/{taskId}/variables")
    public void setVariables(@PathVariable String taskId, @RequestBody Map<String, Object> variables) {
        myService.setVariables(taskId, variables);
    }

    @DeleteMapping(value="/tasks/{taskId}/variable/{variableName}")
    public void removeVariable(@PathVariable String taskId, @PathVariable String variableName) {
        myService.removeVariable(taskId, variableName);
    }

    @DeleteMapping(value="/tasks/{taskId}/variables")
    public void removeVariables(@PathVariable String taskId, @RequestBody Collection<String> variableNames) {
        myService.removeVariables(taskId, variableNames);
    }

    @GetMapping(value="/tasks/{taskId}/variable-local/{variableName}")
    public Object getVariableLocal(@PathVariable String taskId, @PathVariable String variableName) {
        return myService.getVariableLocal(taskId, variableName);
    }

    @PostMapping(value="/tasks/{taskId}/variable-local")
    public void setVariableLocal(@PathVariable String taskId, @RequestParam String variableName, @RequestBody Object value) {
        myService.setVariableLocal(taskId, variableName, value);
    }

    @GetMapping(value="/tasks/{taskId}/variables-local")
    public Map<String, Object> getVariablesLocal(@PathVariable String taskId) {
        return myService.getVariablesLocal(taskId);
    }

    @PostMapping(value="/tasks/{taskId}/variables-local")
    public void setVariablesLocal(@PathVariable String taskId, @RequestBody Map<String, Object> variables) {
        myService.setVariablesLocal(taskId, variables);
    }

    @DeleteMapping(value="/tasks/{taskId}/variable-local/{variableName}")
    public void removeVariableLocal(@PathVariable String taskId, @PathVariable String variableName) {
        myService.removeVariableLocal(taskId, variableName);
    }

    @DeleteMapping(value="/tasks/{taskId}/variables-local")
    public void removeVariablesLocal(@PathVariable String taskId, @RequestBody Collection<String> variableNames) {
        myService.removeVariablesLocal(taskId, variableNames);
    }

}
