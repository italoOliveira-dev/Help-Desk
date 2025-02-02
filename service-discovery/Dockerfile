FROM openjdk:21-jdk-slim AS build
WORKDIR /app
COPY . .
RUN ./gradlew build

FROM openjdk:21-jdk-slim
ARG JAR_FILE=/app/build/libs/*.jar
RUN useradd -m appuser
USER appuser
EXPOSE 8761
COPY --from=build ${JAR_FILE} /app/app.jar
ENTRYPOINT ["java", "-jar", "/app/app.jar"]

# docker container run -d -p 8761:8761 --name helpdesk-service-discovery -e CONFIG_SERVER_URI=http://localhost:8888 italooliveiraxd/helpdesk-service-discovery:latest