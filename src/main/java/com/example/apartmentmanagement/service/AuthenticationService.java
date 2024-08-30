package com.example.apartmentmanagement.service;

import com.example.apartmentmanagement.dto.request.AuthenticationRequest;
import com.example.apartmentmanagement.dto.request.IntrospectRequest;
import com.example.apartmentmanagement.dto.request.LogOutRequest;
import com.example.apartmentmanagement.dto.request.RefreshRequest;
import com.example.apartmentmanagement.dto.response.AuthenticationResponse;
import com.example.apartmentmanagement.dto.response.IntrospectResponse;

import com.example.apartmentmanagement.entity.InvalidatedToken;
import com.example.apartmentmanagement.entity.User;
import com.example.apartmentmanagement.exception.AppException;
import com.example.apartmentmanagement.exception.ErrorCode;
import com.example.apartmentmanagement.exception.UserException;
import com.example.apartmentmanagement.repository.InvalidatedTokenRepository;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.StringJoiner;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationService {
    @NonFinal
    @Value("${jwt.signing-key}")
    protected String SIGNER_KEY ;

    @NonFinal
    @Value("${jwt.refreshable-duration}")
    protected long REFRESHABLE_DURATION;

    @NonFinal
    @Value("${jwt.valid-duration}")
    protected long VALID_DURATION;

    final UserRepository userRepository;
    final InvalidatedTokenRepository invalidatedTokenRepository;

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
        if(!authenticated) throw new UserException(ErrorCode.UNAUTHENTICATED);

        var token = tokenGenerator(user);
        return AuthenticationResponse.builder()
                .token(token)
                .authenticated(true)
                .build();
     }

     private String tokenGenerator(User user){
         JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

         JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                 .subject(user.getUsername())
                 .issuer("Admin Fuc")
                 .issueTime(new Date())
                 .expirationTime(new Date(
                         Instant.now().plus(VALID_DURATION, ChronoUnit.SECONDS).toEpochMilli()))
                 .jwtID(UUID.randomUUID().toString())
                 .claim("scope", buildScope(user))
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
    private SignedJWT verifyToken(String token, boolean isRefresh) throws JOSEException, ParseException {
        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());

        SignedJWT signedJWT = SignedJWT.parse(token);

        Date expiryTime = (isRefresh)
                ? new Date(signedJWT
                .getJWTClaimsSet()
                .getIssueTime()
                .toInstant()
                .plus(REFRESHABLE_DURATION, ChronoUnit.SECONDS)
                .toEpochMilli())
                : signedJWT.getJWTClaimsSet().getExpirationTime();

        var verified = signedJWT.verify(verifier);

        if (!(verified && expiryTime.after(new Date()))) throw new AppException(ErrorCode.UNAUTHENTICATED);

        if (invalidatedTokenRepository.existsById(signedJWT.getJWTClaimsSet().getJWTID()))
            throw new AppException(ErrorCode.UNAUTHENTICATED);

        return signedJWT;
    }
     private String buildScope(User user){
         StringJoiner stringJoiner = new StringJoiner(" ");
         if(!CollectionUtils.isEmpty(user.getRoles())){
             user.getRoles().forEach(role -> stringJoiner.add(role.name()));
         }
         return stringJoiner.toString();
     }

    public void logout(LogOutRequest request) throws ParseException, JOSEException {
        try {
            log.info("there?");
            var signToken = verifyToken(request.getToken(), true);

            log.info("there?");
            String jit = signToken.getJWTClaimsSet().getJWTID();
            log.info("there?");
            Date expiryTime = signToken.getJWTClaimsSet().getExpirationTime();
            log.info("there?");
            InvalidatedToken invalidatedToken =
                    InvalidatedToken.builder().id(jit).expiryTime(expiryTime).build();

            invalidatedTokenRepository.save(invalidatedToken);
        } catch (AppException exception) {
            log.info("Token already expired");
        }
    }

    public AuthenticationResponse refreshToken(RefreshRequest request) throws ParseException, JOSEException {
        var signedJWT = verifyToken(request.getToken(), true);

        var jit = signedJWT.getJWTClaimsSet().getJWTID();
        var expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();

        InvalidatedToken invalidatedToken =
                InvalidatedToken.builder().id(jit).expiryTime(expiryTime).build();

        invalidatedTokenRepository.save(invalidatedToken);

        var username = signedJWT.getJWTClaimsSet().getSubject();

        var user =
                userRepository.findByUsername(username).orElseThrow(() -> new AppException(ErrorCode.UNAUTHENTICATED));

        var token = tokenGenerator(user);

        return AuthenticationResponse.builder().token(token).authenticated(true).build();
    }
}
