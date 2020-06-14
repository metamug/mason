
<jsp:directive.page pageEncoding="UTF-8" trimDirectiveWhitespaces="true"/>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<%@taglib uri="http://xml.metamug.net/jsp/jstl/mason" prefix="m" %>

<m:resource>

        <m:request method='POST' className="com.example.entity.Movies">
            <c:forEach items="${rbody.films}" var="film" varStatus="loop">
                <sql:query var="result" dataSource="${datasource}">
                    select ? as "date", ? as "name", ? as "rating"

                        <sql:param value="${film.releaseDate}"/>
                        <sql:param value="${film.name}"/>
                        <sql:param value="${film.rating}"/>
                </sql:query>
                <c:set target="${output}" property="postResult_${loop.index}" value="${result}"/>
            </c:forEach>
        </m:request>

    <m:request method='PUT' item="true">
        <sql:update var="result" dataSource="${datasource}">
            UPDATE movie SET rating=? where id=?

            <sql:param value="${mtgReq.params['rating']}"/>
            <sql:param value="${mtgReq.id}"/>
        </sql:update>
        <c:set target="${output}" property="putResult" value="${result}"/>
    </m:request>


</m:resource>