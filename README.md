# Two spring boot app communicating through docker.

## application #1: "docker":
    has single GET request returns stirng: 

    Request URL: http://localhost:8081/
    Response: "hello from app called docker"

## application #2: "spring-boot-docker":
    has two GET requests "/" and, "/get": 

    Request URL: http://localhost:8080/
    Request: "hello"

    Request URL: http://localhost:8080/get
    Response: "hello from app called docker"

    Second GET request calls the URL of the first app
    like so:  (http://docker:8081/) which it returns the response of the first app. 




