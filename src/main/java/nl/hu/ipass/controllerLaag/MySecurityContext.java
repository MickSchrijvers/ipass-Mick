package nl.hu.ipass.controllerLaag;

import nl.hu.ipass.domeinLaag.Gebruiker;

import javax.ws.rs.core.SecurityContext;
import java.security.Principal;

public class MySecurityContext implements SecurityContext {
    private Gebruiker gebruiker;
    private String scheme;

    public MySecurityContext(Gebruiker gebruiker, String scheme) {
        this.gebruiker = gebruiker;
        this.scheme = scheme;
    }

    @Override
    public Principal getUserPrincipal() {
        return this.gebruiker;
    }

    @Override
    public boolean isUserInRole(String s) {
        if (gebruiker.getRol() != null) {
            System.out.printf("%s equals %s", s, gebruiker.getRol());
            return s.equals(gebruiker.getRol());
        }
        return false;
    }

    @Override
    public boolean isSecure() {
        return "https".equals(this.scheme);
    }

    @Override
    public String getAuthenticationScheme() {
        return SecurityContext.BASIC_AUTH;
    }
}
