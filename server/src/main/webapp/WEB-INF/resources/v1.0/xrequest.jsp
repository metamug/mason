<jsp:directive.include file="../fragments/mason-init.jspf"/>
<m:resource>

    <m:request method="GET">
        <m:xrequest var="xreq" url="https://postman-echo.com/get?foo1=bar1&foo2=bar2"
                          method="GET" output="true" >
            <m:header name="Accept" value="application/json" />
        </m:xrequest>
    </m:request>

    <m:request method="POST">
        <m:xrequest var="xreq" url="https://postman-echo.com/post" output="true" method="POST" >
            <m:header name="Content-Type" value="application/json"/>
            <m:xbody>
                {
                    "foo1": "foovalue1",
                    "foo2": "foovalue2"
                }
            </m:xbody>
        </m:xrequest>
    </m:request>

</m:resource>
