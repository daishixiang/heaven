package com.daisx.heaven.controller;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.camunda.bpm.engine.*;
import org.camunda.bpm.engine.history.HistoricActivityInstance;
import org.camunda.bpm.engine.history.HistoricTaskInstance;
import org.camunda.bpm.engine.repository.Deployment;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Comment;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @Author: daisx
 * @Date: 2021/10/19 9:40
 */
@RestController
@RequestMapping("/camunda")
@Slf4j
public class CamundaController {
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private HistoryService historyService;

    /**
     * 激活状态的最新版本的流程定义
     */
    @GetMapping("/definition")
    public List<String> definition() {

        List<ProcessDefinition> processDefinitionList = repositoryService.createProcessDefinitionQuery().active().latestVersion().list();
        log.info("> 处于激活状态的最新版本的流程定义数量: " + processDefinitionList.size());
        List<String> result=new ArrayList<>();
        for (ProcessDefinition pd : processDefinitionList) {
            log.info("\t ===> Process definition ID: " + pd.getId() + " 版本：" + pd.getVersion());
            log.info("\t ===> Process definition KEY: " + pd.getKey() + " 版本：" + pd.getVersion());
            result.add(pd.getId());

        }
        return result;
    }

    /**
     * 部署流程
     * @param file
     * @return
     */
    @PostMapping ("/deploymentForFile")
    public String deploymentForFile (@RequestBody MultipartFile file) throws IOException {
        InputStream inputStream = file.getInputStream();
        Deployment deployment = repositoryService.createDeployment()
                .name(file.getOriginalFilename())
                .addInputStream(file.getOriginalFilename(), inputStream)
                .deploy();
        System.out.println("部署ID： " + deployment.getId()+"; 部署名称： " + deployment.getName());
        return deployment.getId();
    }

