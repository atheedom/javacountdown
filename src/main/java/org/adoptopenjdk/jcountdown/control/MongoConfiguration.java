package org.adoptopenjdk.jcountdown.control;

import java.util.Properties;


public class MongoConfiguration {

    private String host;
    private int port;
    private String username;
    private char[] password;
    private String databaseName;
   
        
    public MongoConfiguration(Properties properties){       
        setDatabaseName(properties.getProperty(PropertiesProducer.DATABASE_NAME));
        setHost(properties.getProperty(PropertiesProducer.HOST)); 
        setPort(Integer.valueOf(properties.getProperty(PropertiesProducer.PORT)));
        setUsername(properties.getProperty(PropertiesProducer.USERNAME));
        setPassword(properties.getProperty(PropertiesProducer.PASSWORD).toCharArray());      
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public char[] getPassword() {
        return password;
    }

    public void setPassword(char[] password) {
        this.password = password;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    } 

}
