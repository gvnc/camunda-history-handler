package github.gvnc.camunda.starter.executionListener;

import github.gvnc.camunda.starter.service.CustomHistoryService;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.camunda.bpm.engine.impl.persistence.entity.ExecutionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SequenceFlowExecutionListener implements ExecutionListener {

    @Autowired
    private CustomHistoryService customHistoryService;

    @Override
    public void notify(DelegateExecution delegateExecution) {
        try {
            ExecutionEntity executionEntity = (ExecutionEntity) delegateExecution;
            String processKey = executionEntity.getProcessDefinition().getKey();
            String processInstanceId = executionEntity.getProcessInstanceId();
            String rootProcessInstanceId = executionEntity.getRootProcessInstanceId();
            String transitionName = executionEntity.getTransition().getId();
            if(executionEntity.getTransition().getProperty("name") != null){
                transitionName = executionEntity.getTransition().getProperty("name").toString();
            }
            log.info("Sequence flow event [processKey={}, rootProcessInstanceId={}, transitionName={}]", processKey, rootProcessInstanceId, transitionName);
            customHistoryService.saveStep(processInstanceId, rootProcessInstanceId, processKey, "Transition", transitionName);
        } catch (Exception e) {
            log.error("Failed to execute notify.", e);
        }
    }
}
