hub=hub.jdcloud.com/mesh
version=2.3-$(shell git rev-parse --short HEAD)
image=$(hub)/duboo-sample-consumer:$(version)
all: install build push
install:
	mvn clean install -DskipTests
build:
	docker build -t $(image) .
push:
	docker push $(image)
