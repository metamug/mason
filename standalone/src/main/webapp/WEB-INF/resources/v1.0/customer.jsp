<jsp:directive.include file="../fragments/mason-init.jspf"/>


<m:resource>

    <m:request method="GET">
       <sql:query var="result" dataSource="${datasource}">
          SELECT * FROM movie
       </sql:query>
       <c:set target="${output}" property="output" value="${result}"/>
    </m:request>

    <m:request method="GET" item="true">
             <sql:query var="result" dataSource="${datasource}">
                   SELECT * from movie where id=?
             <sql:param value="${mtgReq.id}"/>
             </sql:query>

              <c:if test="${result[0].rating gt 4}">
                   <c:set target="${output}" property="getReq2"  value="${result}"/>
              </c:if>
    </m:request>

</m:resource>