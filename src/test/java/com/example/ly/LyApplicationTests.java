package com.example.ly;

import com.alibaba.fastjson2.JSON;
import org.flowable.engine.*;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.flowable.engine.impl.persistence.entity.ModelEntityImpl;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.Model;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
class LyApplicationTests {


    ProcessEngine processEngine = null;

    @BeforeEach
    void contextLoads() {
        ProcessEngineConfiguration cfg = new StandaloneProcessEngineConfiguration().setJdbcUrl("jdbc:mysql://192.168.36.130:3306/flowable?serverTimezone=UTC&&nullCatalogMeansCurrent=true&useSSL=false&allowPublicKeyRetrieval=true").setJdbcUsername("root").setJdbcPassword("Root@123456").setJdbcDriver("com.mysql.cj.jdbc.Driver")
                // 如果数据库中的表结构不存在就新建
                .setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
        processEngine = cfg.buildProcessEngine();
    }


    @Test
    public void testModel() {
        // 获取RepositoryService对象
        RepositoryService repositoryService = processEngine.getRepositoryService();
        ModelEntityImpl modelEntity = new ModelEntityImpl();
        modelEntity.setKey("LeaveApplication");
        modelEntity.setName("请假流程");
        repositoryService.saveModel(modelEntity);

    }

    @Test
    public void queryModel() {
        RepositoryService repositoryService = processEngine.getRepositoryService();
        Model leaveApplication = repositoryService.createModelQuery().modelKey("LeaveApplication").singleResult();
        System.out.println(JSON.toJSONString(leaveApplication));
        System.out.println(leaveApplication.getDeploymentId());
        List<Model> leaveApplication1 = repositoryService.createModelQuery().modelKey("LeaveApplication").list();

    }


    @Test
    public void saveModelResource() throws IOException {
        RepositoryService repositoryService = processEngine.getRepositoryService();
        InputStream systemResourceAsStream = ClassLoader.getSystemResourceAsStream("holiday.bpmn20.xml");
        byte[] bytes = systemResourceAsStream.readAllBytes();
        repositoryService.addModelEditorSource("2501", bytes);
    }


    @Test
    public void queryModelResource() {
        RepositoryService repositoryService = processEngine.getRepositoryService();
        byte[] modelEditorSource = repositoryService.getModelEditorSource("2501");
        String xml = new String(modelEditorSource);
        System.out.println(xml);
    }


    /**
     * 部署流程定义
     */
    @Test
    public void TestDeployment() {
        RepositoryService repositoryService = processEngine.getRepositoryService();

        //xml
        byte[] modelEditorSource = repositoryService.getModelEditorSource("2501");
        String xml = new String(modelEditorSource);

        //发布
        //可反复发布，后面的发布流程会按照版本号，进行管理
        Deployment deployment = repositoryService.createDeployment().addString("holiday.bpmn20.xml", xml).name("请假流程").deploy();
        System.out.println("deployment.getId()" + deployment.getId());//10001
        System.out.println("deployment.getName()" + deployment.getName());//请假流程
    }


    /**
     * 查询已部署的流程定义
     */
    @Test
    public void TestQueryDeployment() {
        RepositoryService repositoryService = processEngine.getRepositoryService();
        List<Deployment> list = repositoryService.createDeploymentQuery().list();
        list.stream().forEach(item -> {
            System.out.println("发布实例有：" + item.getId());
        });

    }


    /**
     * 删除流程定义
     */
    @Test
    public void TestDeploymentDelete() {
        // 获取RepositoryService对象
        RepositoryService repositoryService = processEngine.getRepositoryService();
        // 第一个参数是id，如果部署的流程启动了，就不允许删除了
        repositoryService.deleteDeployment("1");
        // 第二个参数是级联删除，如果流程启动了，相关的任务一并会被删除
        repositoryService.deleteDeployment("1", true);
    }


    /**
     *
     */
    @Test
    public void TestStartProcess() {
        //        // 获取RuntimeService对象
        RuntimeService runtimeService = processEngine.getRuntimeService();
        // 流程变量
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("employee", "zhang-test");
        variables.put("nrOfHolidays", 3);
        variables.put("description", "有事需请假");
        variables.put("approverId", "luoyong3");
//        variables.put("status",null);
        // 启动流程实例
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("LeaveApplication", variables);
        System.out.println("processInstance.getId()" + processInstance.getId());//47501
    }


    @Test
    public void TestQueryTask() {
        TaskService taskService = processEngine.getTaskService();
        List<Task> tasks = taskService.createTaskQuery().taskAssignee("luoyong3").list();
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println(("查询getId " + i + 1) + ") " + tasks.get(i).getId());
            System.out.println(("查询getName " + i + 1) + ") " + tasks.get(i).getName());
        }
    }


    @Test
    public void TestCompleteTask() {
        TaskService taskService = processEngine.getTaskService();
        Task task = taskService.createTaskQuery().taskAssignee("luoyong3").singleResult();
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("count", "first");
//        taskService.setVariable("15010", "status", "approve");
        System.out.println("变量");
        System.out.println(taskService.getVariables(task.getId()));
        variables.put("status", "approve");
        taskService.complete(task.getId(), variables);
    }


    @Test
    public void TestQuerySecondTask() {
        TaskService taskService = processEngine.getTaskService();
        List<Task> leaveApplication = taskService.createTaskQuery().taskCandidateOrAssigned("lisi").list();
        leaveApplication.stream().forEach(item -> {
            System.out.println("--------任务查询-----------------");
        });
    }

    @Test
    public void queryHistory() {
        HistoryService historyService = processEngine.getHistoryService();
        List<HistoricProcessInstance> leaveApplication = historyService.createHistoricProcessInstanceQuery().processInstanceId("42501").list();
        leaveApplication.stream().forEach(item -> {
            Map<String, Object> processVariables = item.getProcessVariables();
            System.out.println("变量" + JSON.toJSONString(processVariables));
            System.out.println("结果：" + JSON.toJSONString(item));
        });
    }


    @Test
    public void TestCompleteSecondTask() {
        TaskService taskService = processEngine.getTaskService();
        List<Task> lisi = taskService.createTaskQuery().taskCandidateOrAssigned("lisi").list();
        Task task = lisi.get(0);
        task.getProcessVariables();
        Map<String, Object> processVariables = task.getProcessVariables();
        System.out.println("流程中的变量：" + JSON.toJSONString(taskService.getVariables(task.getId())));
        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("status", "reject");
        taskService.complete(task.getId(), hashMap);
    }

    @Test
    public void taskQuery() {


    }

}
