package nl.hu.ipass.controllerLaag;

import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.crypto.MacProvider;
import nl.hu.ipass.domeinLaag.*;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.security.Key;
import java.util.*;

@Path("/gebruiker")
public class GebruikerResource {

    @POST
    @Path("/verifieer")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response authenticate(LoginAttempt login){
        int id = Gebruiker.verifieerLogin(login.name, login.password);
        Gebruiker gebruiker = Gebruiker.getGebruikerBijId(id);

        if (id != 0)
        {
            String token = createToken(login.name, gebruiker.getRol(), id);
            
            return Response.ok(new AbstractMap.SimpleEntry<>("JWT", token)).build();
        }else {
            Map<String, String> message = new HashMap<>();
            message.put("error", "gebruiker niet gevonden");
            return Response.status(Response.Status.CONFLICT).entity(message).build();
        }
    }

    final static public Key key = MacProvider.generateKey();

    private String createToken(String naam, String rol, int id) throws JwtException {
        Calendar expiration = Calendar.getInstance();
        expiration.add(Calendar.MINUTE, 30);

        return Jwts.builder()
                .setSubject(naam)
                .setExpiration(expiration.getTime())
                .claim("role", rol)
                .claim("id", id)
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();
    }

    private static Gebruiker getGebruikerBijToken(String token){
        JwtParser parser = Jwts.parser().setSigningKey(GebruikerResource.key);
        Claims claims = parser.parseClaimsJws(token).getBody();

        Gebruiker gebruiker = Gebruiker.getGebruikerBijId((Integer) claims.get("id"));

        return gebruiker;
    }

    @POST
    @Path("/gerecht/nieuw")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response setGerecht(NewRecipe nieuwRecept) {

        Gebruiker gebruiker = getGebruikerBijToken(nieuwRecept.jwt);

        Kookboek kookboek = gebruiker.getKookboek();

        Gerecht gerecht = new Gerecht(nieuwRecept.titel);
        Recept recept = new Recept(nieuwRecept.titel);

        for (String stap : nieuwRecept.stappen) {recept.updateStappen(stap);}
        for (String benodigheid : nieuwRecept.benodigdheden) {recept.updateBenodigdheden(benodigheid);}

        gerecht.setRecept(recept);
        kookboek.updateGerechten(gerecht);

        return Response.ok().build();
    }

    @POST
    @Path("/kookboek")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getKookboek(Token token) {
        Gebruiker gebruiker = getGebruikerBijToken(token.jwt);
        Kookboek kookboek = gebruiker.getKookboek();
        ArrayList<Gerecht> gerechten = kookboek.getGerechten();

        Map<String, ArrayList<Gerecht>> kookboekMap = new HashMap<>();
        kookboekMap.put(kookboek.getTitel(), gerechten);


        return Response.ok(kookboekMap).build();
    }

    @POST
    @Path("/gerecht/{gerechtid}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRecept(@PathParam("gerechtid") int gerechtId){
        ArrayList<Gerecht> alleGerechtenList = new ArrayList<>();
        Recept recept;

        if (alleGerechtenList != null) {
            for (Gerecht element : alleGerechtenList) {
                if (element.getId() == gerechtId) {
                    recept = element.getRecept();
                    if (recept != null) {
                        Map<ArrayList<String>, ArrayList<String>> receptStappenBenodigdhedenMap = new HashMap<>();
                        receptStappenBenodigdhedenMap.put(recept.getStappen(), recept.getBenodigdheden());

                        Map<String, Map<ArrayList<String>, ArrayList<String>>> receptMap = new HashMap<>();
                        receptMap.put(recept.getTitel(), receptStappenBenodigdhedenMap);

                        return Response.ok(receptMap).build();
                    }
                } else {
                    Map<String, String> error = new HashMap<>();
                    error.put("Error", "Geen gerecht gevonden");
                    return Response.status(Response.Status.CONFLICT).entity(error).build();
                }
            }
        }

        Map<String, String> error = new HashMap<>();
        error.put("Error", "Geen gerechten gevonden");
        return Response.status(Response.Status.CONFLICT).entity(error).build();
    }

}
