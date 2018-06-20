# mason

[![Build Status](https://travis-ci.org/metamug/mason.svg?branch=master)](https://travis-ci.org/metamug/mason) 

Mason is a rest api framework with just jsps. Cuts out the DAO layer.

You need to have Apache Maven installed on your system. You can get it <a href="https://maven.apache.org/download.cgi" target="_blank">here</a>. 

Download or clone the project and build using the mvn command
```
mvn clean package
```

After that you can find the mtg-mason-1.0.jar inside the target folder. You can use this jar as a dependency in your Java webapp.

You will need to add following line to your jsp for using the mtg prefix
```
<%@taglib uri="/META-INF/mtg-mason" prefix="mtg" %>
```

And also add the following to web.xml
```
<filter>
    <filter-name>RestRouterFilter</filter-name>
    <filter-class>com.metamug.api.filters.RestRouterFilter</filter-class>
</filter>
```