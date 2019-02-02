# MASON [![Tweet](https://img.shields.io/twitter/url/http/shields.io.svg?style=social)](https://twitter.com/intent/tweet?text=All%20New%20Way%20of%20Writing%20REST%20APIs&url=https://metamug.com/console&via=themetamug&hashtags=REST,API,MySQL,developers)

[![Build Status](https://travis-ci.org/metamug/mason.svg?branch=master)](https://travis-ci.org/metamug/mason) [![Coverage Status](https://coveralls.io/repos/github/metamug/mason/badge.svg?branch=develop)](https://coveralls.io/github/metamug/mason?branch=develop) [![SonarCloud Bugs](https://sonarcloud.io/api/project_badges/measure?project=metamug_mason&metric=bugs)](https://sonarcloud.io/component_measures/metric/reliability_rating/list?id=metamug_mason)
 [![SonarCloud Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=metamug_mason&metric=vulnerabilities)](https://sonarcloud.io/component_measures/metric/security_rating/list?id=metamug_mason) [![Open Source Love](https://badges.frapsoft.com/os/v2/open-source.png?v=103)](https://github.com/ellerbrock/open-source-badges/) [![LGPL v2.1 Metamug License](https://img.shields.io/github/license/metamug/mason.svg?style=flat&colorB=7CFC00)](https://opensource.org/licenses/LGPL-2.1)

Mason is an open-source, lightweight data access layer for REST resources designed to be used in Java web applications. It handles incoming API requests and routes the parameters to these resources.

### Motivation

Use JSP tags for editing database queries and request handling. To hot deploy REST APIs without compiling/deploying Java classes. Build REST APIs with JSP tags.

```xml
<jsp:directive.include file="../fragments/mason-init.jspf"/>
<%-- customer.jsp --%>
<m:resource>
    <m:request method="GET">
     	<sql:query var="result" dataSource="${datasource}"> 
		SELECT name, address, phone, type from retail_customer 
    	</sql:query>
     	<c:set target="${masonOutput}" property="all customers" value="${result}"/>
    </m:request>
</m:resource>
```

Sample Project
- https://github.com/metamug/mason-sample

### Mason Resources

Mason turns your Plain Old JSPs (with neat tag libraries) into REST Resources. Mason doesn't encourage [using scriptlets in Resource JSPs](http://balusc.omnifaces.org/2010/07/how-to-avoid-java-code-in-jsp-files.html).

You can handle GET, POST, PUT, DELETE requests in your JSP. Mason has been tested with tomcat 9. *jstl.jar* shipped with tomcat is make jstl work.

Learn more about jsp configurations here.
https://tomcat.apache.org/tomcat-9.0-doc/jasper-howto.html

### Mason Query

You can write database queries inside the resource JSP files as seen in the above example or you can place them in `{webAppDir}/WEB-INF/classes/query.properties` file and reference them inside the JSP files for reuse.

### Features?

- Request Processing
- Mapping resource URI to JSP
- HTTP BASIC and JWT Authentication
- Convert SQL Results into JSON/XML based on `Accept` Header 🌟
- Make <a href="https://metamug.com/docs/xrequest" target="_blank">External API Requests</a>
- <a href="https://metamug.com/docs/request-parameters#pagination-parameters" target="_blank">Pagination</a>
- Many more

### Mason Jar

![Mason Jar](http://www.hamptonart.com/image/cache/data/2015WEBPHOTOS/PS0927_MasonJar_BL-500x500.jpg)

Clone and package the project with <a href="https://maven.apache.org/download.cgi" target="_blank">mvn</a>

```
mvn clean package
```
After that you can find *mtg-mason-1.0.jar* inside the target folder. You can use this jar as a dependency in your Java webapp.

### How to use mason in your Webapp

1. Place the mason jar file in `{webAppDir}/WEB-INF/lib`

2. Download [jstl](http://www.java2s.com/Code/Jar/j/Downloadjstl12jar.htm) jar file and place it inside `{webAppDir}/WEB-INF/lib`

3. Create a folder `{webAppDir}/WEB-INF/resources/{resourceVersion}` and place your jsp files here.

4. Import mtg-mason.tld inside your jsp file. This taglib is present inside the mason jar and enables usage of the *mtg* prefix. You will also need to import the jstl taglib. Your jsp file should contain the following

```xml
<% @taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<% @taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<% @taglib uri="mtg-mason.tld" prefix="m" %>
<%@ page trimDirectiveWhitespaces="true" %>
```

5. Add the following filter and listener inside `{webAppDir}/WEB-INF/web.xml`

```xml
<filter>
    <filter-name>Router</filter-name>
    <filter-class>com.metamug.mason.Router</filter-class>
</filter>

<filter-mapping>
    <filter-name>Router</filter-name>
    <url-pattern>/*</url-pattern>
    <init-param>
        <param-name>datasource</param-name>
        <param-value>jdbc/mason</param-value>
    </init-param>
</filter-mapping>
```

All requests made to the jsp resources are routed through this filter.

6. Configure your data source in `{webAppDir}/META-INF/context.xml` file.

You can take a look at the [sample webapp](https://github.com/metamug/mason-sample).

### JDBC Drivers

Except for javaee-web-api since that would be present in your application server and any one out of HSQL, MySQL or PostgreSQL dependency.
We also support Oracle database but due to its licensing we can't ship oracle jdbc driver along.
So in case you are using Oracle database, you'll have to manually install its driver as a dependency in your project.

Instructions regarding how to do the same in below link(s) (You can refer either of them).
https://www.mkyong.com/maven/how-to-add-oracle-jdbc-driver-in-your-maven-local-repository/
					OR
https://stackoverflow.com/a/1074971/4800126

### Data Format Support

Mason supports `application/xml`, `applicaton/json` and `application/json+dataset`
Read More about how it is used here.
https://metamug.com/docs/api-request

### How To Contribute

Fork this repo and submit a PR against the listed issues. We will provide certificates to all those who succcessfully contribute and help close existing issues and submit new features.
