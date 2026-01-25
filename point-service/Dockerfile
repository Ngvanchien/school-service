
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY ./pom.xml /app/pom.xml
COPY ./point-service/pom.xml /app/point-service/pom.xml
COPY ./point-service/src /app/point-service/src
RUN mvn -q -f /app/point-service/pom.xml -DskipTests package spring-boot:repackage

FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/point-service/target/point-service-*.jar app.jar
EXPOSE 8083
ENTRYPOINT ["java","-jar","/app/app.jar"]
