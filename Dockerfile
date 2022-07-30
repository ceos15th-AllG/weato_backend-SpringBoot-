FROM openjdk:17-jdk

LABEL maintainer="jihoon806@gmail.com"

VOLUME /tmp

EXPOSE 8080

ARG JAR_FILE=build/libs/weato-0.0.1-SNAPSHOT.jar

ADD ${JAR_FILE} weato.jar

ENTRYPOINT ["java", "-jar", "/weato.jar"]