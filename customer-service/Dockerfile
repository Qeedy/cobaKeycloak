# Dockerfile (di dalam ./customer-service)
FROM eclipse-temurin:17-jdk
WORKDIR /app

# Salin hasil build lokal ke image
COPY target/customer-service-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]