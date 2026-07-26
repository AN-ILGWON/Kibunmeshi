# ── Build Stage ──────────────────────────────────────────────
FROM eclipse-temurin:17-jdk-alpine AS builder

WORKDIR /app

# 의존성 캐시를 위해 gradle 설정 먼저 복사
COPY gradlew ./
COPY gradle ./gradle
COPY build.gradle settings.gradle ./

RUN chmod +x gradlew

# 의존성 다운로드 (소스 없이 미리 캐싱)
RUN ./gradlew dependencies --no-daemon || true

# 소스 코드 전체 복사
COPY src ./src

# 테스트 스킵하고 JAR 빌드
RUN ./gradlew bootJar --no-daemon -x test

# ── Runtime Stage ─────────────────────────────────────────────
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

COPY --from=builder /app/build/libs/kibunmeshi.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]
