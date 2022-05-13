package com.danieltalik.fullStackApp.config;

import com.danieltalik.fullStackApp.DAL.BirthdayDAL;
import com.danieltalik.fullStackApp.DAL.NicknameDAL;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("application.properties")
public class DataAccessConfig {

    @Bean
    H2Config configuration(){
        return new H2Config();
    }

    @Bean
    public BirthdayDAL birthdayDAL(){
        return new BirthdayDAL(configuration());
    }
    @Bean
    public NicknameDAL nicknameDAL(){
        return new NicknameDAL(configuration());
    }
}