    /**
     * 部署流程
     * @param processName
     * @param bpmnXml
     * @return
     */
    @GetMapping("/deployment")
    public String deployment (String processName,String bpmnXml) {
//        bpmnXml="<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
//                "<bpmn2:definitions xmlns:bpmn2=\"http://www.omg.org/spec/BPMN/20100524/MODEL\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:bpmndi=\"http://www.omg.org/spec/BPMN/20100524/DI\" xmlns:dc=\"http://www.omg.org/spec/DD/20100524/DC\" xmlns:di=\"http://www.omg.org/spec/DD/20100524/DI\" id=\"sample-diagram\" targetNamespace=\"http://bpmn.io/schema/bpmn\" xsi:schemaLocation=\"http://www.omg.org/spec/BPMN/20100524/MODEL BPMN20.xsd\"><bpmn2:process id=\"process1567044459787\" name=\"流程1567044459787\"><bpmn2:documentation>描述</bpmn2:documentation><bpmn2:startEvent id=\"StartEvent_1\" name=\"开始\"><bpmn2:outgoing>Flow_0y9wq64</bpmn2:outgoing></bpmn2:startEvent><bpmn2:sequenceFlow id=\"Flow_0y9wq64\" sourceRef=\"StartEvent_1\" targetRef=\"Activity_19q4h4w\" /><bpmn2:task id=\"Activity_0i0e69k\" name=\"test\"><bpmn2:incoming>Flow_0qi6d9h</bpmn2:incoming><bpmn2:outgoing>Flow_1qb9wul</bpmn2:outgoing></bpmn2:task><bpmn2:sequenceFlow id=\"Flow_0qi6d9h\" sourceRef=\"Activity_19q4h4w\" targetRef=\"Activity_0i0e69k\" /><bpmn2:task id=\"Activity_0cqmr48\" name=\"admin\"><bpmn2:incoming>Flow_1qb9wul</bpmn2:incoming><bpmn2:outgoing>Flow_0b1zwk1</bpmn2:outgoing></bpmn2:task><bpmn2:sequenceFlow id=\"Flow_1qb9wul\" sourceRef=\"Activity_0i0e69k\" targetRef=\"Activity_0cqmr48\" /><bpmn2:endEvent id=\"Event_14dyyco\"><bpmn2:incoming>Flow_0b1zwk1</bpmn2:incoming></bpmn2:endEvent><bpmn2:sequenceFlow id=\"Flow_0b1zwk1\" sourceRef=\"Activity_0cqmr48\" targetRef=\"Event_14dyyco\" /><bpmn2:userTask id=\"Activity_19q4h4w\" name=\"other\"><bpmn2:incoming>Flow_0y9wq64</bpmn2:incoming><bpmn2:outgoing>Flow_0qi6d9h</bpmn2:outgoing></bpmn2:userTask></bpmn2:process><bpmndi:BPMNDiagram id=\"BPMNDiagram_1\"><bpmndi:BPMNPlane id=\"BPMNPlane_1\" bpmnElement=\"process1567044459787\"><bpmndi:BPMNEdge id=\"Flow_0y9wq64_di\" bpmnElement=\"Flow_0y9wq64\"><di:waypoint x=\"708\" y=\"-20\" /><di:waypoint x=\"760\" y=\"-20\" /></bpmndi:BPMNEdge><bpmndi:BPMNEdge id=\"Flow_0qi6d9h_di\" bpmnElement=\"Flow_0qi6d9h\"><di:waypoint x=\"860\" y=\"-20\" /><di:waypoint x=\"920\" y=\"-20\" /></bpmndi:BPMNEdge><bpmndi:BPMNEdge id=\"Flow_1qb9wul_di\" bpmnElement=\"Flow_1qb9wul\"><di:waypoint x=\"1020\" y=\"-20\" /><di:waypoint x=\"1080\" y=\"-20\" /></bpmndi:BPMNEdge><bpmndi:BPMNEdge id=\"Flow_0b1zwk1_di\" bpmnElement=\"Flow_0b1zwk1\"><di:waypoint x=\"1180\" y=\"-20\" /><di:waypoint x=\"1242\" y=\"-20\" /></bpmndi:BPMNEdge><bpmndi:BPMNShape id=\"_BPMNShape_StartEvent_2\" bpmnElement=\"StartEvent_1\"><dc:Bounds x=\"672\" y=\"-38\" width=\"36\" height=\"36\" /><bpmndi:BPMNLabel><dc:Bounds x=\"679\" y=\"-62\" width=\"22\" height=\"14\" /></bpmndi:BPMNLabel></bpmndi:BPMNShape><bpmndi:BPMNShape id=\"Activity_0i0e69k_di\" bpmnElement=\"Activity_0i0e69k\"><dc:Bounds x=\"920\" y=\"-60\" width=\"100\" height=\"80\" /></bpmndi:BPMNShape><bpmndi:BPMNShape id=\"Activity_0cqmr48_di\" bpmnElement=\"Activity_0cqmr48\"><dc:Bounds x=\"1080\" y=\"-60\" width=\"100\" height=\"80\" /></bpmndi:BPMNShape><bpmndi:BPMNShape id=\"Event_14dyyco_di\" bpmnElement=\"Event_14dyyco\"><dc:Bounds x=\"1242\" y=\"-38\" width=\"36\" height=\"36\" /></bpmndi:BPMNShape><bpmndi:BPMNShape id=\"Activity_0ny20ac_di\" bpmnElement=\"Activity_19q4h4w\"><dc:Bounds x=\"760\" y=\"-60\" width=\"100\" height=\"80\" /></bpmndi:BPMNShape></bpmndi:BPMNPlane></bpmndi:BPMNDiagram></bpmn2:definitions>";
        InputStream in = new ByteArrayInputStream(bpmnXml.getBytes());

        Deployment deployment = repositoryService.createDeployment()
                .name(processName)
                .addInputStream(processName+".bpmn20.xml", in)
                .deploy();
        System.out.println("部署ID： " + deployment.getId()+"; 部署名称： " + deployment.getName());
        return deployment.getId();
    }

    /**
     * 激活状态的最新版本的流程定义
     */
    @GetMapping("/definitionByDeployment")
    public List<Map<String,Object>> definitionByDeployment(String deploymentId) {
        List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery().deploymentId(deploymentId).active().list();
        List<Map<String,Object>> result=new ArrayList<>();
        for (ProcessDefinition pd : list) {
            Map<String,Object> map=new HashMap<>();
            map.put("id",pd.getId());
            map.put("description",pd.getDescription());
            map.put("versionTag",pd.getVersionTag());
            map.put("category",pd.getCategory());
            map.put("deploymentId",pd.getDeploymentId());
            map.put("diagramResourceName",pd.getDiagramResourceName());
            map.put("name",pd.getName());
            map.put("resourceName",pd.getResourceName());
            result.add(map);
        }
        return result;
    }

