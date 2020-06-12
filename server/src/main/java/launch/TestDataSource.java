package launch;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TestDataSource {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        HikariConfig hikariConfig = new HikariConfig();
        Class.forName("com.mysql.cj.jdbc.Driver");

        hikariConfig.setDriverClassName("com.mysql.cj.jdbc.Driver");
//            hikariConfig.setDataSourceClassName("com.mysql.cj.jdbc.MysqlConnectionPoolDataSource");
        hikariConfig.setJdbcUrl("jdbc:mysql://localhost:3306/moviedb?useOldAliasMetadataBehavior=true&characterEncoding=UTF-8&zeroDateTimeBehavior=CONVERT_TO_NULL&characterSetResults=UTF-8&allowMultiQueries=true&connectTimeout=120000");
//        hikariConfig.addDataSourceProperty("serverName","192.168.1.11");
//        hikariConfig.addDataSourceProperty("port","3306");
//        hikariConfig.addDataSourceProperty("databaseName", "moviedb");
        hikariConfig.setUsername("moviebuff");
        hikariConfig.setPassword("");

        hikariConfig.setMaximumPoolSize(5);
        hikariConfig.setConnectionTestQuery("SELECT 1");
        hikariConfig.setPoolName("jdbc/mason");

        DataSource ds = new HikariDataSource(hikariConfig);
        try {
            Connection connection = ds.getConnection();
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("select * from movie");
            while (rs.next())
                System.out.println(rs.getString(1) + "  " + rs.getString(2) + "  " + rs.getString(3));
            connection.close();
        } catch (Exception e) {
            System.out.println(e);
        }

    }
}
