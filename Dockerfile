FROM eclipse-temurin:21-jdk-alpine AS builder

ARG MODULE_NAME
ARG BUILD_MODULE_PATH

ENV MODULE=modules/$MODULE_NAME
ENV BUILD_MODULE_PATH=modules/$BUILD_MODULE_PATH

WORKDIR /app
COPY pom.xml .
COPY commons ./commons
COPY modules/pom.xml ./modules/
COPY modules/${MODULE_NAME} ./modules/${MODULE_NAME}

RUN mv ./$BUILD_MODULE_PATH/target/*.jar ./app.jar
RUN java -Djarmode=layertools -jar ./app.jar extract

FROM eclipse-temurin:21-jre-alpine
RUN apk --no-cache add curl
COPY --from=builder /app/dependencies/ /app/spring-boot-loader/ /app/modules-dependencies/ /app/snapshot-dependencies/ /app/application/ ./
CMD ["java", "org.springframework.boot.loader.launch.JarLauncher"]
