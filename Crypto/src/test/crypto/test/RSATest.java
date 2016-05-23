package crypto.test;

import key.Key;
import org.junit.Test;
import rsa.RSA;

import java.math.BigInteger;

import static org.junit.Assert.*;

public class RSATest {

    @Test
    public void test() {
        RSA rsa = new RSA();
        rsa.KeyGen(1024);

        Key myPublicKey = rsa.getPublicKey();
        BigInteger cipher = rsa.Encrypt(new BigInteger("1234"), myPublicKey);

        Key myPrivateKey = rsa.getSecretKey();
        BigInteger message = rsa.Decrypt(cipher, myPrivateKey);

        assertTrue(message.equals(new BigInteger("1234")));
    }

}