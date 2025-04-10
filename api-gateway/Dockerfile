FROM openjdk:21-jdk-slim AS build
WORKDIR /app
COPY . .
RUN ./gradlew build

FROM openjdk:21-jdk-slim
ARG JAR_FILE=/app/build/libs/*.jar
RUN useradd -m appuser
USER appuser
EXPOSE 8765
COPY --from=build ${JAR_FILE} /app/app.jar
ENTRYPOINT ["java", "-jar", "/app/app.jar"]