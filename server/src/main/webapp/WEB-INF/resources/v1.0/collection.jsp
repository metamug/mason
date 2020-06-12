<jsp:directive.include file="../fragments/mason-init.jspf"/>

<m:resource>

    <m:request method="GET">
       <sql:query var="result" dataSource="${datasource}">
          SELECT * FROM movie
       </sql:query>
       <c:set target="${output}" property="output" value="${result}"/>
    </m:request>

</m:resource>
