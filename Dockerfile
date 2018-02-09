FROM openjdk:8
ADD /target/jbot-0.0.1-SNAPSHOT-spring-boot.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]