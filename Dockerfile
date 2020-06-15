FROM gradle:6.3.0-jdk8 AS builder
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
USER root
RUN gradle build

FROM  openjdk:8-jdk-alpine

COPY --from=builder build/libs/*.jar bpcrs.jar
EXPOSE 5000
ENTRYPOINT ["java","-jar","/bpcrs.jar"]