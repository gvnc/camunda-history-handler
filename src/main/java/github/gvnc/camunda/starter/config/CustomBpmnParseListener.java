package github.gvnc.camunda.starter.config;

import github.gvnc.camunda.starter.executionListener.SequenceFlowExecutionListener;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.impl.bpmn.parser.AbstractBpmnParseListener;
import org.camunda.bpm.engine.impl.pvm.process.ScopeImpl;
import org.camunda.bpm.engine.impl.pvm.process.TransitionImpl;
import org.camunda.bpm.engine.impl.util.xml.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CustomBpmnParseListener extends AbstractBpmnParseListener {

    @Autowired
    private SequenceFlowExecutionListener sequenceFlowExecutionListener;

    @Override
    public void parseSequenceFlow(Element sequenceFlowElement, ScopeImpl scopeElement, TransitionImpl transition) {
        try {
            transition.addListener("take", sequenceFlowExecutionListener);
        } catch (Exception e) {
            log.error("Failed parseSequenceFlow.", e);
        }
    }
}
