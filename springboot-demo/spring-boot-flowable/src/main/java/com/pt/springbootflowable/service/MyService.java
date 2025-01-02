package com.pt.springbootflowable.service;

import com.pt.springbootflowable.entity.Person;
import com.pt.springbootflowable.repo.PersonRepository;
import jakarta.transaction.Transactional;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.task.Comment;
import org.flowable.task.api.Task;
import org.flowable.task.api.TaskQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @ClassName MyService
 * @Author pt
 * @Description
 * @Date 2024/12/31 10:25
 **/
@Service
@Transactional
public class MyService {

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private PersonRepository personRepository;
    /**
     * 启动流程实例
     * @param assignee 流程处理人
     */
    public void startProcess(String assignee) {
        Person person = personRepository.findByUsername(assignee);
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("person", person);
        runtimeService.startProcessInstanceByKey("oneTaskProcess", variables);
    }


    /**
     * 获取任务
     * @param assignee 流程处理人
     * @return 任务列表
     */
    public List<Task> getTasks(String assignee) {
        return taskService.createTaskQuery()
                .taskAssignee(assignee)
                .list();
    }

    /**
     * 完成任务
     * @param taskId 任务ID
     */ 
    public void completeTask(String taskId) {
        taskService.complete(taskId);
    }

    public void claimTask(String taskId, String userId) {
        taskService.claim(taskId, userId);
    }

    /**
     * 设置任务处理人
     * @param taskId 任务ID
     * @param userId 处理人ID
     */
    public void setAssignee(String taskId, String userId) {
        taskService.setAssignee(taskId, userId);
    }

    /**
     * 删除任务
     * @param taskId 任务ID
     * @param deleteReason 删除原因
     */
    public void deleteTask(String taskId, String deleteReason) {
        taskService.deleteTask(taskId, deleteReason);
    }

    /**
     * 添加任务评论
     * @param taskId 任务ID
     * @param userId 处理人ID
     * @param message 评论内容
     */
    public void addTaskComment(String taskId, String userId, String message) {
        taskService.addComment(taskId, null, message);
    }

    /**
     * 设置任务截止日期
     * @param taskId 任务ID
     * @param dueDate 截止日期
     */
    public void setDueDate(String taskId, Date dueDate) {
        taskService.setDueDate(taskId, dueDate);
    }

    /**
     * 设置任务优先级
     * @param taskId 任务ID
     * @param priority 优先级
     */
    public void setPriority(String taskId, int priority) {
        taskService.setPriority(taskId, priority);
    }

    /**
     * 获取任务的评论列表
     * @param taskId 任务ID
     * @return 评论列表
     */
    public List<Comment> getTaskComments(String taskId) {
        return taskService.getTaskComments(taskId);
    }

    /**
     * 获取任务的变量
     * @param taskId 任务ID
     * @param variableName 变量名
     * @return 变量值
     */
    public Object getVariable(String taskId, String variableName) {
        return taskService.getVariable(taskId, variableName);
    }

    /**
     * 设置任务的变量
     * @param taskId 任务ID
     * @param variableName 变量名
     * @param value 变量值
     */
    public void setVariable(String taskId, String variableName, Object value) {
        taskService.setVariable(taskId, variableName, value);
    }

    /**
     * 获取任务的所有变量
     * @param taskId 任务ID
     * @return 变量Map
     */
    public Map<String, Object> getVariables(String taskId) {
        return taskService.getVariables(taskId);
    }

    /**
     * 设置任务的多个变量
     * @param taskId 任务ID
     * @param variables 变量Map
     */
    public void setVariables(String taskId, Map<String, Object> variables) {
        taskService.setVariables(taskId, variables);
    }

    /**
     * 删除任务变量
     * @param taskId 任务ID
     * @param variableName 变量名
     */
    public void removeVariable(String taskId, String variableName) {
        taskService.removeVariable(taskId, variableName);
    }

    /**
     * 删除任务的多个变量
     * @param taskId 任务ID
     * @param variableNames 变量名列表
     */
    public void removeVariables(String taskId, Collection<String> variableNames) {
        taskService.removeVariables(taskId, variableNames);
    }

    /**
     * 获取任务的本地变量
     * @param taskId 任务ID
     * @param variableName 变量名
     * @return 变量值
     */
    public Object getVariableLocal(String taskId, String variableName) {
        return taskService.getVariableLocal(taskId, variableName);
    }

    /**
     * 设置任务的本地变量
     * @param taskId 任务ID
     * @param variableName 变量名
     * @param value 变量值
     */
    public void setVariableLocal(String taskId, String variableName, Object value) {
        taskService.setVariableLocal(taskId, variableName, value);
    }

    /**
     * 获取任务的所有本地变量
     * @param taskId 任务ID
     * @return 变量Map
     */
    public Map<String, Object> getVariablesLocal(String taskId) {
        return taskService.getVariablesLocal(taskId);
    }

    /**
     * 设置任务的多个本地变量
     * @param taskId 任务ID
     * @param variables 变量Map
     */
    public void setVariablesLocal(String taskId, Map<String, Object> variables) {
        taskService.setVariablesLocal(taskId, variables);
    }

    /**
     * 删除任务的本地变量
     * @param taskId 任务ID
     * @param variableName 变量名
     */
    public void removeVariableLocal(String taskId, String variableName) {
        taskService.removeVariableLocal(taskId, variableName);
    }

    /**
     * 删除任务的多个本地变量
     * @param taskId 任务ID
     * @param variableNames 变量名列表
     */
    public void removeVariablesLocal(String taskId, Collection<String> variableNames) {
        taskService.removeVariablesLocal(taskId, variableNames);
    }

    /**
     * 获取任务
     * @param taskId 任务ID
     * @return 任务对象
     */
    public Task getTask(String taskId) {
        return taskService.createTaskQuery().taskId(taskId).singleResult();
    }

    /**
     * 查询任务列表
     * @param taskQuery 任务查询对象
     * @return 任务列表
     */
    public List<Task> getTasks(TaskQuery taskQuery) {
        return taskQuery.list();
    }

    /**
     * 完成任务
     * @param taskId 任务ID
     */
    public void complete(String taskId) {
        taskService.complete(taskId);
    }

    /**
     * 完成任务并设置变量
     * @param taskId 任务ID
     * @param variables 变量Map
     */
    public void complete(String taskId, Map<String, Object> variables) {
        taskService.complete(taskId, variables);
    }

    /**
     * 委派任务
     * @param taskId 任务ID
     * @param userId 被委派人ID
     */
    public void delegateTask(String taskId, String userId) {
        taskService.delegateTask(taskId, userId);
    }

    /**
     * 解决委派任务
     * @param taskId 任务ID
     */
    public void resolveTask(String taskId) {
        taskService.resolveTask(taskId);
    }

    /**
     * 设置任务所有者
     * @param taskId 任务ID
     * @param userId 所有者ID
     */
    public void setOwner(String taskId, String userId) {
        taskService.setOwner(taskId, userId);
    }

    public void createDemoUsers() {
        if (personRepository.findAll().isEmpty()) {
            personRepository.save(new Person("jbarrez", "Joram", "Barrez", new Date()));
            personRepository.save(new Person("trademakers", "Tijs", "Rademakers", new Date()));
        }
    }

}