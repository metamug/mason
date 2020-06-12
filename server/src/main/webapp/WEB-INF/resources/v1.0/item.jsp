<jsp:directive.include file="../fragments/mason-init.jspf"/>

<m:resource>

      <m:request method="GET" item="true">
         <sql:query var="result" dataSource="${datasource}">
               SELECT * from movie where id=?
         <sql:param value="${mtgReq.id}"/>
         </sql:query>
         <c:set target="${output}" property="getReq2"  value="${result}"/>
     </m:request>

</m:resource>
