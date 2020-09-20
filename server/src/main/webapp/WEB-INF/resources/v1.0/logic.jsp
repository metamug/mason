<jsp:directive.include file="../fragments/mason-init.jspf"/>

<m:resource>
    <m:request method="GET">
        <m:execute className="com.metamug.mason.plugin.GroovyRunner" var="res" >
            <m:arg name="file" value="test.groovy" />
        </m:execute>
        <c:set target="${output}" property="response" value="${res}"/>
    </m:request>
</m:resource>
