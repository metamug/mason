
<jsp:directive.page pageEncoding="UTF-8" trimDirectiveWhitespaces="true"/>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<%@taglib uri="http://xml.metamug.net/jsp/jstl/mason" prefix="m" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>

<m:resource>

    <m:request method='POST' className="com.example.entity.Movie">

        <c:set target="${output}" property="postResult3" value="${mtgReq.body.rating}"/>
        <c:set target="${output}" property="postResult4" value="${mtgReq.body.name}"/>

        <sql:query var="result" dataSource="${datasource}">
            select ? as "date", ? as "name", ? as "rating"

            <sql:param value="${mtgReq.body.releaseDate}"/>
            <sql:param value="${mtgReq.body.name}"/>
            <sql:param value="${mtgReq.body.rating}"/>
        </sql:query>
        <c:set target="${output}" property="postResult_${loop.index}" value="${result}"/>

    </m:request>


</m:resource>