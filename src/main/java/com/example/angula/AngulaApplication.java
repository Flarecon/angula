package com.example.angula;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.example.reactor.components.Sweet;

@ComponentScan(basePackages = {"com.example.reactor,com.example.angula"}, 
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = Sweet.class))
@EnableCaching
@EnableScheduling
@SpringBootApplication
public class AngulaApplication {

	@Value("${ready.message}")
	private String readyMessage;

	public static void main(String[] args) {
		var context = SpringApplication.run(AngulaApplication.class, args);
		System.out.println(context.getBean(AngulaApplication.class).readyMessage);
	}
}
