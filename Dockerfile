FROM gradle:4.7.0-jdk8-alpine AS build
COPY --chown=gradle:gradle . /
USER root
RUN gradle build --no-daemon

FROM  openjdk:8-jdk-alpine

COPY --from=build build/libs/bpcrs-0.0.1-SNAPSHOT.jar bpcrs-0.0.1-SNAPSHOT.jar
EXPOSE 5000
ENTRYPOINT ["java","-jar","/bpcrs-0.0.1-SNAPSHOT.jar"]