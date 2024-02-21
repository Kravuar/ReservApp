FROM eclipse-temurin:21-jdk-alpine AS builder

ARG MODULE_PATH
ARG BUILD_MODULE_PATH

ENV MODULE=modules/$MODULE_PATH
ENV BUILD_MODULE_PATH=modules/$BUILD_MODULE_PATH

WORKDIR /app
COPY pom.xml .
COPY commons ./commons
COPY modules/pom.xml ./modules/
COPY modules/${MODULE_PATH} ./modules/${MODULE_PATH}

RUN mv ./$BUILD_MODULE_PATH/target/*.jar ./app.jar
RUN java -Djarmode=layertools -jar ./app.jar extract

FROM eclipse-temurin:21-jre-alpine
COPY --from=builder /app/dependencies/ /app/spring-boot-loader/ /app/modules-dependencies/ /app/snapshot-dependencies/ /app/application/ ./
CMD ["java", "org.springframework.boot.loader.launch.JarLauncher"]
