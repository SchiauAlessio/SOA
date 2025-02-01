# Build the JAR file with Maven
FROM maven:3.8.4-openjdk-17 AS build
RUN mkdir -p /web_server
WORKDIR /web_server
COPY pom.xml /web_server
COPY src /web_server/src
RUN mvn -f pom.xml clean package

# Use the JAR file to compile the project
FROM openjdk:17-jdk-slim
RUN mkdir -p /web_server
WORKDIR /web_server
COPY --from=build web_server/target/web_server-0.0.1-SNAPSHOT.jar web_server-0.0.1-SNAPSHOT.jar
EXPOSE 8082
ENTRYPOINT ["java","-jar","web_server-0.0.1-SNAPSHOT.jar"]