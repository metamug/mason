<jsp:directive.include file="../fragments/mason-init.jspf"/>

<m:resource>
    <m:request method="GET">
        <m:script file="test.groovy" var="res" />
        <c:set target="${output}" property="response" value="${res}"/>
    </m:request>
</m:resource>
