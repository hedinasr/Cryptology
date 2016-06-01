package crypto.test;

import key.Key;
import org.junit.Test;
import rsa.RSA;

import java.math.BigInteger;
import java.util.Random;

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

        System.out.println(myPublicKey.getKey().get(1).isProbablePrime(40));
        System.out.println(myPrivateKey.getKey().get(0).isProbablePrime(40));

        assertTrue(message.equals(new BigInteger("1234")));
    }

    /**
     * Soient c = Encrypt(m) et c' = Encrypt(m').
     * Est-ce-que l'élément c * c' (mod N) est un chiffrement
     * de m * m' (mod N) ? Oui
     */
    @Test
    public void test1() {
        RSA rsa = new RSA();
        rsa.KeyGen(1024);

        // get the public & secret key
        Key myPublicKey = rsa.getPublicKey();
        Key myPrivateKey = rsa.getSecretKey();

        // Generate m & m'
        BigInteger m = new BigInteger(4, new Random());
        BigInteger m_prime = new BigInteger(4, new Random());

        // compute m * m'
        BigInteger m_prod = m.multiply(m_prime);

        // generate c & c'
        BigInteger c = rsa.Encrypt(m, myPublicKey);
        BigInteger c_prime = rsa.Encrypt(m_prime, myPublicKey);
        BigInteger c_prod = rsa.Encrypt(m_prod, myPublicKey);

        BigInteger cypher = c.multiply(c_prime);

        assertTrue(cypher.mod(myPublicKey.getKey().get(1)).equals(c_prod.mod(myPublicKey.getKey().get(1))));
    }

    @Test
    public void test2() {
        RSA rsa = new RSA();
        rsa.KeyGen(1024);
        Key myPublicKey = rsa.getPublicKey();

        while (true) {
            BigInteger message = new BigInteger(10, new Random());
            BigInteger cipher = rsa.Encrypt(message, myPublicKey);
            if (message.bitLength() < cipher.bitLength())
                break;
        }

        System.out.println("Bingo !!!");
    }
}