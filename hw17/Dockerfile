FROM openjdk:17.0.2-jdk
WORKDIR /home
COPY build/libs/hw17-0.1.0.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]