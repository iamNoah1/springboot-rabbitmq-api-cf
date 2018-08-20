# springboot-rabbitmq-api-cf-props
Example Java Spring Boot application which can run locally and on Cloud Foundry. Main focus is to show how to connect to a RabbitMQ using explicit configuration. When running locally Spring will use the [application.properties](src/main/resources/application.properties) which defines the connection for a local running RabbitMQ. When running on Cloud Foundry Spring will use the [application-cloud.](src/main/resources/application-cloud.properties) which uses the VCAP_SERVICES environment variables. The VCAP_SERVICES environment variables are created by Cloud Foundry whenever a service gets bound the an application and contains the credentials for the respective service. Note that the Java Buildpack will flatten the environment variables so that we can access each hierarchy level using a dot. 
Note that we will have to explicitly deactivate the auto-reconfiguration otherwise it would ignore the properties from the property file and connect to the bound service, which by the way only works if there is just one service of a kind. Anyway, additionally we will have to tell Spring to use the "cloud" profile and thus load the application-cloud.properties file instead of the application.properties file. 

## Run locally
* `docker pull rabbitmq:3.7.7-management`
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