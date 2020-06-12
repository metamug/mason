<jsp:directive.include file="../fragments/mason-init.jspf"/>
<m:resource>

    <m:request method="GET">
    	  <m:xrequest var="xreq" url="https://postman-echo.com/get?foo1=Hello&foo2=World"
                          method="GET" >
            <m:header name="Accept" value="application/json" />
        </m:xrequest>
        <m:execute className="com.metamug.plugin.ExtractExample" var="execRes" param="${mtgReq}" output="true">
    		    <m:arg name="foo1" value="${m:jsonPath('$.args.foo1', xreq)}" />
    	  </m:execute>

    </m:request>

</m:resource>
