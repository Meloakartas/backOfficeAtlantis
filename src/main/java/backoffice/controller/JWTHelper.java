package backoffice.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import java.util.Date;
import java.util.List;

class JWTHelper {

    static boolean isUserAuthenticated(String id_token)
    {
        DecodedJWT jwt = id_token != null ? JWTHelper.ParseJWT(id_token) : null;
        return jwt != null && JWTHelper.CheckAuthTokenValidity(jwt);
    }

    static DecodedJWT ParseJWT(String id_token)
    {
        try {
            return JWT.decode(id_token);
        } catch (JWTDecodeException exception){
            return null;
        }
    }

    private static boolean CheckAuthTokenValidity(DecodedJWT jwt)
    {
        return audienceMatch(jwt.getAudience()) && issuerMatch(jwt.getIssuer()) && validDates(jwt.getExpiresAt(), jwt.getNotBefore());
    }

    private static boolean audienceMatch(List<String> audience)
    {
        //System.out.println("Audience : " + audience.get(0));
        return audience.get(0).equals("27fb84fe-4baf-4b6b-bfe7-f2d0638f2790");
    }

    private static boolean issuerMatch(String issuer)
    {
        //System.out.println("Issuer : " + issuer );
        return issuer.equals("https://atlantisproject.b2clogin.com/41dd4f0b-7a80-473f-82fa-d518398d6a7f/v2.0/");
    }

    private static boolean validDates(Date expireDate, Date notBeforeDate)
    {
        //System.out.println("Expire Date : " + expireDate + " / Not Before Date : " + notBeforeDate + " / Current Date : " + new Date());
        return notBeforeDate.compareTo(new Date()) < 0 && expireDate.compareTo(new Date()) > 0;
    }

    private static void GenerateKeys()
    {
//        final RSAPrivateKey privateKey = null; //Get the key instance
//        final String privateKeyId = ""; //Create an Id for the above key;
//
//        RSAKeyProvider keyProvider = new RSAKeyProvider() {
//            @Override
//            public RSAPublicKey getPublicKeyById(String kid) {
//                //Received 'kid' value might be null if it wasn't defined in the Token's header
//                RSAPublicKey publicKey = jwkStore.get(kid);
//                return (RSAPublicKey) publicKey;
//            }
//
//            @Override
//            public RSAPrivateKey getPrivateKey() {
//                return privateKey;
//            }
//
//            @Override
//            public String getPrivateKeyId() {
//                return privateKeyId;
//            }
//        };
//
//        Algorithm algorithm = Algorithm.RSA256(keyProvider);

        //Check Public with Kid : https://blogs.aaddevsup.xyz/2019/03/using-jwt-io-to-verify-the-signature-of-a-jwt-token/

//        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXUyJ9.eyJpc3MiOiJhdXRoMCJ9.AbIJTDMFc7yUa5MhvcP03nJPyCPzZtQcGEp-zWfOkEE";
//        RSAPublicKey publicKey = null;//Get the key instance
//        RSAPrivateKey privateKey = null;//Get the key instance
//        try {
//            Algorithm algorithm = Algorithm.RSA256(publicKey, privateKey);
//            JWTVerifier verifier = JWT.require(algorithm)
//                    .withIssuer("auth0")
//                    .build(); //Reusable verifier instance
//            DecodedJWT jwt = verifier.verify(token);
//        } catch (JWTVerificationException exception){
//            //Invalid signature/claims
//        }
    }
}
