<jsp:directive.include file="../fragments/mason-init.jspf"/>

<m:resource>

    <m:request method="GET">
        <sql:query var="result" dataSource="${datasource}">
            SELECT * from customer
        </sql:query>
        <m:execute className="com.metamug.plugin.ResultExample" var="execRes" param="${result}"/>
        <c:set target="${output}" property="getReq1" value="${execRes}" />
    </m:request>

</m:resource>
