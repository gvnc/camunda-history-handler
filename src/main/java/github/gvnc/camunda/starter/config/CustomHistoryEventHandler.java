package github.gvnc.camunda.starter.config;

import github.gvnc.camunda.starter.service.CustomHistoryService;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.impl.history.event.HistoricActivityInstanceEventEntity;
import org.camunda.bpm.engine.impl.history.event.HistoricProcessInstanceEventEntity;
import org.camunda.bpm.engine.impl.history.event.HistoryEvent;
import org.camunda.bpm.engine.impl.history.handler.HistoryEventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CustomHistoryEventHandler implements HistoryEventHandler {

    @Autowired
    private CustomHistoryService historyService;

    @Override
    public void handleEvent(HistoryEvent historyEvent) {
        try {
            if (historyEvent instanceof HistoricActivityInstanceEventEntity) {
                handleActivityEventy((HistoricActivityInstanceEventEntity) historyEvent);
            } else if (historyEvent instanceof HistoricProcessInstanceEventEntity) {
                handleProcessEvent((HistoricProcessInstanceEventEntity) historyEvent);
            }
        } catch (Exception e) {
            log.error("Failed to handle history event.", e);
        }
    }

    private void handleActivityEventy(HistoricActivityInstanceEventEntity activityEventEntity) {
        try {
            String activityType = activityEventEntity.getActivityType();
            if (activityType.equals(CamundaConstants.USER_TASK) || activityType.equals(CamundaConstants.SCRIPT_TASK)
                    || activityType.equals(CamundaConstants.SERVICE_TASK)) {
                String processKey = activityEventEntity.getProcessDefinitionKey();
                String processInstanceId = activityEventEntity.getProcessInstanceId();
                String rootProcessInstanceId = activityEventEntity.getRootProcessInstanceId();
                String stepName = activityEventEntity.getActivityName();
                String eventType = activityEventEntity.getEventType();

                if (CamundaConstants.FLOW_STATUS_START.equals(eventType)) {
                    log.info("History activity event[activityType={}, processKey={}, stepName={}, eventType={}]",
                            activityType, processKey, stepName, eventType);
                    historyService.saveStep(processInstanceId, rootProcessInstanceId, processKey, activityType, stepName);
                }
            }
        } catch (Exception e) {
            log.error("Failed to handle history activity event.", e);
        }
    }

    public void handleProcessEvent(HistoricProcessInstanceEventEntity processInstanceEventEntity) {
        try {
            String eventType = processInstanceEventEntity.getEventType();

            if (CamundaConstants.FLOW_STATUS_START.equals(eventType) || CamundaConstants.FLOW_STATUS_END.equals(eventType)) {

                String processKey = processInstanceEventEntity.getProcessDefinitionKey();
                String processInstanceId = processInstanceEventEntity.getProcessInstanceId();
                String rootProcessInstanceId = processInstanceEventEntity.getRootProcessInstanceId();
                log.info("History process event[processKey={}, eventType={}]", processKey, eventType);
                historyService.saveStep(processInstanceId, rootProcessInstanceId, processKey, "Process", eventType);
            }
        } catch (Exception e) {
            log.error("Failed to handle history process event.", e);
        }
    }

    @Override
    public void handleEvents(List<HistoryEvent> list) {
        if (list != null && list.size() > 0) {
            log.info("List handleEvent triggered.{}", list.size());
        }
    }
}