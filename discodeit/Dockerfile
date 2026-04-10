# ===== 1단계: build =====
FROM gradle:8-jdk17 AS build
WORKDIR /app

# 1. 의존성 파일 복사
COPY build.gradle settings.gradle gradlew ./
COPY gradle ./gradle

# 2. 의존성 다운로드
RUN chmod +x ./gradlew && ./gradlew dependencies --no-daemon

# 3. 소스 코드 복사
COPY src ./src

# 4. 빌드 (plain jar 생성을 방지하고 최적화)
RUN ./gradlew clean build -x test --no-daemon

# ===== 2단계: runtime (local-slim 환경) =====
FROM amazoncorretto:17-alpine-jdk
# 💡 alpine 버전을 쓰면 이미지 크기가 훨씬 줄어듭니다 (심화 요구사항 대응)

WORKDIR /app

# plain.jar를 제외하고 실제 실행 가능한 jar만 복사
COPY --from=build /app/build/libs/*-SNAPSHOT.jar app.jar || COPY --from=build /app/build/libs/*[0-9].jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]