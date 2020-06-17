//package com.marshal.epoch.workflow.handler.impl;
//
//import java.util.HashMap;
//
//import com.marshal.epoch.core.component.SessionContext;
//import com.marshal.epoch.workflow.constant.ActivitiConstant;
//import com.marshal.epoch.workflow.entity.TaskActionRequestExt;
//import com.marshal.epoch.workflow.exception.TaskHandleException;
//import com.marshal.epoch.workflow.handler.TaskHandler;
//import org.activiti.engine.TaskService;
//import org.activiti.engine.impl.identity.Authentication;
//import org.activiti.engine.task.Task;
//import org.springframework.beans.factory.annotation.Autowired;
//
///**
// * @auth: Marshal
// * @date: 2019/7/4
// * @desc: 加签后处理人处理器(前加签)
// */
//public class ResolveTaskHandler implements TaskHandler {
//
//    @Autowired
//    private TaskService taskService;
//
//    @Override
//    public String getAction() {
//        return ActivitiConstant.ACTION_RESOLVE;
//    }
//
//    @Override
//    public void process(SessionContext sessionContext, Task taskEntity, TaskActionRequestExt actionRequest) throws TaskHandleException {
//        String taskId = taskEntity.getId();
//
//        Authentication.setAuthenticatedUserId(sessionContext.getEmployeeCode());
//
//        //这里以approveResult作为action用来区分同意和拒绝
//        taskService.addComment(taskEntity.getId(), taskEntity.getProcessInstanceId(), ActivitiConstant.APPROVE_ACTION, actionRequest.getApproveResult());
//        taskService.addComment(taskEntity.getId(), taskEntity.getProcessInstanceId(), ActivitiConstant.APPROVE_COMMENT, actionRequest.getComment());
//
//        HashMap<String, Object> variables = new HashMap<>();
//        variables.put(ActivitiConstant.APPROVE_RESULT, actionRequest.getApproveResult());
//        taskService.setVariables(taskId, variables);
//        taskService.resolveTask(taskId);
//
//    }
//}
