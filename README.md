# Read Me First
TBD

# Getting Started

### Requirements
* Install docker and docker compose
* Install JDK-adopt 11
* Install Kotlin

### Installation
* Clone this repository
* Using a terminal run the following command on the repository root folder: 
  * Init mongo db using docker: `docker-compose -f docker-compose.yml up`
  * Start the api application: `gradle bootRun`
* Go to the browser: `http://localhost:8080/swagger-ui/index.html`

#### Application Modes
* Inviter mode: `gradle bootRunInviter`
  * Default port: 8081
  * Database name: wal-agent-inviter
* Invitee mode: `gradle bootRunInvitee`
  * Default port: 8082
  * Database name: wal-agent-invitee

#### Connect to MongoDB
* Host: localhost
* Port: 27017
* URL: mongodb://localhost:27017/
* User: root
* Pass: secret

### Reference Documentation
For further reference, please consider the following sections:

* [Official Gradle documentation](https://docs.gradle.org)
* [Spring Boot Gradle Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.6.6/gradle-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.6.6/gradle-plugin/reference/html/#build-image)
* [Spring Data MongoDB](https://docs.spring.io/spring-boot/docs/2.6.6/reference/htmlsingle/#boot-features-mongodb)
* [Spring Web](https://docs.spring.io/spring-boot/docs/2.6.6/reference/htmlsingle/#boot-features-developing-web-applications)

### Guides
The following guides illustrate how to use some features concretely:

* [Accessing Data with MongoDB](https://spring.io/guides/gs/accessing-data-mongodb/)
* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/bookmarks/)

### Additional Links
These additional references should also help you:

* [Gradle Build Scans – insights for your project's build](https://scans.gradle.com#gradle)

