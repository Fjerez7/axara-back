FROM amazoncorretto:22-alpine-jdk

WORKDIR /app

# Copiar el archivo JAR de la aplicación al contenedor
COPY target/backend-0.0.1-SNAPSHOT.jar app.jar
RUN mkdir -p /app/product-images
COPY src/main/resources/product-images/* /app/product-images/

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar","-Dspring.profiles.active=docker"]