FROM openjdk:8-jdk-alpine
ARG JAR_FILE=target/physical-features-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} physical-features-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","physical-features-0.0.1-SNAPSHOT.jar"]