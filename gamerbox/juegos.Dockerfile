# ==========================================
# ETAPA 1: Construcción (Builder temporal)
# ==========================================
FROM maven:3.9.6-eclipse-temurin-17-alpine AS builder
WORKDIR /app


COPY . .

# Maven compila solo ms-juegos y las dependencias de su padre
RUN ls -R
RUN mvn clean package -pl ms-juegos -am -DskipTests

# ==========================================
# ETAPA 2: Ejecución (Runtime ligero para Producción)
# ==========================================
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Extraemos SOLAMENTE el .jar compilado de la Etapa 1
COPY --from=builder /app/ms-juegos/target/*.jar app.jar

# Exponemos el puerto
EXPOSE 8082

ENTRYPOINT ["java", "-jar", "app.jar"]