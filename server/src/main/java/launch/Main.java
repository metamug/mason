package launch;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.StandardRoot;
import org.apache.tomcat.util.descriptor.web.ContextResource;

import javax.sql.DataSource;
import java.io.File;

public class Main {

    public static void main(String[] args) throws Exception {

        String webappDirLocation = "src/main/webapp/";
        Tomcat tomcat = new Tomcat();

        //The port that we should run on can be set into an environment variable
        //Look for that variable and default to 8080 if it isn't there.
        String webPort = System.getenv("PORT");
        if(webPort == null || webPort.isEmpty()) {
            webPort = "8080";
        }

        tomcat.setPort(Integer.valueOf(webPort));


        StandardContext ctx = (StandardContext) tomcat.addWebapp("/rest", new File(webappDirLocation).getAbsolutePath());
//        ctx.setDefaultContextXml("/META-INF/context.xml");
        System.out.println("configuring app with basedir: " + new File("./" + webappDirLocation).getAbsolutePath());

        // Declare an alternative location for your "WEB-INF/classes" dir
        // Servlet 3.0 annotation will work

        File additionWebInfClasses = new File("target/classes");
        WebResourceRoot resources = new StandardRoot(ctx);
        resources.addPreResources(new DirResourceSet(resources, "/WEB-INF/classes",
                additionWebInfClasses.getAbsolutePath(), "/"));
        ctx.setResources(resources);


        tomcat.enableNaming();
        ctx.setDefaultWebXml(new File("/WEB-INF/web.xml").getAbsolutePath());
        Connector connector = tomcat.getConnector();

//        ContextResource resource = new ContextResource();
//        resource.setProperty("factory", "org.apache.tomcat.jdbc.pool.DataSourceFactory");
//        resource.setName("jdbc/mason");
//        resource.setType(DataSource.class.getName());
//        resource.setProperty("driverClassName", "com.mysql.cj.jdbc.Driver");
//        resource.setProperty("jdbcUrl", "jdbc:mysql://192.168.1.11:3306/moviedb?useOldAliasMetadataBehavior=true&characterEncoding=UTF-8&zeroDateTimeBehavior=CONVERT_TO_NULL&characterSetResults=UTF-8&allowMultiQueries=true&connectTimeout=120000");
//        resource.setProperty("username", "moviebuff");
//        resource.setProperty("password", "password");
//        resource.setProperty("poolName", "masonSamplePool");
//        resource.setProperty("factory", "com.zaxxer.hikari.HikariJNDIFactory");
//        ctx.getNamingResources().addResource(resource);

        try {
            tomcat.start();
        } catch (LifecycleException e) {
            e.printStackTrace();
        }
        tomcat.getServer().await();
    }
}
