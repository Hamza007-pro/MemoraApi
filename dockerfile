FROM adoptopenjdk/openjdk17:alpine-jre

WORKDIR /app

COPY . .

RUN ./gradlew build

EXPOSE 8080

CMD ["java", "-jar", "build/libs/your-backend.jar"]
