FROM openjdk:21-jdk
ARG JAR_FILE=./build/libs/*.jar
EXPOSE 8888
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]

# docker container run -d -p 8888:8888 -e CONFIG_SERVER_URI='SEU-REPOSITORIO' -e CONFIG_SERVER_USERNAME='SEU-USUARIO' -e CONFIG_SERVER_PASSWORD='SUA-SENHA' italooliveriaxd/helpdesk-config-server:latest