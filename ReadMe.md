# Coding challenge
## So the challenge is, please provide us with a function, where we can fill in the current time and a provider and get returned the expected delivery date.

The configuration for different carriers is free to choose.
For the given test data, we assume cut-off-time: 12 and delivery days: 2


{"actual datetime", "expected delivery date"}
{"2020-02-14T09:21:38+00:00", "2020-02-18"},
{"2020-03-30T09:21:38+00:00", "2020-04-01"},
{"2020-03-30T10:21:38+00:00", "2020-04-01"},
{"2020-03-30T11:21:38+00:00", "2020-04-02"},
{"2020-04-01T11:21:38+00:00", "2020-04-04"},
{"2020-04-03T09:21:38+00:00", "2020-04-07"},
{"2020-04-03T11:21:38+00:00", "2020-04-08"},
{"2020-01-01T11:21:38+00:00", "2020-01-04"}, // new year
{"2020-04-10T11:21:38+00:00", "2020-04-16"}, // good friday
{"2020-04-13T11:21:38+00:00", "2020-04-16"}, // easter monday
{"2020-05-01T11:21:38+00:00", "2020-05-06"}, // labour day

## Install and Run
### Requirements
1. Docker/Docker Compose
2. maven
3. JDK 1.8
4. Git client


### How to Run with Docker and with Maven?
Current setup provides you two ways to run the application

**Development mode**
- Open the project in your favourite IDE (Ex. IntelliJ IDEA).
```
- mvn clean install

- mvn spring-boot:run

Application resources available:
 - Swagger API Docs:  [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
 
Using swagger try it now, API can be triggered. 
 - GET /api/v1/oderservice/estimate
 - Request > change the default start date and trigger
 - Request Object : 
{
  "duration": 0,
  "loanAmount": 0,
  "nominalRate": 0,
  "startDate": "2020-12-01T00:44:026Z"
}

curl -X GET "http://localhost:8080/api/v1/oderservice/estimate?localDateTime=2020-05-01T11%3A21%3A38%2B00%3A00&provider=dhl" -H "accept: application/json"

**Production mode**

* mvn clean install
* docker build -t orderservice .
* docker run -p 8080:8080 -it orderservice

Application resources available:
 - Swagger API Docs:  [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

-CURL Command

* curl -X GET "http://localhost:8080/api/v1/oderservice/estimate?localDateTime=2020-05-01T11%3A21%3A38%2B00%3A00&provider=dhl" -H "accept: application/json"

-OUTPUT FILE

* curl -X GET "http://localhost:8080/api/v1/oderservice/estimate?localDateTime=2020-05-01T11%3A21%3A38%2B00%3A00&provider=dhl" -H "accept: application/json"n --output output.json

-Windows Docker Tool Box

* curl -X GET "http://192.168.99.100:8080/api/v1/oderservice/estimate?localDateTime=2020-05-01T11%3A21%3A38%2B00%3A00&provider=dhl" -H "accept: application/json"n --output output.json


-verify output.json in current directory



## Tech stack & Open-source libraries

Server - Backend

* 	[JDK 8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html) - Java Platform, Standard Edition Development Kit
* 	[Spring Boot](https://spring.io/projects/spring-boot) - Framework to ease the bootstrapping and development of new Spring Applications
* 	[Junit 5](https://junit.org/junit5/docs/current/user-guide/) - Distributed version control system
* 	[Maven](https://maven.apache.org/) - Dependency Management
* 	[Git](https://github.com/) - Version Controller 
* 	[Docker](https://www.docker.com/) - Docker is a set of platform as a service products that use OS-level virtualization to deliver software in packages called containers. Containers are isolated from one another and bundle their own software, libraries and configuration files; they can communicate with each other through well-defined channels

External Tools & Services

* 	[Postman](https://www.getpostman.com/) - API Development Environment (Testing Docmentation)