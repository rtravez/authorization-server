FROM eclipse-temurin:21-jdk-alpine

RUN addgroup -S appgroup && adduser -S appuser -G appgroup

VOLUME /tmp
EXPOSE 8080
COPY --chown=appuser:appgroup ./target/authorization-server-0.0.1-SNAPSHOT.jar /authorization-server-service.jar
USER appuser
ENTRYPOINT ["java","-jar","/authorization-server-service.jar"]