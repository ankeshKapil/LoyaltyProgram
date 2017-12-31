package com.cosmos.LoyaltyProgram.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@PropertySource("classpath:sms.properties")
public class PropertyConfig {

	@Bean
	public static PropertySourcesPlaceholderConfigurer properties(){
		PropertySourcesPlaceholderConfigurer configurer=new PropertySourcesPlaceholderConfigurer();
		return configurer;
				
	}
}
