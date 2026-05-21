FROM maven:3.9-eclipse-temurin-21 AS builder

WORKDIR /build
COPY pom.xml .
COPY src/ src/
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jdk-jammy

WORKDIR /app
COPY --from=builder /build/target/*.jar app.jar

RUN useradd -m springuser
USER springuser

EXPOSE 1895

ENTRYPOINT ["java","-jar","app.jar"]