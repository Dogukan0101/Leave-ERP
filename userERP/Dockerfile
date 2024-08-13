FROM openjdk:21-jdk

RUN groupadd app && useradd -g app -s /bin/sh -m app

USER app

COPY target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]
