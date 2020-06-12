<jsp:directive.page pageEncoding="UTF-8" trimDirectiveWhitespaces="true"/>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<%@taglib uri="http://xml.metamug.net/jsp/jstl/mason" prefix="m" %>

<m:resource parentName="jwt">

    <m:request method="POST" >
       <sql:query var="authQuery" dataSource="${datasource}" >
            SELECT CONCAT(r.user_id,'') AS sub, r.role_name AS aud
            FROM usr_role r INNER JOIN usr u ON r.user_id=u.user_id
            WHERE u.user_name=? AND u.pass_word= ?
            <sql:param value="${mtgReq.params['user']}"/>
            <sql:param value="${mtgReq.params['pass']}"/>
       </sql:query>
       <m:execute className="com.metamug.mason.plugin.TokenGenerator" var="token" param="${mtgReq}" output="true">
         <m:arg name="aud" value="${authQuery.rows[0].aud}" />
         <m:arg name="sub" value="${authQuery.rows[0].sub}" />
       </m:execute>
      
    </m:request>

</m:resource>
