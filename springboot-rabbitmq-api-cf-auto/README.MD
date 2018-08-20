# springboot-rabbitmq-api-cf-auto
Example Java Spring Boot application which can run locally and on Cloud Foundry. Main focus is to show how to connect to a RabbitMQ using Spring Boot magic also known as convention over configuration. When running locally Spring Boot assumes that there is a RabbitMQ server running on localhost using the default port(5672). If running on Cloud Foundry, the Java Buildpack will recognize that there is RabbitMQ service bound to the application and inject the respective Beans with the configuration to connect to the bound service. 

## Run locally
* `docker pull rabbitmq:3-management`
* `docker run -d -p 15672:15672 -p 5672:5672 rabbitmq:3-management`
* `mvn clean spring-boot:run`

## Run on Cloud Foundry
* `mvn clean install`
* create a rabbitmq service in Cloud Foundry named `rabbitmq`
* `cf push`

## Run integration tests
* `docker pull rabbitmq:3-management`
* `docker run -d -p 15672:15672 -p 5672:5672 rabbitmq:3-management`
* `mvn test` to run explicitly. Running `mvn install` will also run them.

## Endpoints

### Send Message

* `curl -H "Accept: application/json" -H "Content-type: application/json" -X POST -d '{"message": "message for queue"}' http://<baseurl>/message`. Have a look on stdout, the message should appear there.