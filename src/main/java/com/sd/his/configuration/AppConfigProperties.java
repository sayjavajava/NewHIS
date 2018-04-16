package com.sd.his.configuration;


import com.sd.his.model.Properties;

import com.sd.his.repositiories.PropertiesRepository;

import com.sd.his.repositiories.PropertiesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;


import javax.annotation.PostConstruct;
import javax.sql.DataSource;


@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix="app")
public class AppConfigProperties  {
    private String clientId;
    private String clientSecret;
    private String authServerScheme;



    public AppConfigProperties(){}

    @Autowired
    private PropertiesRepository propertiesRepo;




    @PostConstruct
     public void init(){
        Properties properties = propertiesRepo.findOne(1);
        this.setClientId(getClientId());
        this.setClientSecret(properties.getClientSecret());
        this.setAuthServerScheme(properties.getClientSecret());
    }

    private final Logger logger = LoggerFactory.getLogger(AppConfigProperties.class);
    public AppConfigProperties(String client_id, String client_secret, String auth_server_scheme) {
        this.clientId= client_id;
        this.clientSecret = client_secret;
        this.authServerScheme = auth_server_scheme;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getAuthServerScheme() {
        return authServerScheme;
    }

    public void setAuthServerScheme(String authServerScheme) {
        this.authServerScheme = authServerScheme;
    }

    public PropertiesRepository getPropertiesRepo() {
        return propertiesRepo;
    }

    public void setPropertiesRepo(PropertiesRepository propertiesRepo) {
        this.propertiesRepo = propertiesRepo;
    }

    public Logger getLogger() {
        return logger;
    }
}
