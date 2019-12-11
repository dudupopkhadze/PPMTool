package dev.projects.ppmtool.security;

import dev.projects.ppmtool.domain.User;
import io.jsonwebtoken.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static dev.projects.ppmtool.security.SecurityConstants.EXPERATION_TIME;
import static dev.projects.ppmtool.security.SecurityConstants.SECRET;

@Component
public class JWTTokenProvider {

    public String   generateToken(Authentication authentication){
        User user =(User)authentication.getPrincipal();
        Date now = new Date(System.currentTimeMillis());
        Date expiryDate = new Date(now.getTime()+EXPERATION_TIME);

        String userId = Long.toString(user.getId());
        Map<String ,Object> claims = new HashMap<>();
        claims.put("id",userId);
        claims.put("username",user.getUsername());
        claims.put("fullName",user.getFullName());

        return Jwts.builder()
                .setSubject(userId)
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512,SECRET)
                .compact();
    }

    public boolean validateToken(String token){
        try {
            Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token);
            return  true;
        } catch (ExpiredJwtException e) {
            System.out.println("Expired JWt Token");
        } catch (UnsupportedJwtException e) {
            System.out.println("Unuported JWT Token");
        } catch (MalformedJwtException e) {
            System.out.println("Invalid JWT Token");
        } catch (SignatureException e) {
            System.out.println("Invalid JWT Signature");
        } catch (IllegalArgumentException e) {
            System.out.println("JWT claims string is Empty");
        }
        return false;
    }

    public Long getUserIdFromJWT(String token){
        Claims claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
        String id = (String)claims.get("id");

        return Long.parseLong(id);
    }

}
