FROM openjdk:8u171-jdk-alpine
RUN apk add --no-cache tzdata && cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && echo "Asia/Shanghai" > /etc/timezone && apk del tzdata
WORKDIR /app
VOLUME /app/upload
VOLUME /data
COPY ./target/*.jar /app/app.jar
ENV SPRING_PROFILES_ACTIVE=test,swagger
EXPOSE 8080
HEALTHCHECK CMD curl --fail http://localhost:8080 || exit 1
CMD java -jar /app/app.jar --spring.profiles.active=${SPRING_PROFILES_ACTIVE}
