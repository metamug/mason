### Output Map

Mason provides an implicit map for collecting results from tags like sql, execute and xrequest.
This map is marshalled into JSON/XML depending on the `Accept` header in the request.

```jsp
<sql:query var="result" dataSource="${datasource}">
    SELECT * from movie
</sql:query>
<c:set target="${output}" property="movies" value="${result}"/>
```

As shown in the above example, result of the SQL query is added to output map. This will produce the following output.

```json
{
  "movies":[
    {
      "releaseDate": "2017-01-27 15:34:22.0",
      "name": "Reader",
      "rating": 3.1,
      "id": 1
    },
    {
      "releaseDate": "2017-01-27 15:34:22.0",
      "name": "The Dark Knight",
      "rating": 5,
      "id": 2
    }
  ]
}
```
> If the result is not added to the implicit output map, result will be added to the response.

#### Output Attribute

Adding the result to the output map is necessary for sql tag, since sql tag is provided by JSTL core library. 
For tags provided by mason like `xrequest` and `execute` an extra `output="true"` attribute can do the same.

```jsp
<m:request method="GET">
    <m:execute className="com.metamug.plugin.example.FileDownload" var="execRes" param="${mtgReq}" output="true" >
        <m:xparam name="host" value="your.host.net" />
        <m:xparam name="user" value="server" />
        <m:xparam name="password" value="password" />
        <m:xparam name="dir" value="/home/user/projects/files" />
    </m:execute>
    <c:set target="${output}" property="getResult" value="${execRes}" />
</m:request>
```
