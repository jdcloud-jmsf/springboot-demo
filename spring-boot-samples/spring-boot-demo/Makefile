hub=hub.jdcloud.com/mesh
version=2.8.2-$(shell git rev-parse --short HEAD)
image=$(hub)/spring-boot-demo:$(version)
all: install build push
install:
	mvn clean install -DskipTests
build:
	docker build -t $(image) .
push:
	docker push $(image)
