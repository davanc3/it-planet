FROM eclipse-temurin:17-jdk
ARG JAR_FILE=target/it.planet-0.0.1-SNAPSHOT.jar
WORKDIR /var/www
COPY ${JAR_FILE} it.planet-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","it.planet-0.0.1-SNAPSHOT.jar"]