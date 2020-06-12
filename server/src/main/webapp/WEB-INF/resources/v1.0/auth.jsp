<jsp:directive.page pageEncoding="UTF-8" trimDirectiveWhitespaces="true"/>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<%@taglib uri="http://xml.metamug.net/jsp/jstl/mason" prefix="m" %>

<m:resource auth="supplier">

    <m:request method="GET">
        <sql:query var="result" dataSource="${datasource}"> 
        	SELECT * from movie
        </sql:query>
        <c:set target="${masonOutput}" property="d0" value="${result}"/>
    </m:request>

</m:resource>
