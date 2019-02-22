package com.oreilly.cloud.ctrl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.AccessTokenRequest;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;
import java.security.Principal;
import java.util.logging.Logger;

/**
 * @author <a href="mailto:sobngwi@gmail.com">Alain SOBNGWI</a>
 */

@RestController
public class ClientCtrl {
    private Logger log = Logger.getLogger("ClientCtrl");
    @Value("${resource.endPoint}")
    private String resourceEndPoint;

    @Autowired
    private OAuth2RestTemplate restTemplate;

    @RequestMapping("/execute")
    public String execute(Principal principal) throws URISyntaxException {
        User user  = (User) ((Authentication)principal).getPrincipal();
        log.info(String.format("userNAme :[%s] PWD : %s ,  Role = %s", user.getUsername(), user.getPassword(),user.getAuthorities()));

        final OAuth2ClientContext oAuth2ClientContext = this.restTemplate.getOAuth2ClientContext();
        log.info("Access Token1 =" + oAuth2ClientContext.getAccessToken());
        RequestEntity<String> request = new RequestEntity<>(HttpMethod.POST, new URI(this.resourceEndPoint));
        AccessTokenRequest accessTokenRequest = oAuth2ClientContext.getAccessTokenRequest();
        accessTokenRequest.set("username", user.getUsername());
        accessTokenRequest.set("password", user.getPassword());
        log.info(String.format("accessTokenRequest + %s", accessTokenRequest));
        final ResponseEntity<String> exchange = restTemplate.exchange(request, String.class);
        log.info(String.format("USer %s header = %s", user.getUsername(),  exchange.getHeaders()));
        log.info(String.format("statusCode = %s", exchange.getStatusCode()));
        log.info("Access Token2 =" + oAuth2ClientContext.getAccessToken());
        return exchange.getBody();
    }
}
