package github.gvnc.camunda.starter.repository;

import github.gvnc.camunda.starter.repository.model.FlowStepHistory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlowStepHistoryRepository extends CrudRepository<FlowStepHistory, Long> {

}
