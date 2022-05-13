package com.danieltalik.fullStackApp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Configuration
@PropertySource("classpath:application.properties")
public class H2Config {

    @Value("${spring.datasource.url}")
    private String connectionURL;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;


    public String getPassword() {
        return password;
    }

    public String getConnectionURL() {
        return connectionURL;
    }

    public String getUsername() {
        return username;
    }

    public Connection connection(){
        Connection conn = null;
        try{
            conn = DriverManager.getConnection(getConnectionURL(),getUsername(),getPassword());
        }catch (SQLException e){
            e.getMessage();
        } return conn;
    }
}
