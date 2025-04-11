package ru.avoidedconnections.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.avoidedconnections.dto.JwtAuthenticationDTO;
import ru.avoidedconnections.model.JwtBlacklist;
import ru.avoidedconnections.repository.JwtBlacklistRepository;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;

@Component
public class JwtService {

    private static final Logger LOGGER = LogManager.getLogger(JwtService.class);
    @Value("d71b6c245aa5aabc13a93c64ae02059fa0d3bc0b131285f4190e1c4f12f5cb6ec629e391a4f4899aca1ddaf8951cd4b02b3888c7da44ab887b83890197eed5fd")
    private String jwtSecret;
    @Autowired
    private JwtBlacklistRepository jwtBlacklistRepository;

    public JwtAuthenticationDTO generateAuthToken(String name) {
        JwtAuthenticationDTO jwtDto = new JwtAuthenticationDTO();
        jwtDto.setAccessToken(generateJwtToken(name));
        jwtDto.setRefreshToken(generateRefreshToken(name));
        return jwtDto;
    }

    public JwtAuthenticationDTO refreshBaseToken(String name, String refreshToken) {
        JwtAuthenticationDTO jwtDto = new JwtAuthenticationDTO();
        jwtDto.setAccessToken(generateJwtToken(name));
        jwtDto.setRefreshToken(refreshToken);
        return jwtDto;
    }

    public String getNameFromToken(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claims.getSubject();
    }

    public boolean validateJwtToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(getSignInKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            return true;
        } catch (ExpiredJwtException expEx) {
            LOGGER.error("Expired JwtException", expEx);
        } catch (UnsupportedJwtException expEx) {
            LOGGER.error("Unsupported JwtException", expEx);
        } catch (MalformedJwtException expEx) {
            LOGGER.error("UMalformed JwtException", expEx);
        } catch (SecurityException expEx) {
            LOGGER.error("Security Exception", expEx);
        }catch (Exception expEx) {
            LOGGER.error("invalid token", expEx);
        }
        return false;
    }

    private String generateJwtToken(String name) {
        Date date = Date.from(LocalDateTime.now().plusMinutes(30).atZone(ZoneId.systemDefault()).toInstant());
        return Jwts.builder()
                .subject(name)
                .expiration(date)
                .signWith(getSignInKey())
                .compact();
    }

    private String generateRefreshToken(String name) {
        Date date = Date.from(LocalDateTime.now().plusDays(1).atZone(ZoneId.systemDefault()).toInstant());
        return Jwts.builder()
                .subject(name)
                .expiration(date)
                .signWith(getSignInKey())
                .compact();
    }

    public Date getExpirationFromToken(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claims.getExpiration();
    }

    public void addTokenToBlacklist(String token, Instant expirationDate) {
        JwtBlacklist jwtBlacklist = new JwtBlacklist();
        jwtBlacklist.setToken(token);
        jwtBlacklist.setExpirationDate(expirationDate);
        jwtBlacklistRepository.save(jwtBlacklist);
    }


    private SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
