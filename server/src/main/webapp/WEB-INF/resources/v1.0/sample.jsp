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

    <m:request method='POST' item="item" >
        <c:set target="${output}" property="item" value="${item}"/>
        <c:set target="${output}" property="mtgReq.id" value="${mtgReq.id}"/>        
        <c:set target="${output}" property="postResult4" value="${mtgReq.params['test']}"/>
    </m:request>

</m:resource>
