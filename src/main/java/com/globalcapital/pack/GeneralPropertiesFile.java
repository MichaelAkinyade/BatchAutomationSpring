package com.globalcapital.pack;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:reportBatch.properties")
@PropertySource("classpath:cronValues.properties")
public class GeneralPropertiesFile {
	
}
