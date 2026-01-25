
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY ./pom.xml /app/pom.xml
COPY ./auth-service/pom.xml /app/auth-service/pom.xml
COPY ./auth-service/src /app/auth-service/src
RUN mvn -q -f /app/auth-service/pom.xml -DskipTests package spring-boot:repackage

FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/auth-service/target/auth-service-*.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","/app/app.jar"]

