FROM openjdk:latest

VOLUME /tmp

ADD ./target/projeto-api-1.0.0.jar projeto-api.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/projeto-api.jar"]