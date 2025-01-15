---
sidebar_position: 10
sidebar_label: Token Encryption
---

# Token Encryption

### **Overview**

This document explains how token encryption is implemented in a Spring Boot application. It covers the encoding and decoding of JWT tokens, password hashing, and secure login processes.

---

### **Token Generation and Encoding**

The `LoginServiceImpl` class is responsible for handling user login. It validates user credentials and generates an access token using the `AccessTokenEncoder`.

```java title="LoginServiceImpl.java"
@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AccessTokenEncoder accessTokenEncoder;

    @Override
    public OutputDTOLogin login(InputDTOLogin loginRequest) {
        UserEntity user = userRepository.findByEmail(loginRequest.getUsername());

        if (user == null || !passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException();
        }

        String accessToken = accessTokenEncoder.encode(new AccessTokenImpl(
            Long.valueOf(user.getId()), 
            List.of(user.getRole())
        ));

        return OutputDTOLogin.builder().accessToken(accessToken).build();
    }
}
```

---

### **Access Token Implementation**

#### **Encoding the Token**

The `AccessTokenEncoderDecoderImpl` encodes the token with user roles and IDs using the `io.jsonwebtoken` library. It also sets the token expiration time.

```java title="AccessTokenEncoderDecoderImpl.java (Encode)"
@Service
public class AccessTokenEncoderDecoderImpl implements AccessTokenEncoder {
    private final Key key;

    public AccessTokenEncoderDecoderImpl(@Value("${jwt.secret}") String secretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    @Override
    public String encode(AccessToken accessToken) {
        Map<String, Object> claimsMap = new HashMap<>();
        claimsMap.put("roles", accessToken.getRole());
        claimsMap.put("userId", accessToken.getUserId());

        Instant now = Instant.now();
        return Jwts.builder()
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plus(10, ChronoUnit.HOURS)))
                .addClaims(claimsMap)
                .signWith(key)
                .compact();
    }
}
```

#### **Decoding the Token**

The same class decodes the token to extract the user details and roles for authorization purposes.

```java title="AccessTokenEncoderDecoderImpl.java (Decode)"
@Override
public AccessToken decode(String accessTokenEncoded) {
    try {
        Jwt<?, Claims> jwt = Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(accessTokenEncoded);
        Claims claims = jwt.getBody();

        List<String> roles = claims.get("roles", List.class);
        Long userId = claims.get("userId", Long.class);

        return new AccessTokenImpl(userId, roles);
    } catch (JwtException e) {
        throw new InvalidAccessTokenException(e.getMessage());
    }
}
```

---

### **Password Hashing**

Password security is ensured by hashing passwords with `BCryptPasswordEncoder`. This encoder is defined in a configuration class.

```java title="PasswordEncoderConfig.java"
@Configuration
public class PasswordEncoderConfig {

    @Bean
    public PasswordEncoder createBCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
```

---

### **Key Components of Token Encryption**

1. **JWT Library:**
   - Used for creating and validating JSON Web Tokens (JWT).
   - Provides methods to encode and decode tokens securely.

2. **BCrypt Password Hashing:**
   - Ensures that passwords are stored securely by hashing them before saving in the database.

3. **Token Expiration:**
   - Tokens are valid for a limited time (e.g., 10 hours) to enhance security.

4. **Error Handling:**
   - If token validation fails, an `InvalidAccessTokenException` is thrown.

---

### **Summary**

- **Token Encoding:** Securely encodes user data into a JWT token with expiration.
- **Token Decoding:** Extracts user details for authentication and authorization.
- **Password Security:** Uses `BCryptPasswordEncoder` to hash passwords for secure storage.

This implementation ensures a robust and secure token-based authentication mechanism.