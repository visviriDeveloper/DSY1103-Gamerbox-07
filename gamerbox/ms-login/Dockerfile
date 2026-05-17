FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY target/ms-login-*.jar app.jar
EXPOSE 8088
ENTRYPOINT ["java", "-jar", "app.jar"]