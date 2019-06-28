package backoffice.controller;

import backoffice.model.Key;
import com.auth0.jwt.interfaces.DecodedJWT;
import java.math.BigInteger;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

public interface IJWTHelper {
    boolean isUserAuthenticated(String id_token);

    DecodedJWT ParseJWT(String id_token);

    RSAPublicKey getPublicKeyUsingModulusAndExponent(BigInteger modulus, BigInteger exponent);

    RSAPrivateKey getPrivateKeyUsingModulusAndExponent(BigInteger modulus, BigInteger exponent);

    DecodedJWT IsTokenValid(String id_token);

    Key GetModulusAndExponent(String kid) throws Exception;
}
