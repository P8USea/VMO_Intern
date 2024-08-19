package com.example.apartmentmanagement.service;

import com.example.apartmentmanagement.dto.request.AuthenticationRequest;
import com.example.apartmentmanagement.dto.request.IntrospectRequest;
import com.example.apartmentmanagement.dto.response.AuthenticationResponse;
import com.example.apartmentmanagement.dto.response.IntrospectResponse;
import com.example.apartmentmanagement.entity.User;
import com.example.apartmentmanagement.exception.ErrorCode;
import com.example.apartmentmanagement.exception.UserException;
import com.example.apartmentmanagement.repository.UserRepository;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationService {
    @NonFinal
    @Value("${jwt.signing-key}")
    protected String SIGNER_KEY ;

    UserRepository userRepository;

    public IntrospectResponse introspect(IntrospectRequest request) throws JOSEException, ParseException {
        var token = request.getToken();
        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());
        SignedJWT signedJWT = SignedJWT.parse(token);

        Date expiration = signedJWT.getJWTClaimsSet().getExpirationTime();
        var verified = signedJWT.verify(verifier);
        return IntrospectResponse.builder()
                .valid(verified && expiration.after(new Date()))
                .build();

    }


    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new UserException(ErrorCode.USER_NOT_EXISTED));
        System.out.println(user);

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        boolean authenticated = passwordEncoder.matches(request.getPassword(), user.getPassword());
        if(!authenticated) throw new UserException(ErrorCode.UNAUTHENTICATED_USER);

        var token = tokenGenerator(user.getUsername());
        return AuthenticationResponse.builder()
                .token(token)
                .authenticated(true)
                .build();
     }

     private String tokenGenerator(String username){
         JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

         JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                 .subject(username)
                 .issuer("assminFuc")
                 .issueTime(new Date())
                 .expirationTime(new Date(Instant.now()
                         .plus(1, ChronoUnit.HOURS).toEpochMilli()))
                 .claim("username", username)
                 .build();

         Payload payload = new Payload(jwtClaimsSet.toJSONObject());

         JWSObject jwsObject = new JWSObject(header, payload);

         try {
             jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
             return jwsObject.serialize();
         } catch (JOSEException e) {
             throw new RuntimeException(e);
         }

     }

}
