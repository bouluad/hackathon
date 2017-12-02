FROM openjdk:8-jre-alpine
FROM maven:3-jdk-8
ENV SPRING_OUTPUT_ANSI_ENABLED=ALWAYS \
    JHIPSTER_SLEEP=0 \
    JAVA_OPTS=""
RUN mkdir -p /app
ADD . /app
WORKDIR /app
RUN mvn package

EXPOSE 8082 5702/udp

ENTRYPOINT ["java","-jar","/app/target/catalogue-0.0.1-SNAPSHOT.war"]
