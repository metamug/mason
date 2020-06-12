<jsp:directive.include file="../fragments/mason-init.jspf"/>

<m:resource>

    <m:request method="GET">
       <m:header name="CustomHeader1" value="Value1" />
       <m:header name="CustomHeader2" value="Value2" />
       <sql:query var="result" dataSource="${datasource}">
          SELECT * FROM movie
       </sql:query>
       <c:set target="${output}" property="output" value="${result}"/>
    </m:request>

</m:resource>
