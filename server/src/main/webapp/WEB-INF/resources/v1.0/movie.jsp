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
        <c:set target="${output}" property="getReq2"  value="${result}"/>
    </m:request>


    <m:request method='POST'>
           <sql:update var="result" dataSource="${datasource}">
                INSERT INTO movie (releaseDate,name,rating) values (?,?,?)

                <sql:param value="${mtgReq.params['releaseDate']}"/>
                <sql:param value="${mtgReq.params['name']}"/>
                <sql:param value="${mtgReq.params['rating']}"/>
           </sql:update>
           <c:set target="${output}" property="postResult" value="${result}"/>
    </m:request>

    <m:request method="POST">
            <m:xrequest var="result" url="http://localhost:8080/rest/v1.0/movie" output="true" method="POST" >
                <m:header name="Content-Type" value="application/json"/>
                <m:xbody>
                    {
                        "releaseDate": "2004-04-05 15:34:22.0",
                        "name": "Avengers",
                        "rating": "4.6",
                    }
                </m:xbody>
            </m:xrequest>
    </m:request>


    <m:request method='PUT' item="true">
       <sql:update var="result" dataSource="${datasource}">
            UPDATE movie SET rating=? where id=?

            <sql:param value="${mtgReq.params['rating']}"/>
            <sql:param value="${mtgReq.id}"/>
       </sql:update>
       <c:set target="${output}" property="putResult" value="${result}"/>
    </m:request>


    <m:request method='DELETE' item="true">
        <sql:update var="result" dataSource="${datasource}">
            DELETE FROM movie WHERE id=?

            <sql:param value="${mtgReq.id}"/>
        </sql:update>
        <c:set target="${output}" property="deleteResult" value="${result}"/>
    </m:request>

</m:resource>