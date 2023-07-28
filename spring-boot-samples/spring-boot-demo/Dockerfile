FROM hub.jdcloud.com/mesh/jdk8:20220707
MAINTAINER zhangdalei<zhangdalei1@jd.com>

ENV APP_NAME=demo APP_GROUP=v1 PRIVIDER_NAME="-"

WORKDIR /app
COPY target/*.jar /app/app.jar
EXPOSE 80
ENTRYPOINT ["java","-Dserver.port=80","-jar","/app/app.jar"]
