
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY ./pom.xml /app/pom.xml
COPY ./eureka-server/pom.xml /app/eureka-server/pom.xml
COPY ./eureka-server/src /app/eureka-server/src
RUN mvn -q -f /app/eureka-server/pom.xml -DskipTests package spring-boot:repackage

FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/eureka-server/target/eureka-server-*.jar app.jar
EXPOSE 8761
ENTRYPOINT ["java","-jar","/app/app.jar"]
