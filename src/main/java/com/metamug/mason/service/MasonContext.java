package com.metamug.mason.service;

import com.mysql.cj.jdbc.MysqlConnectionPoolDataSource;

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
            MysqlConnectionPoolDataSource ds = new MysqlConnectionPoolDataSource();
            ds.setURL("jdbc:mysql://192.168.1.11:3306/deepak?useOldAliasMetadataBehavior=true&amp;characterEncoding=UTF-8&amp;zeroDateTimeBehavior=CONVERT_TO_NULL&amp;characterSetResults=UTF-8&amp;allowMultiQueries=true&amp;connectTimeout=120000");
            ds.setUser("xuser");
            ds.setPassword("password");
            dataSources.put("jdbc/mason", ds);

            //add more datasources to the list as necessary
        }

        if (dataSources.containsKey(name)) {
            return dataSources.get(name);
        }

        throw new NamingException("Unable to find datasource: " + name);
    }

}
