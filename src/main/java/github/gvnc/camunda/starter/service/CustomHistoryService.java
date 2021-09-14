package github.gvnc.camunda.starter.service;

import github.gvnc.camunda.starter.repository.FlowStepHistoryRepository;
import github.gvnc.camunda.starter.repository.model.FlowStepHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomHistoryService {

    @Autowired
    private FlowStepHistoryRepository flowStepHistoryRepository;

    public void saveStep(String rootProcessInstanceId, String processInstanceId, String processName,
                         String stepType, String stepName){

        FlowStepHistory flowStepHistory = new FlowStepHistory();
        flowStepHistory.setRootProcessInstanceId(rootProcessInstanceId);
        flowStepHistory.setProcessInstanceId(processInstanceId);
        flowStepHistory.setProcessName(processName);
        flowStepHistory.setStepType(stepType);
        flowStepHistory.setStepName(stepName);
        flowStepHistoryRepository.save(flowStepHistory);
    }
}
