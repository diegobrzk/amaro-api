# Amaro - Product API

Demo API made for Amaro to retrieve product vector tags and calculate de similarity between the products.

  - Insert the products JSON.
  - Generate the vector tags JSON.
  - Insert the product with vector tags JSON.
  - See the magic!

# Features

  - Products vector tags generation API.
  - Similarity API.

## Tech

Amaro - Product API uses the following open source technologies to work:

* [JDK 10] - Java JDK 10 to use lombok and others frameworks!
* [Docker] - To easily get ready images to run!
* [elasticsearch] - Easily index and search for products.
* [Spring] - Using the Core, MVC, and others to make coding easy!
* [Spring Data] - Helps with repository and CRUD for elasticsearch!
* [Spring Boot] - To easily configure and boot this application!

## Installation

Amaro - Product API requires [Docker], [Docker Compose] and Java 10 ([JDK 10]) to run.

### Docker

To install Docker you can follow: https://docs.docker.com/compose/
To install Docker Compose you can follow: https://docs.docker.com/compose/install/

### Java 10

To install [JDK 10] you can follow: https://docs.oracle.com/javase/10/install/overview-jdk-10-and-jre-10-installation.htm

## Configuration

Because of an elasticsearch dependency we need to increase the *max_map_count* on linux environments from 65530 to 262144, to do that execute the following configuration:

```sh
$ sudo sysctl -w vm.max_map_count=262144
```

See more about here: https://www.elastic.co/guide/en/elasticsearch/reference/current/_maximum_map_count_check.html

## Build & Run

Once the git repository is clonned you can do as follows:

1) Go into the git repository folder, ex:

```sh
$ cd /home/diegosantos/Git/amaro-api
```

2) Start the docker container:

```sh
$ docker-compose up
```

3) Once the container is up, clean, build and run the application:
```sh
$ ./gradlew clean build bootRun
```

## Test

1) Execute the following post request on terminal:

```sh
$ curl -X POST -H "Content-type: application/json" --data '{"products": [{"id": 8371,"name": "VESTIDO TRICOT CHEVRON","tags": ["balada", "neutro", "delicado", "festa"]}]}' http://localhost:8080/product/tags
```

2) The application should return:

```sh
$ {"products":[{"id":"8371","name":"VESTIDO TRICOT CHEVRON","tags":["balada","neutro","delicado","festa"],"tagsVector":[1,0,0,0,1,0,0,0,0,1,0,0,0,0,0,0,0,1,0,0]}]}
```

You can also import *amaro-api-sample.json* at [Postman] to test the endpoints with sample data.

## Enjoy

Use swagger to see the application documentation:

```sh
$ http://localhost:8080/swagger-ui.html#/
```

See ya.

   [Docker Compose]: <https://docs.docker.com/compose/>
   [JDK 10]: <https://www.oracle.com/technetwork/java/javase/10-relnote-issues-4108729.html>
   [Docker]: <https://www.docker.com/>
   [elasticsearch]: <https://www.elastic.co/products/elasticsearch>
   [swagger]: <https://swagger.io/>
   [Spring]: <http://spring.io/>
   [Spring Boot]: <http://spring.io/projects/spring-boot>
   [Spring Data]: <https://spring.io/projects/spring-data>
   [Postman]: <https://www.getpostman.com/>
   [dill]: <https://dillinger.io/>