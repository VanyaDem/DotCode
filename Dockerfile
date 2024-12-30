FROM openjdk:17-alpine
WORKDIR /app
COPY target/DotCode-0.0.1-SNAPSHOT.jar dotcode.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "dotcode.jar"]
