This is a simple rest-api written with the [Spring Boot][3] Java framework. It returns cities/locations within a radius
specified by latitude and longitude in json-format.

Example, find locations within 250 km. of Berlin, Germany. Time measured is after reboot and *only* one run.

$ time curl -s http://localhost:8080/cities/52.516666/13.4/250 > /dev/null<br />
curl -s http://localhost:8080/cities/52.516666/13.4/250 > /dev/null  0.01s user 0.01s system 1% cpu 1.705 total

The GET-request is then cached using caffeine and returned much faster.

$ time curl -s http://localhost:8080/cities/52.516666/13.4/250 > /dev/null<br />
curl -s http://localhost:8080/cities/52.516666/13.4/250 > /dev/null  0.01s user 0.01s system 21% cpu 0.073 total

How many locations did we find?

$ curl -s http://localhost:8080/cities/52.516666/13.4/250 |grep -o name_|wc -l<br />
27896

Approx. 4.1 MB of json-data is returned.

$ curl -s http://localhost:8080/cities/52.516666/13.4/250 |wc<br />
0    7477 4344434

It is a rewrite of [jooby-worldcities][2] except the import-part. It uses postgresql as database, maven as build-system
and JDK 8. If you want you can [download][1] a dump of the database with approx. 3.1 mill. records and import it.
Create a database with the user worldcitites as owner and import it.

You can run the program from the command line using "mvn spring-boot:run" and connect to http://localhost:8080.


[1]: https://dl.dropboxusercontent.com/u/2729115/worldcities.zip
[2]: https://github.com/kometen/jooby-worldcities
[3]: https://projects.spring.io/spring-boot/
