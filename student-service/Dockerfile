
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY ./pom.xml /app/pom.xml
COPY ./student-service/pom.xml /app/student-service/pom.xml
COPY ./student-service/src /app/student-service/src
RUN mvn -q -f /app/student-service/pom.xml -DskipTests package spring-boot:repackage

FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/student-service/target/student-service-*.jar app.jar
EXPOSE 8082
ENTRYPOINT ["java","-jar","/app/app.jar"]
