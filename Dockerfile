# the first stage of our build will extract the layers
FROM adoptopenjdk:11-jre-hotspot as builder
WORKDIR application
ARG JAR_FILE=./build/libs/cloud_in_action-1.0-SNAPSHOT.jar
COPY ${JAR_FILE} cloud_in_action-1.0-SNAPSHOT.jar
RUN java -Djarmode=layertools -jar cloud_in_action-1.0-SNAPSHOT.jar extract

# the second stage of our build will copy the extracted layers
FROM adoptopenjdk:11-jre-hotspot
WORKDIR application
COPY --from=builder application/dependencies/ ./
COPY --from=builder application/spring-boot-loader/ ./
COPY --from=builder application/snapshot-dependencies/ ./
COPY --from=builder application/application/ ./
ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]