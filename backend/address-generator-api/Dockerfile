FROM maven:3.9.6-eclipse-temurin-17 AS build

WORKDIR /app
COPY pom.xml .
RUN mvn -B dependency:go-offline

COPY src ./src
RUN mvn -B package -DskipTests

FROM eclipse-temurin:17-jre-alpine AS runtime

WORKDIR /app
ENV SERVER_PORT=8080

COPY --from=build /app/target/address-generator-api-*.jar /app/address-generator-api.jar

EXPOSE 8080
CMD ["java", "-jar", "/app/address-generator-api.jar"]
