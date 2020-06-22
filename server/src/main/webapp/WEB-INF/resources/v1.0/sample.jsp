<jsp:directive.page pageEncoding="UTF-8" trimDirectiveWhitespaces="true"/>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<%@taglib uri="http://xml.metamug.net/jsp/jstl/mason" prefix="m" %>

<m:resource>

    <m:request method="GET" item="true">
        <c:set target="${output}" property="b" value="${mtgReq.params['test']}"/>
        <c:set target="${output}" property="a" value="${mtgReq.id}"/>
        <c:set target="${output}" property="c" value="abb"/>
    </m:request>

</m:resource>
