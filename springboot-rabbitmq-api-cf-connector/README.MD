# springboot-rabbitmq-api-cf-auto
Example Java Spring Boot application which can run locally and on Cloud Foundry. Main focus is to show how to connect to a RabbitMQ using Spring Boot Cloud Connectors. Therefore we define a Configuration Bean ([RabbitMQConfig](/src/main/java/com/anynines/config/RabbitMQConfig.java)) where we can set some connection properties. Spring Boot will take that the ServiceConnector Bean gets injected to the right place and connect to the RabbitMQ service with the name that is defined via environment variable (RABBITMQ_SERVICE_NAME).

## Run locally
* `docker pull rabbitmq:3.7.7-management`
* `docker run -d -p 15672:15672 -p 5672:5672 rabbitmq:3-management`
* `mvn clean spring-boot:run`

## Run on Cloud Foundry
* `mvn clean install`
* create a rabbitmq service in Cloud Foundry named `rabbitmq`
* `cf push`

## Endpoints

### Send Message

* `curl -H "Accept: application/json" -H "Content-type: application/json" -X POST -d '{"message": "blablablaaaa"}' http://localhost:8080/messagecurl -H "Accept: application/json" -H "Content-type: application/json" -X POST -d '{"message": "message for queue"}' http://localhost:8080/message`. Have a look on stdout, the message should appear there.