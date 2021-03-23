FROM openjdk:15-jdk-alpine
LABEL author='skazey@gmail.com'
LABEL version='0.0.1-SNAPSHOT'
COPY target/webinfo-0.0.1-SNAPSHOT.jar /app/app.jar
WORKDIR /app
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
