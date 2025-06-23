# 베이스 이미지 선택
FROM openjdk:21-jdk

# JAR 복사
COPY build/libs/*.jar app.jar

# 포트 노출
EXPOSE 8080

# 실행 명령
ENTRYPOINT ["java", "-jar", "/app.jar"]
