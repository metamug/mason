<jsp:directive.include file="../fragments/mason-init.jspf"/>
<m:resource>

    <m:request method="GET">
    	  <m:xrequest var="xreq" url="https://postman-echo.com/get?foo1=Hello&foo2=World"
                          method="GET" >
            <m:header name="Accept" value="application/json" />
        </m:xrequest>

        <c:set target="${output}" property="xreq" value="${xreq}"/>
        <m:script file="xtos.groovy" var="scriptOut" />
        <c:set target="${output}" property="sout" value="${scriptOut}"/>

    </m:request>

</m:resource>
