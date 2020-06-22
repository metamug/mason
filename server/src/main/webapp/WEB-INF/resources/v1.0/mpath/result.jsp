<jsp:directive.include file="../../fragments/mason-init.jspf"/>

<m:resource>
    <m:request method="GET">

        <sql:query var="result" dataSource="${datasource}">
            SELECT * FROM movie
        </sql:query>

        <m:execute className="com.example.MovieExecuteExample" var="movieCount" output="true">
            <m:arg name="movieInfo" value="${result}" />
        </m:execute>

    </m:request>        
</m:resource>