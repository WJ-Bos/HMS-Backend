package main.wjbos.zenhotel.Utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.function.Function;

@Service
public class JWTUtils {

    private static final long EXPIRATION_TIME = 604_800_000; // 7 days

    private final SecretKey Key;

    /**
     * Initializes the secret key used for signing the JWT tokens.
     * The secret key is a base64-decoded string that is converted to a SecretKeySpec instance.
     * The secret key is used to sign the JWT tokens with the HMAC-SHA256 algorithm.
     */
    public JWTUtils() {
        String secretKey = "345678643459834579384634572985739865";
        byte[] keyBytes = Base64.getDecoder().decode(secretKey.getBytes(StandardCharsets.UTF_8));
        this.Key = new SecretKeySpec(keyBytes,"HmacSHA256");
    }

    /**
     * Generates a JWT token for a given User.
     * The token is built with the following claims:
     * - Subject: The username of the user, obtained from UserDetails.
     * - Issued At: The current timestamp.
     * - Expiration: The current timestamp plus the defined expiration time (7 days).
     * The token is then signed using the HMAC-SHA256 algorithm and the secret key.
     * Finally, the token is returned as a compact, serialized string.
     *
     * @param userDetails The user for which the token is generated.
     * @return A JWT token as a compact, serialized string.
     */
    public String generateToken(UserDetails userDetails) {
        return Jwts.builder().subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(Key)
                .compact();
    }

    /**
     * Extracts the username from a given JWT token.
     * The username is obtained from the subject claim of the JWT token.
     *
     * @param token The JWT token from which to extract the username.
     * @return The username contained in the JWT token.
     */
    public String extractUsername(String token) {
        return extractClaims(token, Claims::getSubject);
    }


    /**
     * Extracts a claim from a given JWT token using the provided {@link Function}.
     * The {@link Function} takes a {@link Claims} object and returns the desired claim.
     *
     * @param token The JWT token from which to extract the claim.
     * @param claimsResolver The function that extracts the claim from the parsed JWT token.
     * @param <T> The type of the extracted claim.
     * @return The extracted claim.
     */
    private <T> T extractClaims(String token, Function<Claims, T> claimsResolver) {
        return claimsResolver.apply(Jwts.parser().verifyWith(Key).build().parseSignedClaims(token).getPayload());
    }


    /**
     * Checks if the given JWT token is valid.
     * The token is considered valid if:
     * 1. The subject claim of the token matches the provided user's username.
     * 2. The token is not expired.
     *
     * @param token The JWT token to validate.
     * @param userDetails The user for which the token is generated.
     * @return true if the token is valid, false otherwise.
     */
    public boolean isValidToken(String token, UserDetails userDetails) {
        final String userName = extractUsername(token);
        return  (userName.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }


    private boolean isTokenExpired(String token) {
        // Returns true if the token has expired, false otherwise
        // The expiration of the token is determined by the expiration claim
        // of the token, which is set when the token is generated.
        // The expiration claim is compared to the current timestamp to determine
        // if the token has expired.
        return extractClaims(token, Claims::getExpiration).before(new Date());
    }
}
