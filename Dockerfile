#FROM maven:amazoncorretto:17 as builder
#WORKDIR /opt/app
#COPY mvnw pom.xml ./
#COPY ./src ./src
#RUN mvn clean install -DskipTests
#
#FROM corretto-17.0.14

# Этап сборки
FROM maven:3.8.7-eclipse-temurin-17 AS build
WORKDIR /app
# Копируем файлы исходного кода и pom.xml
COPY src ./src
COPY mvnw pom.xml ./
COPY init.sql /docker-entrypoint-initdb.d/
# Собираем приложение
RUN mvn clean install -DskipTests

# Этап запуска
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
# Копируем собранный JAR из этапа сборки
COPY --from=build /app/target/*.jar app.jar
# Запускаем приложение
ENTRYPOINT ["java", "-jar", "app.jar"]