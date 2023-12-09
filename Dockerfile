FROM openjdk:17-alpine
WORKDIR /app
COPY build/libs/alexandria-0.0.3.jar /app
COPY build/resources/main/* /app
CMD ["/bin/sh", "-c", "java -jar alexandria-0.0.3.jar --spring.profiles.active='${ACTIVE_PROFILE}'"]