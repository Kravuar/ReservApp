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


ARG MAIL_HOST
ARG MAIL_PORT
ARG MAIL_USERNAME
ARG MAIL_PASSWORD
ARG MAIL_SMPT_AUTH=true
ARG MAIL_START_TLS=true
ENV SPRING_MAIL_HOST=$MAIL_HOST
ENV SPRING_MAIL_PORT=$MAIL_PORT
ENV SPRING_MAIL_USERNAME=$MAIL_USERNAME
ENV SPRING_MAIL_PASSWORD=$MAIL_PASSWORD
ENV SPRING_MAIL_PROPERTIES_MAIL_SMTP_AUTH=$MAIL_SMPT_AUTH
ENV SPRING_MAIL_PROPERTIES_MAIL_STARTTLS=$MAIL_START_TLS

COPY --from=builder dependencies/ ./
COPY --from=builder spring-boot-loader ./
COPY --from=builder modules-dependencies ./
COPY --from=builder snapshot-dependencies/ ./
COPY --from=builder application/ ./

EXPOSE 8080
CMD ["java", "org.springframework.boot.loader.JarLauncher"]