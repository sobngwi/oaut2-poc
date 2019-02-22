package com.oreilly.cloud.ctrl;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.logging.Logger;

/**
 * @author <a href="mailto:sobngwi@gmail.com">Alain SOBNGWI</a>
 */
@RestController
public class Resource {
Logger log = Logger.getLogger(Resource.class.getName());
    @RequestMapping("/resource/endpoint")
    @PreAuthorize("hasRole('USER')")
    public String endpoint(Principal principal){
        log.info(String.format("principal %s", principal));
        return "Welcome Mr, Mme, " + principal.getName() +", This message is protected by the resource server.";
    }
}
