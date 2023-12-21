= Spring Boot Books Example app

This is an Api service written for training purpose in JAVA/Maven uses Spring boot framework.

== How to build

JAVA 17 Amazon Corretto 17.0.6

== CONFIG file : application.yml
- Default port 8080

== SWAGER:
http://localhost:8080/swagger-ui/index.html

== H2_CONSOLE:
http://localhost:8080/h2-console
JDBC url jdbc:h2:mem:testdb
Username and password in application.yml

== CODE FORMATTER:
Info: https://github.com/diffplug/spotless/tree/main/plugin-maven
This code formatter runs on each Compile phase and apply automatically.

Manual run of code formatter:
spotless:check
spotless:apply

== RUN Appplication in Maven
spring-boot:run

== RUN Unit Tests
-Dtest=SpringBootBooksApplicationTests test

== Run Functional Tests in Postman:
test/Postman/Collection
test/Postman/Environments

