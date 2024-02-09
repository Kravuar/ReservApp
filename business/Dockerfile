FROM eclipse-temurin:21-jdk-alpine AS builder
ARG JAR_FILE=app/target/*.jar
COPY ${JAR_FILE} application.jar
RUN java -Djarmode=layertools -jar application.jar extract

FROM eclipse-temurin:21-jre-alpine

ARG BUSINESS_DB_URL
ARG DB_USERNAME
ARG DB_PASSWORD
ENV SPRING_DATASOURCE_URL=$BUSINESS_DB_URL
ENV SPRING_DATASOURCE_USERNAME=$DB_USERNAME
ENV SPRING_DATASOURCE_PASSWORD=$DB_PASSWORD

COPY --from=builder dependencies/ ./
COPY --from=builder spring-boot-loader ./
COPY --from=builder modules-dependencies ./
COPY --from=builder snapshot-dependencies/ ./
COPY --from=builder application/ ./

EXPOSE 8080
CMD ["java", "org.springframework.boot.loader.JarLauncher"]