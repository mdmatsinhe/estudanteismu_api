package siga.artsoft.api.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import siga.artsoft.api.user.User;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    public String gerarToken(User utilizador){
        try {
            var algoritimo = Algorithm.HMAC256(secret);
           return JWT.create()
                   .withIssuer("API Voll.med")
                   .withSubject(utilizador.getUsername())
                   .withClaim("subjectId",utilizador.getId())
                   .withClaim("loginCount",utilizador.getLoginCount())
                   .withExpiresAt(dataExpiracao())
                    .sign(algoritimo);
        } catch (JWTCreationException exception){
            // nInvalid Signing configuration / Couldn't convert Claims.
          throw new RuntimeException("erro ao gerar o token "+exception.getMessage());
        }
    }

    private Instant dataExpiracao(){
        return LocalDateTime.now().plusHours(3).toInstant(ZoneOffset.of("+02:00"));
    }

    public String getSubject(String tokenJWT) {
        try {
            var algoritmo = Algorithm.HMAC256(secret);
            return JWT.require(algoritmo)
                    .withIssuer("API Voll.med")
                    .build()
                    .verify(tokenJWT)
                    .getSubject();
        } catch (JWTVerificationException exception) {
            throw new RuntimeException("Token JWT inválido ou expirado!");
        }
    }
}
