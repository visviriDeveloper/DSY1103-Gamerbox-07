# Etapa 1: Compilación
FROM maven:3.8.8-eclipse-temurin-17 AS build
WORKDIR /app

# Copiar el POM padre de la raíz de gamerbox
COPY pom.xml .

# Copiar el submódulo ms-juegos completo
COPY ms-juegos ./ms-juegos

# Moverse a la carpeta del microservicio y compilarlo junto con el padre
WORKDIR /app/ms-juegos
RUN mvn clean package -DskipTests

# Etapa 2: Imagen de ejecución
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /app/ms-juegos/target/ms-juegos-*.jar app.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "app.jar"]
