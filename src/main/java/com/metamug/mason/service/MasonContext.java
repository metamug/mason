package com.metamug.mason.service;

import com.mysql.cj.jdbc.MysqlConnectionPoolDataSource;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.util.Hashtable;

public class MasonContext extends InitialContext {


    private Hashtable<String, DataSource> dataSources;

    public MasonContext() throws NamingException {
        super(true);
        dataSources = new Hashtable<>();
    }

    @Override
    public Object lookup(String name) throws NamingException {

        if (dataSources.isEmpty()) { //init datasources

            HikariConfig hikariConfig = new HikariConfig();
            hikariConfig.setDriverClassName("com.mysql.cj.jdbc.Driver");
//            hikariConfig.setDataSourceClassName("com.mysql.cj.jdbc.MysqlConnectionPoolDataSource");
            hikariConfig.addDataSourceProperty("serverName","192.168.1.11");
            hikariConfig.addDataSourceProperty("port","3306");
            hikariConfig.addDataSourceProperty("databaseName", "moviedb");
            hikariConfig.setUsername("moviebuff");
            hikariConfig.setPassword("password");

            hikariConfig.setMaximumPoolSize(5);
            hikariConfig.setConnectionTestQuery("SELECT 1");
            hikariConfig.setPoolName("jdbc/mason");

//            hikariConfig.addDataSourceProperty("dataSource.cachePrepStmts", "true");
//            hikariConfig.addDataSourceProperty("dataSource.prepStmtCacheSize", "250");
//            hikariConfig.addDataSourceProperty("dataSource.prepStmtCacheSqlLimit", "2048");
//            hikariConfig.addDataSourceProperty("dataSource.useServerPrepStmts", "true");

            HikariDataSource ds = new HikariDataSource(hikariConfig);

//            MysqlConnectionPoolDataSource ds = new MysqlConnectionPoolDataSource();
//            ds.setURL("jdbc:mysql://192.168.1.11:3306/deepak?useOldAliasMetadataBehavior=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull&characterSetResults=UTF-8&allowMultiQueries=true&connectTimeout=120000");
//            ds.setUser("xuser");
//            ds.setPassword("password");
            dataSources.put("jdbc/mason", ds);

            //add more datasources to the list as necessary
        }

        if (dataSources.containsKey(name)) {
            return dataSources.get(name);
        }

        throw new NamingException("Unable to find datasource: " + name);
    }

}
