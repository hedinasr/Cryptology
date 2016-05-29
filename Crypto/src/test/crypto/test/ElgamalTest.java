package crypto.test;

import elgamal.ElGamal;
import org.junit.Test;

import java.math.BigInteger;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.*;

public class ElGamalTest {

    @Test
    public void test () {
        System.out.println("===== ElGamal Test =====");
        BigInteger p = ElGamal.getPrime(50, 40, new Random());
        BigInteger g = ElGamal.randNum(p, new Random());
        BigInteger pPrime = p.subtract(BigInteger.ONE).divide(ElGamal.TWO);

        System.out.println(pPrime.isProbablePrime(40));

        System.out.println("g^2 % p = " + g.modPow(ElGamal.TWO, p));
        System.out.println("g^p' % p = " + g.modPow(pPrime, p));
        System.out.println("g^(2p') % p = " + g.modPow(pPrime.multiply(ElGamal.TWO), p));

        // ElGamal test
        String myString = "Bonjour le monde";
        List<List<BigInteger>> pksk = ElGamal.KeyGen(200);
        List<BigInteger> encrypt = ElGamal.Encrypt(pksk.get(0).get(0), pksk.get(0).get(1), pksk.get(0).get(2), new BigInteger(myString.getBytes()));

        assertTrue(new String(ElGamal.Decrypt(pksk.get(1).get(0), pksk.get(1).get(1), encrypt.get(0), encrypt.get(1)).toByteArray()).equals(myString));
    }
}