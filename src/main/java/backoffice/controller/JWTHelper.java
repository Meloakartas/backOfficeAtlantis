package backoffice.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import javafx.util.Pair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;
import java.util.Objects;

class JWTHelper {

    static boolean isUserAuthenticated(String id_token)
    {
        return (id_token != null ? IsTokenValid(id_token) : null) != null;
    }

    static DecodedJWT ParseJWT(String id_token)
    {
        try {
            return JWT.decode(id_token);
        } catch (JWTDecodeException exception){
            return null;
        }
    }

    private static RSAPublicKey getPublicKeyUsingModulusAndExponent(BigInteger modulus, BigInteger exponent) {
        try {
            return (RSAPublicKey) KeyFactory.getInstance("RSA")
                    .generatePublic(new RSAPublicKeySpec(modulus, exponent));
        } catch (Exception e) {
            return null;
        }
    }

    private static RSAPrivateKey getPrivateKeyUsingModulusAndExponent(BigInteger modulus, BigInteger exponent) {
        try {
            return (RSAPrivateKey) KeyFactory.getInstance("RSA")
                    .generatePrivate(new RSAPrivateKeySpec(modulus, exponent));
        } catch (Exception e) {
            return null;
        }
    }

    private static DecodedJWT IsTokenValid(String id_token)
    {
        DecodedJWT jwt = ParseJWT(id_token);
        Pair modulusExponent = null;

        try {
            modulusExponent = GetModulusAndExponent(Objects.requireNonNull(jwt).getKeyId());
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(modulusExponent == null)
            return null;

        BigInteger modulus = new BigInteger(1, Base64.getUrlDecoder().decode(modulusExponent.getKey().toString()));
        BigInteger publicExponent = new BigInteger(1, Base64.getUrlDecoder().decode(modulusExponent.getValue().toString()));

        RSAPublicKey publicKey;//Get the key instance

        publicKey = getPublicKeyUsingModulusAndExponent(modulus, publicExponent);

        if(publicKey == null)
            return null;

        RSAPrivateKey privateKey;//Get the key instance

        privateKey = getPrivateKeyUsingModulusAndExponent(modulus, publicExponent);

        if(privateKey == null)
            return null;

        try {
            Algorithm algorithm = Algorithm.RSA256(publicKey, privateKey);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("https://atlantisproject.b2clogin.com/41dd4f0b-7a80-473f-82fa-d518398d6a7f/v2.0/")
                    .withAudience("27fb84fe-4baf-4b6b-bfe7-f2d0638f2790")
                .build(); //Reusable verifier instance
            return verifier.verify(id_token);
        } catch (JWTVerificationException exception){

            System.out.println(exception.getMessage());
            return null;
        }
    }

    private static Pair GetModulusAndExponent(String kid) throws Exception {
        URL obj = new URL("https://atlantisproject.b2clogin.com/atlantisproject.onmicrosoft.com/discovery/v2.0/keys?p=b2c_1_signuporsignin");
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        String modulus = null;
        String exponent = null;

        JSONObject myResponse = new JSONObject(response.toString());
        JSONArray keys = (JSONArray) myResponse.get("keys");

        for(int i =0; i<keys.length(); i++)
        {
            JSONObject object = (JSONObject) keys.get(i);
            if(object.get("kid").toString().equals(kid))
            {
                modulus = object.get("n").toString();
                exponent = object.get("e").toString();
            }
        }

        return new Pair(modulus, exponent);
    }
}
