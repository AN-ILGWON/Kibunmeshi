FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

COPY build.gradle settings.gradle gradlew ./
COPY gradle ./gradle

RUN chmod +x gradlew
RUN ./gradlew build --no-daemon

COPY build/libs/kibunmeshi.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]
