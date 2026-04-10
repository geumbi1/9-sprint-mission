# ===== 1단계: build =====
FROM gradle:8-jdk17 AS build

WORKDIR /app

# 1. 의존성 파일 먼저 복사 (캐시 활용 핵심)
COPY build.gradle settings.gradle gradlew ./
COPY gradle ./gradle

# 2. 의존성 미리 다운로드 (캐시 레이어 생성)
RUN chmod +x ./gradlew && ./gradlew dependencies --no-daemon

# 3. 소스 코드 복사 (여기서 변경이 자주 발생)
COPY src ./src

# 4. 빌드 실행
RUN ./gradlew clean build -x test --no-daemon

# ===== 2단계: runtime =====
FROM amazoncorretto:17

WORKDIR /app

COPY --from=build /app/build/libs/*.jar app.jar

ENV PROJECT_NAME=discodeit
ENV PROJECT_VERSION=1.2-M8
ENV JVM_OPTS=""

EXPOSE 80

CMD ["sh", "-c", "java $JVM_OPTS -jar app.jar"]