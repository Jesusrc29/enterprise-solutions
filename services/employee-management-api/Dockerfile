FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /workspace

COPY pom.xml pom.xml
COPY services/employee-management-api/pom.xml services/employee-management-api/pom.xml
COPY services/employee-management-api/src services/employee-management-api/src
COPY lombok.config lombok.config

RUN mvn -pl services/employee-management-api -am clean package -DskipTests

FROM eclipse-temurin:21-jre
WORKDIR /app

COPY --from=build /workspace/services/employee-management-api/target/employee-management-api-0.1.0-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/app.jar"]
