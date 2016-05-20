package crypto.test;

import elgamal.Elgamal;
import org.junit.Test;

import java.math.BigInteger;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.*;

public class ElgamalTest {

    @Test
    public void test () {
        System.out.println("===== Elgamal Test =====");
        BigInteger p = Elgamal.getPrime(50, 40, new Random());
        BigInteger g = Elgamal.randNum(p, new Random());
        BigInteger pPrime = p.subtract(BigInteger.ONE).divide(Elgamal.TWO);

        System.out.println("g^2 % p = " + g.modPow(Elgamal.TWO, p));
        System.out.println("g^p' % p = " + g.modPow(pPrime, p));
        System.out.println("g^(2p') % p = " + g.modPow(pPrime.multiply(Elgamal.TWO), p));

        // Elgamal test
        String myString = "Bonjour le monde";
        List<List<BigInteger>> pksk = Elgamal.KeyGen(200);
        List<BigInteger> encrypt = Elgamal.Encrypt(pksk.get(0).get(0), pksk.get(0).get(1), pksk.get(0).get(2), new BigInteger(myString.getBytes()));

        assertTrue(new String(Elgamal.Decrypt(pksk.get(1).get(0), pksk.get(1).get(1), encrypt.get(0), encrypt.get(1)).toByteArray()).equals(myString));
    }
}