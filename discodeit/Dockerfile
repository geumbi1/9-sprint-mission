FROM amazoncorretto:17

WORKDIR /app

COPY . .

RUN chmod +x ./gradlew

RUN ./gradlew clean build -x test

ENV PROJECT_NAME=discodeit
ENV PROJECT_VERSION=0.0.1-SNAPSHOT
ENV JVM_OPTS=""

EXPOSE 80

CMD ["sh", "-c", "java $JVM_OPTS -jar build/libs/${PROJECT_NAME}-${PROJECT_VERSION}.jar"]