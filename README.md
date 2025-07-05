# Spring Boot Resource Server Repository

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT) ![GitHub issues](https://img.shields.io/github/issues/khaitan-nitin/java-resource-service)


Welcome to the Resource Server Repository! This repository contains the sample source code for a resource server written in Spring Boot and Java. It is designed to show the sample code for a microservice, making it easy to maintain multiple services.

## Table of Contents
* [Getting Started](#getting-started)
    * [Prerequisites](#prerequisites)
    * [Setup](#setup)
* [Usage](#usage)
* [Swagger](#swagger)
* [Configuration](#configuration)
* [Contributing](#contributing)
* [License](#license)

## Getting Started
Follow these instructions to get the Resource Server up and running in your local development environment.

### Prerequisites
Make sure you have the following installed:

* Java Development Kit (JDK) 17 or later
* Maven
* Git

### Setup
1.  Copy all the 3 repositories to your local machine in the same folder.
2.  Clone the `sample config repository` to your local machine:
    ```
    git clone https://github.com/khaitan-nitin/java-codzs-config.git
    cd java-codzs-config
    ```
3.  Clone the `sample keys repository` to your local machine:
    ```
    git clone https://github.com/khaitan-nitin/java-codzs-resource.git
    cd java-codzs-resource
    ```
4.  Clone the `resource server repository` to your local machine:
    ```
    git clone https://github.com/khaitan-nitin/java-resource-service.git
    cd java-resource-service
    ```
5.  Build the `resource server project` using Maven:
    ```
    mvn clean install
    ```
6.  Run the Config Server:
    ```
    java -Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=7002 -Dspring.profiles.active=local -Dconfig.password=password -Dconfig.server.url=https://local.codzs.com:5002 -Dserver.ssl.keystore=./../java-codzs-resource/local/local.codzs.com.p12 -Dserver.ssl.password=localpassword -jar target/java-resource-service.jar 
    ```
    * `spring.profiles.active`: current active profile as per the environment
    * `config.server.url`: Config server base URL
    * `config.password`: Config server password
    * `server.ssl.keystore`: Path to the ssl key store file
    * `server.ssl.password`: SSL Key store password

    To configure IDE, do the below entry in the environment variable in the IDE (and change the values as per your environment):
    ```
    -Dspring.profiles.active=local
    
    config.password=password; config.server.url=https://local.codzs.com:5002; server.ssl.keystore=./../java-codzs-resource/local/local.codzs.com.p12; server.ssl.password=localpassword
    ```

## Swagger
Swagger is a set of open-source tools built around the OpenAPI Specification that can help you design, build, document and consume REST APIs. The url for the swagger is: https://local.codzs.com:8002/management/swagger-ui/index.html

## Configuration
The Resource Server can be customized to suit various requirements. You can modify the following files and settings:

* `application.yml`: Basic Admin Server settings.
* `application-config-service.yml`: Config service connection details, to pull authorization server configuration details on startup.
* `application-keystore.yml`: Configuration for SSL settings.

For more advanced customization and features, refer to the Spring Cloud Config documentation.

## Contributing
We welcome contributions from the community! If you want to contribute to this project, please follow these steps:

* Fork the repository.
* Create a new branch for your changes.
* Make your changes and commit them with descriptive commit messages.
* Push your changes to your forked repository.
* Create a pull request, detailing the changes you made.

We appreciate your contributions, whether it's bug fixes, new features, or documentation improvements.

## License
This project is licensed under the MIT License - see the LICENSE file for details.