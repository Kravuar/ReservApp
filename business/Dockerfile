# Stage 1: Maven build with 'prod' profile
FROM maven:3.8.6-openjdk-21 AS build
WORKDIR /app
# Copy the source code
COPY ./app /app
# Build the service application with the 'prod' profile
RUN mvn -Pprod clean package -DskipTests
# Extract the built application from {target/*name*.jar} into target/dependency of the container
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../target/*.jar)

# Stage 2: Final image
FROM eclipse-temurin:21-jre-alpine
# Expose the port the app runs on
EXPOSE 8080
# Environment variables for service database configuration
ARG SPRING_DATASOURCE_URL
ARG SPRING_DATASOURCE_USERNAME
ARG SPRING_DATASOURCE_PASSWORD
# Setting environment variables from build arguments
ENV SPRING_DATASOURCE_URL=$SPRING_DATASOURCE_URL
ENV SPRING_DATASOURCE_USERNAME=$SPRING_DATASOURCE_USERNAME
ENV SPRING_DATASOURCE_PASSWORD=$SPRING_DATASOURCE_PASSWORD
# Volume to be used for storing temporary files
VOLUME /tmp
# Copy dependency files from the build stage
COPY --from=build /app/target/dependency/BOOT-INF/lib /app/lib
COPY --from=build /app/target/dependency/META-INF /app/META-INF
COPY --from=build /app/target/dependency/BOOT-INF/classes /app
# Set the entry point to run the application
ENTRYPOINT ["java", "-cp", "app:app/lib/*", "net.kravuar.business.Application"]
