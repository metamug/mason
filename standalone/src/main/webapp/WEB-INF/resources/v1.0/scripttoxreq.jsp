<jsp:directive.include file="../fragments/mason-init.jspf"/>

<m:resource>

    <m:request method="GET">
        <m:script file="test.groovy" var="script" />
        <m:xrequest output="true" var="xreq" url="https://postman-echo.com/post" method="POST" >
           <m:header name="Content-Type" value="application/json"/>
           <m:xbody>
               {
                   "foo1": "Welcome",
                   "foo2": "${script.message}"
               }
           </m:xbody>
        </m:xrequest>

    </m:request>

</m:resource>
