FROM gradle:8.6-jdk17 AS build

WORKDIR /app

COPY path/to/build.gradle path/to/settings.gradle ./

COPY src ./src

RUN gradle clean build -x test

FROM openjdk:17-jdk-oracle

WORKDIR /app

COPY --from=build /app/build/libs/*.jar elevator-web-service-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java", "-jar", "elevator-web-service-0.0.1-SNAPSHOT.jar"]