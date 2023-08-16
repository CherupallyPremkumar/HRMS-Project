FROM openjdk:11
EXPOSE 8080
ADD target/spring-boot-HRMS.jar spring-boot-HRMS.jar
ENTRYPOINT ["java","-jar","/spring-boot-HRMS.jar","--spring.config.location=classpath:/application-docker.properties"]