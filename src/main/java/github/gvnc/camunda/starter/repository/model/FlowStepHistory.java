package github.gvnc.camunda.starter.repository.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class FlowStepHistory {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter
    private Long id;

    @Getter @Setter
    private String rootProcessInstanceId;

    @Getter @Setter
    private String processInstanceId;

    @Getter @Setter
    private String processName;

    @Getter @Setter
    private String stepType;

    @Getter @Setter
    private String stepName;
}