    /**
     * 启动流程实例
     * @param processDefinitionId
     * @return
     */
    @GetMapping("/start")
    public String start (String processDefinitionId) {
        //流程启动中设置的map成为全局变量，中间任何时候能通过runtimeService.getVariable(task.getExecutionId(),key)获取值Object类型的数据
        Map<String, Object> params = new HashMap<>();
        ProcessInstance processInstance = runtimeService.startProcessInstanceById(processDefinitionId,params);
        System.out.println("流程实例的ProcessInstanceId: " + processInstance.getId()+"; 流程实例的ProcessDefinitionId: " + processInstance.getProcessDefinitionId());
        return processInstance.getId();
    }

    /**
     * 任务列表
     * @param assignee
     * @return
     */
    @GetMapping("/taskList")
    public  List<Map>  taskList (String assignee) {
        List<Task> list = taskService.createTaskQuery().taskAssignee(assignee).list();
        List<Map> taskLikeList = new ArrayList<>();
        for (Task task : list) {
            Map taskMap=new HashMap();
            taskMap.put("taskId",task.getId());
            taskMap.put("taskName",task.getName());
            taskMap.put("processInstanceId",task.getProcessInstanceId());
            taskMap.put("processDefinitionId",task.getProcessDefinitionId()); 
            taskMap.put("createTime",task.getCreateTime());
            taskMap.put("assignee",task.getAssignee());
            taskMap.put("description",task.getDescription());
            taskMap.put("dueDate",task.getDueDate());
            taskMap.put("executionId",task.getExecutionId());
            taskMap.put("owner",task.getOwner());
            taskLikeList.add(taskMap);
        }
        return taskLikeList;
    }

    /**
     * 已完成的任务
     * @param assignee
     * @return
     */
    @GetMapping("/completedTaskList")
    public List<Map> completedTaskList(String assignee){

        List<HistoricTaskInstance> list1 = historyService
                .createHistoricTaskInstanceQuery()
                .taskAssignee(assignee)
                .finished()
                .list();
        List<Map> taskLikeList = new ArrayList<>();
        for (HistoricTaskInstance hti : list1) {
            Map<String,Object> taskLike = new HashMap<>();
            taskLike.put("assignee",hti.getAssignee());
            taskLike.put("createTime",hti.getStartTime());
            //这个传入参数就是启动时设置map的
            taskLike.put("description",hti.getDescription());
            taskLike.put("executionId",hti.getExecutionId());
            taskLike.put("processDefinitionId",hti.getProcessDefinitionId());
            taskLike.put("taskId",hti.getId());
            taskLike.put("taskName",hti.getName());
            taskLike.put("taskDefinitionKey",hti.getTaskDefinitionKey());
            taskLike.put("processInstanceId",hti.getProcessInstanceId());
            taskLikeList.add(taskLike);
        }
        return taskLikeList;
    }

    /**
     * complete
     * @param taskId
     * @return
     */
    @GetMapping("/complete")
    public String complete (String taskId,String assignee,String message) {
        Task task=taskService.createTaskQuery().taskId(taskId).taskAssignee(assignee).singleResult();
        if (task!=null) {
            taskService.addComment(taskId, task.getProcessInstanceId(), message);
            System.out.println("taskId=" + taskId + "; assignee=" + assignee);

            taskService.complete(taskId);
            return "审批完成";
        }else {
            return "没有可执行任务";
        }
    }


    /**
     * 流程历史任务
     * @param processInstanceId
     * @return
     */
    @GetMapping("/history")
    public List<Map> history (String processInstanceId) {
        List<HistoricActivityInstance> list = historyService.createHistoricActivityInstanceQuery()
                .processInstanceId(processInstanceId)
                .orderByHistoricActivityInstanceStartTime()
                .asc()
                .list();
        List<Map> result=new ArrayList<>(list.size());
        for (HistoricActivityInstance historicActivityInstance : list) {
            Map<String,Object> map=new HashMap<>();
            String taskId = historicActivityInstance.getTaskId();
            List<Comment> taskComments = taskService.getTaskComments(taskId);
            map.put("activityName",historicActivityInstance.getActivityName());
            map.put("activityType",historicActivityInstance.getActivityType());
            map.put("assignee",historicActivityInstance.getAssignee()==null?"无":historicActivityInstance.getAssignee());

            if (taskComments.size()>0){
                map.put("message",taskComments.get(0).getFullMessage());
            }else {
                map.put("message","无");
            }
            result.add(map);
        }
        return result;
    }



}
