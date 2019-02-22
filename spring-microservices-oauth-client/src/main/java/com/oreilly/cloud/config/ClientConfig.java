package com.oreilly.cloud.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

/**
 * @author <a href="mailto:sobngwi@gmail.com">Alain SOBNGWI</a>
 */
@Component
public class ClientConfig {
    Logger log = Logger.getLogger("ClientConfig");

    @Value("${access.token.uri}")
    private String accessTokenUri;

    @Bean
    //@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS, value = "prototype")
    public OAuth2RestTemplate restTemplate(){
        log.info("Creating .... OAuth2RestTemplate");
        return new OAuth2RestTemplate(resource(), context());
    }

    @Bean
    @Scope(proxyMode = ScopedProxyMode.TARGET_CLASS, value = "prototype")
    public OAuth2ProtectedResourceDetails resource(){
        log.info("Creating .... OAuth2ProtectedResourceDetails");
        ResourceOwnerPasswordResourceDetails details = new ResourceOwnerPasswordResourceDetails();
        details.setAccessTokenUri(accessTokenUri);
        details.setClientId("webapp");
        details.setClientSecret("websecret");
        details.setGrantType("password");
        return details;
    }

    @Bean
    @Scope(proxyMode = ScopedProxyMode.TARGET_CLASS, value = "prototype")
    public DefaultOAuth2ClientContext context(){
        return new DefaultOAuth2ClientContext();
    }
}
