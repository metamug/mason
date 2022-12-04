# Server

This a sample project build with embedded tomcat and uses mason tag library. 
This can be deployed as a standalone jar in the microservices environment.

## Run 
```
mvn compile exec:java -Dexec.mainClass=launch.Main
```

## Examples

Run the maven project And point to the following url. The source code for these rest resources
are found in the [src/main/webapp/resources/v1.0/](server/src/main/webapp/resources/v1.0/)

### Set Output values

```
http://localhost:3000/rest/v1.0/sample
```
### Object Marshalling

```
http://localhost:3000/rest/v1.0/execute
```
### SQL

```
http://localhost:3000/rest/v1.0/movie
```

For the sql to work you must update the db credentials in context.xml. Note the server loads much 
faster if context.xml doesn't contain any datasource. As this library uses Hikari-cp 

### Scripting

```
http://localhost:8082/rest/v1.0/logic
```
