package github.gvnc.camunda.starter;

import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableProcessApplication
public class CamundaStarterApplication {


  public static void main(String... args) {
    SpringApplication.run(CamundaStarterApplication.class, args);
  }

}