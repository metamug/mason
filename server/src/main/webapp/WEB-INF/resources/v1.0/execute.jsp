<jsp:directive.page pageEncoding="UTF-8" trimDirectiveWhitespaces="true"/>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<%@taglib uri="http://xml.metamug.net/jsp/jstl/mason" prefix="m" %>

<m:resource>
    <m:request method="POST">
        <m:execute className="com.example.RequestHandler" var="getCustomer"
                   param="${mtgReq}" output="true"/>
    </m:request>
</m:resource>