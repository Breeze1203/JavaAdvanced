package com.example.flowabledemo;

import com.example.flowabledemo.entity.PurchaseRequest;
import com.example.flowabledemo.service.PurchaseRequestRepository;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

/**
 * @ClassName ProcessSimulationRunner
 * @Author pt
 * @Description
 * @Date 2025/7/22 20:55
 **/
@Component
public class ProcessSimulationRunner implements CommandLineRunner {

    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private PurchaseRequestRepository purchaseRequestRepository;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("✅ --- Starting Process Simulation --- ✅");
        // Step 1: Create and save the business entity using JPA
        PurchaseRequest purchase = new PurchaseRequest();
        purchase.setApplicant("Alice");
        purchase.setItemName("New High-Performance Laptop");
        purchase.setAmount(4500.00);
        purchase.setStatus("PENDING_APPROVAL");
        purchaseRequestRepository.save(purchase);
        System.out.println("📝 JPA Entity Saved. Purchase ID: " + purchase.getId());

        // Step 2: Prepare variables and start the Flowable process
        Map<String, Object> variables = new HashMap<>();
        variables.put("manager", "user_bob");   // User 1
        variables.put("director", "user_charlie"); // User 2
        variables.put("ceo", "user_diana");      // User 3

        System.out.println("🚀 Starting process 'threeStepApproval' for business key: " + purchase.getId());
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("threeStepApproval", String.valueOf(purchase.getId()), variables);

        // Step 3: Simulate Manager (Bob) approval
        simulateApproval("user_bob", "Manager (Bob)");

        // Step 4: Simulate Director (Charlie) approval
        simulateApproval("user_charlie", "Director (Charlie)");

        // Step 5: Simulate CEO (Diana) approval
        simulateApproval("user_diana", "CEO (Diana)");

        // Step 6: Verify the process has ended
        long finishedCount = runtimeService.createProcessInstanceQuery().processInstanceId(processInstance.getId()).count();
        if (finishedCount == 0) {
            purchase.setStatus("APPROVED");
            purchaseRequestRepository.save(purchase);
            System.out.println("🎉 Process Finished! Purchase request status updated to APPROVED.");
        }

        System.out.println("✅ --- Process Simulation Finished --- ✅");
    }

    private void simulateApproval(String assignee, String role) {
        List<Task> tasks = taskService.createTaskQuery().taskAssignee(assignee).list();
        if (tasks.isEmpty()) {
            System.out.println("❌ ERROR: No tasks found for " + role);
            return;
        }
        Task task = tasks.get(0);
        System.out.println("👉 Found task for " + role + ": " + task.getName());
        taskService.complete(task.getId());
        System.out.println("👍 " + role + " has approved the task.");
    }
}