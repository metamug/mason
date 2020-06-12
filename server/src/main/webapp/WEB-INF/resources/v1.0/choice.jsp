<jsp:directive.include file="../fragments/mason-init.jspf"/>

<m:resource>

    <m:request method="GET">
        <c:if test="${mtgReq.params['q'] eq 1}">
             <sql:query var="result" dataSource="${datasource}"> SELECT * from movie </sql:query>
             <c:set target="${output}" property="getReq1" value="${result}"/>
        </c:if>
        <c:if test="${mtgReq.params['q'] eq 2}">
             <sql:query var="result" dataSource="${datasource}"> SELECT 'HELLO WHEN!' </sql:query>
             <c:set target="${output}" property="getReq1" value="${result}"/>
        </c:if>
    </m:request>
</m:resource>
