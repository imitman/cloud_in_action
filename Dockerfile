FROM openjdk:11-slim
COPY ./build/libs/cloud_in_action-1.0-SNAPSHOT.war /usr/app/
WORKDIR /usr/app
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "cloud_in_action-1.0-SNAPSHOT.war"]