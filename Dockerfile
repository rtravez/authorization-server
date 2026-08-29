FROM eclipse-temurin:21-jdk-alpine

RUN addgroup -S appgroup && adduser -S appuser -G appgroup

VOLUME /tmp
EXPOSE 8080
COPY --chown=appuser:appgroup ./target/authorization-server-0.0.1-SNAPSHOT.jar /auth-service.jar
USER appuser
ENTRYPOINT ["java","-jar","/auth-service.jar"]