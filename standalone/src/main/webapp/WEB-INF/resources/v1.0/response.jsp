<jsp:directive.include file="../fragments/mason-init.jspf"/>
<m:resource>

    <m:request method="GET">
        <m:xrequest url="https://postman-echo.com/get?foo1=bar1&foo2=bar2"
              var="xreq" method="GET" output="true"
                  className="com.metamug.plugin.ResponseExample" >
            <m:header name="Accept" value="application/json" />
        </m:xrequest>
    </m:request>

</m:resource>
