
<jsp:directive.page pageEncoding="UTF-8" trimDirectiveWhitespaces="true"/>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<%@taglib uri="http://xml.metamug.net/jsp/jstl/mason" prefix="m" %>

<m:resource>

    <m:request method='POST' className="com.example.entity.Movies">
        <c:forEach items="${mtgReq.body.films}" var="film" varStatus="loop">
            <sql:query var="result" dataSource="${datasource}">
                select ? as "date", ? as "name", ? as "rating"

                <sql:param value="${film.releaseDate}"/>
                <sql:param value="${film.name}"/>
                <sql:param value="${film.rating}"/> 
            </sql:query>
            <c:set target="${output}" property="postResult_${loop.index}" value="${result}"/>
        </c:forEach>
    </m:request>

    <m:request method='POST' item="item" >
        <c:set target="${output}" property="postResult0" value="${item}"/>
        <c:set target="${output}" property="postResult1" value="${mtgReq.id}"/>
        <c:set target="${output}" property="postResult2" value="${mtgReq.body}"/>
        <c:set target="${output}" property="postResult3" value="${mtgReq.body[1]}"/>
        <c:set target="${output}" property="postResult4" value="${mtgReq.body[1].name}"/>
    </m:request>

</m:resource>