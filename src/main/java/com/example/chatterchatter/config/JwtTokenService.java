package com.example.chatterchatter.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.chatterchatter.config.constant.SecurityConstant;
import com.example.chatterchatter.model.UserPrincipal;
import com.example.chatterchatter.model.domain.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.chatterchatter.config.constant.SecurityConstant.JWT_TOKEN_HEADER;

@Service
public class JwtTokenService {

    @Value("jwt.secret")
    private String secret;

    public HttpHeaders getJwtHeader(User user) {
        UserPrincipal principal = new UserPrincipal(user);
        HttpHeaders headers = new HttpHeaders();
        headers.add(JWT_TOKEN_HEADER, this.generateJwtToken(principal));
        return headers;
    }

    public String generateJwtToken(UserPrincipal principal){
        String[] claims = getClaimsFromUser(principal);
        return JWT.create().withIssuer(SecurityConstant.WEBEC_ISSUER)
                .withAudience(SecurityConstant.WEBEC_ADMINISTRATION)
                .withIssuedAt(new Date(System.currentTimeMillis() + SecurityConstant.EXPIRATION_TIME))
                .withSubject(principal.getUsername())
                .withArrayClaim(SecurityConstant.AUTHORITIES, claims)
                .withExpiresAt(new Date())
                .sign(Algorithm.HMAC512(secret.getBytes()));
    }

    public List<GrantedAuthority> getAuthorities(String token){
        String[] claims = getClaimsFromToken(token);
        return Arrays.stream(claims).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    public UsernamePasswordAuthenticationToken getAuthentication(String username, List<GrantedAuthority> authorities, HttpServletRequest request) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, null, authorities);
        token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        return token;
    }

    public boolean isTokenValid(String username, String token){
        JWTVerifier verifier = getJWTVerifier();
        return StringUtils.isNotEmpty(username) && !isTokenExpired(verifier, token);
    }

    public String getSubject(String token){
        JWTVerifier verifier = getJWTVerifier();
        return verifier.verify(token).getSubject();
    }

    private boolean isTokenExpired(JWTVerifier verifier, String token) {
        Date expirationDate = verifier.verify(token).getExpiresAt();
        return expirationDate.before(new Date());
    }

    private String[] getClaimsFromToken(String token) {
        JWTVerifier verifier = getJWTVerifier();
        return verifier.verify(token).getClaim(SecurityConstant.AUTHORITIES).asArray(String.class);
    }

    private JWTVerifier getJWTVerifier() {
        JWTVerifier verifier;
        try {
            Algorithm algorithm = Algorithm.HMAC512(secret);
            verifier = JWT.require(algorithm).withIssuer(SecurityConstant.WEBEC_ISSUER).build();
        } catch (JWTVerificationException e){
            throw new JWTVerificationException(SecurityConstant.TOKEN_CANNOT_BE_VERIFIED);
        }
        return verifier;
    }

    private String[] getClaimsFromUser(UserPrincipal principal) {
        List<String> authorities = principal.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        return authorities.toArray(String[]::new);
    }
}
