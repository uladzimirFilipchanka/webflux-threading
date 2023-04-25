FROM eclipse-temurin:17

COPY build/libs/webflux-threading-*.jar /app/webflux-threading.jar

ENTRYPOINT java -jar /app/webflux-threading.jar