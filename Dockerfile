FROM java:8-jre-alpine
MAINTAINER Threeq <threeq@foxmail.com>

VOLUME /tmp
ADD app.jar /app.jar
RUN sh -c 'touch /app.jar'

EXPOSE 8080

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]