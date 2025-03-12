
FROM openjdk:21


WORKDIR /app


COPY target/employment-management-0.0.1-SNAPSHOT.jar /app/employment-management-0.0.1-SNAPSHOT.jar


EXPOSE 8081


ENTRYPOINT ["java", "-jar", "employment-management-0.0.1-SNAPSHOT.jar"]
