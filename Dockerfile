FROM gradle:4.7.0-jdk8-alpine AS builder
COPY --chown=gradle:gradle . /
USER root
RUN gradle build --no-daemon

FROM  openjdk:8-jdk-alpine

COPY --from=builder build/libs/*.jar bpcrs.jar
EXPOSE 5000
ENTRYPOINT ["java","-jar","/bpcrs.jar"]