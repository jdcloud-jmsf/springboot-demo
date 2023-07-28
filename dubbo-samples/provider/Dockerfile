FROM openjdk:8
MAINTAINER zhangdalei<zhangdalei1@jd.com>
RUN apt-get update && apt-get install -y iputils-ping  net-tools

WORKDIR /app
COPY target/*.jar /app/app.jar
EXPOSE 80 20880
CMD java ${JAVA_TOOL_OPTIONS} -Dserver.port=80 -jar /app/app.jar