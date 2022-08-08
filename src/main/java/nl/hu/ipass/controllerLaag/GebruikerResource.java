package nl.hu.ipass.controllerLaag;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;
import nl.hu.ipass.domeinLaag.Gebruiker;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.security.Key;
import java.util.AbstractMap;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

@Path("/gebruiker")
public class GebruikerResource {

    @GET
    @Path("/test")
    @Produces(MediaType.APPLICATION_JSON)
    public Response testFunction(){
        return Response.ok().build();
    }

    @POST
    @Path("/verifieer")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response authenticate(LoginAttempt login){
        String role = Gebruiker.verifieerLogin(login.name, login.password);
        if (role != null)
        {
            String token = createToken(login.name, role);
            return Response.ok(new AbstractMap.SimpleEntry<>("JWT", token)).build();
        }else {
            Map<String, String> message = new HashMap<>();
            message.put("error", "gebruiker niet gevonden");
            return Response.status(Response.Status.CONFLICT).entity(message).build();
        }
    }

    final static public Key key = MacProvider.generateKey();

    private String createToken(String naam, String rol) throws JwtException {
        Calendar expiration = Calendar.getInstance();
        expiration.add(Calendar.MINUTE, 30);

        return Jwts.builder()
                .setSubject(naam)
                .setExpiration(expiration.getTime())
                .claim("role", rol)
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();
    }
}
