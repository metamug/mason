
<jsp:directive.page pageEncoding="UTF-8" trimDirectiveWhitespaces="true"/>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<%@taglib uri="http://xml.metamug.net/jsp/jstl/mason" prefix="m" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>

<m:resource>

    <m:request method='POST' className="com.example.entity.Movies">

        <c:set target="${output}" property="postResult2" value="${fn:length(mtgReq.body.films)}"/>
        <c:set target="${output}" property="postResult3" value="${mtgReq.body.films[0].rating}"/>
        <c:set target="${output}" property="postResult4" value="${mtgReq.body.films[1].name}"/>

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
        <c:set target="${output}" property="postResult2" value="${fn:length(mtgReq.body.films)}"/>

        <sql:update var="result" dataSource="${datasource}">
            INSERT INTO movie (releaseDate,name,rating) values (?,?,?)

            <sql:param value="${mtgReq.body.releaseDate}"/>
            <sql:param value="${mtgReq.body.name}"/>
            <sql:param value="${mtgReq.body.rating}"/>
        </sql:update>
        <c:set target="${output}" property="postResult" value="${result}"/>

    </m:request>

</m:resource>