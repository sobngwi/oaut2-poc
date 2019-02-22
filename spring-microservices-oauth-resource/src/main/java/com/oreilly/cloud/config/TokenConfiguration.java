package com.oreilly.cloud.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import javax.sql.DataSource;

/**
 * @author <a href="mailto:sobngwi@gmail.com">Alain SOBNGWI</a>
 */
@Configuration
public class TokenConfiguration {

    @Bean
    public RemoteTokenServices tokenService(){
        RemoteTokenServices tokenService = new RemoteTokenServices();
        tokenService.setCheckTokenEndpointUrl("http://localhost:9090/oauth/check_token");
        tokenService.setClientId("resource1");
        tokenService.setClientSecret("secret");
        return tokenService;
    }

    @Bean
    public TokenStore tokenStore(){
        return new JdbcTokenStore(dataSource());
    }

    @Bean
    public DataSource dataSource(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.hsqldb.jdbcDriver");
        //dataSource.setUrl("jdbc:hsqldb:hsql://localhost/testdb");
        dataSource.setUrl("jdbc:hsqldb:hsql:file:oath2");
        dataSource.setUsername("SA");
        dataSource.setPassword("");
        return dataSource;
    }
}
