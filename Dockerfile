FROM openjdk:8
ADD ["classes", "maven-archiver", "maven-status", "application.properties", "BatchAutomationLoginSpring-0.0.1-SNAPSHOT.jar"]
ADD ["BatchAutomationLoginSpring-0.0.1-SNAPSHOT.jar.original, cronValues.properties", "h2DatasourceConfig.properties", "quartz.properties", "reportBatch.properties"] 
EXPOSE 8023
ENTRYPOINT["java", "-jar", "BatchAutomationLoginSpring-0.0.1-SNAPSHOT.jar"]