This is a simple rest-api written with the Spring Boot Java framework. It returns cities/locations within a radius
specified by latitude and longitude in json-format.

It is a rewrite of [jooby-worldcities][2] except the import-part. It uses postgresql as database, maven as build-system
and JDK 8. If you want you can [download][1] a dump of the database with approx. 3.1 mill. records and import it.
Create a database with the user worldcitites as owner and import it.

[1]: https://dl.dropboxusercontent.com/u/2729115/worldcities.zip
[2]: https://github.com/kometen/jooby-worldcities
