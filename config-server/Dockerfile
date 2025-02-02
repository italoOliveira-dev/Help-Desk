FROM openjdk:21-jdk-slim AS build
WORKDIR /app
COPY . .
RUN ./gradlew build

FROM openjdk:21-jdk-slim
ARG JAR_FILE=/app/build/libs/*.jar
RUN useradd -m appuser
USER appuser
EXPOSE 8888
COPY --from=build ${JAR_FILE} /app/app.jar
ENTRYPOINT ["java", "-jar", "/app/app.jar"]

# docker container run -d -p 8888:8888 -e CONFIG_SERVER_URI='SEU-REPOSITORIO' -e CONFIG_SERVER_USERNAME='SEU-USUARIO' -e CONFIG_SERVER_PASSWORD='SUA-SENHA' italooliveriaxd/helpdesk-config-server:latest