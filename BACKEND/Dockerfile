FROM openjdk:11

ARG JAR_FILE=build/libs/challympic-2.0.0.jar

COPY ${JAR_FILE} app.jar

EXPOSE 8880

ENTRYPOINT ["java", "-jar", "/app.jar"]
