# Build stage
FROM maven:3.9.8-eclipse-temurin-21 AS build

# Đặt thư mục làm việc bên trong container
WORKDIR /app

# Sao chép file pom.xml và thư mục src vào container
COPY pom.xml .
COPY src/ ./src/

# Build ứng dụng Spring Boot
RUN mvn clean package -DskipTests

# Package stage
FROM openjdk:21-jdk-slim

# Đặt thư mục làm việc trong container
WORKDIR /app

# Sao chép file JAR đã build từ build stage vào container
COPY --from=build /app/target/ims-0.0.1-SNAPSHOT.jar app.jar

# Mở cổng 8080 để có thể truy cập ứng dụng
EXPOSE 8080

# Định nghĩa lệnh để chạy ứng dụng
ENTRYPOINT ["java", "-jar", "app.jar"]
