# ==========================================
# ETAPA 1: Construcción (Builder)
# ==========================================
FROM maven:3.9.6-eclipse-temurin-17-alpine AS builder
WORKDIR /app

# 1. Copiamos el pom.xml padre
COPY pom.xml .

# 2. Copiamos SOLAMENTE la carpeta del microservicio que queremos
COPY ms-juegos ms-juegos

# 3. Ejecutamos el truco multi-módulo que íbamos a usar antes
RUN mvn clean package -pl ms-juegos -am -DskipTests

# ==========================================
# ETAPA 2: Ejecución (Runtime ligero)
# ==========================================
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Extraemos el .jar que se generó en la Etapa 1
COPY --from=builder /app/ms-juegos/target/*.jar app.jar

# Exponemos el puerto del microservicio
EXPOSE 8082

ENTRYPOINT ["java", "-jar", "app.jar"]