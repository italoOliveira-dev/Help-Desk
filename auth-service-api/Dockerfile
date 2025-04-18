FROM eclipse-temurin:21-jdk-jammy
ARG JAR_FILE=./build/libs/*.jar
RUN apt-get update && \
    apt-get install -y curl && \
    rm -rf /var/lib/apt/lists/* && \
    useradd -m appuser
USER appuser
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]