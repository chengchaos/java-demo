FROM java:8
MAINTAINER Cheng, Chao <chengchaos@outlook.com>
VOLUME /tmp
EXPOSE 8080
ADD target/*.jar app.jar
ENTRYPOINT [ "java",
    "-Djava.security.egd=file:/dev/./urandom",
    "-jar",
    "/app.jar" ]