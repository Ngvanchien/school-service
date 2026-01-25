
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY ./pom.xml /app/pom.xml
COPY ./api-gateway/pom.xml /app/api-gateway/pom.xml
COPY ./api-gateway/src /app/api-gateway/src
# Sửa tại đây: build trực tiếp module
RUN mvn -q -f /app/api-gateway/pom.xml -DskipTests package spring-boot:repackage

FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/api-gateway/target/api-gateway-*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]
