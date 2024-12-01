package org.planner;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@Slf4j
public class WeddingPlannerApplication {

	public static void main(String[] args) {
		SpringApplication.run(WeddingPlannerApplication.class, args);
		log.info(" -- > Wedding Planner Application Running ... ");
	}

}
