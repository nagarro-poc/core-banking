Steps to enable Spring Security for default authentication (JSESSIONID)

This approach is using JSESSIONID cookie returned after successful authentication 
and will be used for further calls until the session is active.

1. Add Spring security dependencies:
   <dependency>
   <groupId>org.springframework.boot</groupId>
   <artifactId>spring-boot-starter-security</artifactId>
   </dependency>
   <dependency>
   <groupId>org.springframework.security</groupId>
   <artifactId>spring-security-test</artifactId>
   <scope>test</scope>
   </dependency>
2. In Security Config create beans for password encoder and filter chain.
   Spring security requires beans to be defined for UserDetailsService and PasswordEncoder. 
   It will be used by Authentication Provider. 
3. Spring SecurityFilterChain has currently disabled CSRF and secured "/welcome" api request.
   The request to "/register" api is not secured. Both form login and basic http is enabled. 
4. For PasswordEncoder BcryptPasswordEncoder is used.
5. For UserDetailsService a custom component class is created that implements loadUserByUsername method. 
   It will find user from DB and create and return a User object with default authority. 
   Note-For authority "ROLE_" should be prefixed. 
6. UserAccountServiceImpl will have main logic for save operation. When saving a user in DB it will encode the password with the PasswordEncoder bean autowired.

Steps to enable Spring Security for JWT authentication

1. Create a secret key: either base 64 encoded, without base 64 encoding or random secret key using Spring security provided methods. 
   All the options are present in SecurityConstants.java
2. Specify JWT_HEADER = "Authorization"
3. Create two filters which will be responsible for JWT creation and validation - JWTGeneratorFilter and JWTValidatorFilter.
4. In JWTGeneratorFilter, 
   i) convert the secret into bytes array based on secret key method used (base64 encoded, without base64 encoding, random secret key).
   ii) Create JWT using:
         Jwts.builder().setIssuer("JAVA POC").setSubject("JWT Token")
         .claim("username", authentication.getName())
         .setIssuedAt(new Date())
         .setExpiration(new Date((new Date()).getTime() + 600000))
         .signWith(key).compact(); 
   iii) Set JWT in response with username.
5. In JWTValidatorFilter,
   i) Check if the token contains Authorization header and starts with "Bearer"
   ii) convert the secret into bytes array based on secret key method used (base64 encoded, without base64 encoding, random secret key).
   ii) Validate JWT using:
         Claims claims = Jwts.parserBuilder()
         .setSigningKey(key)
         .build()
         .parseClaimsJws(jwt)
         .getBody();
   iii) If it is validated, then create Authentication token and add to SecurityContextHolder
   iv) If any exception then return the response with UNAUTHORIZED status and do not continue further with filter chain. 
       This response will be received by api gateway.
6. Add the filters in SecurityConfig:
     .addFilterBefore(new JWTValidatorFilter(), BasicAuthenticationFilter.class)
     .addFilterAfter(new JWTGeneratorFilter(), BasicAuthenticationFilter.class)
7. Create a "/validate" and "/token" api endpoints which will return response if JWT generation or validation is successful. 
8. Any api call to these uri paths ("/validate" and "/token") will be intercepted first by the filters defined for JWT. 
   If the filter is successful then it will proceed to the api call and display response 
   or else will return error from the filter itself.