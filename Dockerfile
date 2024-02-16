FROM eclipse-temurin:21-jdk-alpine AS builder
ARG MODULE_NAME
ARG BUILD_MODULE_RELATIVE_PATH
ENV MODULE_PATH=modules/$MODULE_NAME/$BUILD_MODULE_RELATIVE_PATH

RUN echo "MODULE_NAME: $MODULE_NAME"
RUN echo "MODULE_PATH: $MODULE_PATH"

WORKDIR /app
COPY commons ./commons
COPY pom.xml .
COPY modules/pom.xml ./modules/
COPY modules/${MODULE_NAME} ./modules/${MODULE_NAME}

RUN --mount=type=cache,target=/root/.m2 ./$MODULE_PATH/mvnw -f $MODULE_PATH/pom.xml clean package -Dmaven.test.skip
RUN mv $MODULE_PATH/target/*.jar /app/app.jar
RUN java -Djarmode=layertools -jar /app/app.jar extract

FROM eclipse-temurin:21-jre-alpine
COPY --from=builder /app/dependencies/ /app/spring-boot-loader/ /app/modules-dependencies/ /app/snapshot-dependencies/ /app/application/ ./
CMD ["java", "org.springframework.boot.loader.launch.JarLauncher"]
