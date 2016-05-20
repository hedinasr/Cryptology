package rsa;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class RSA {
    /**
     * Generate public and private key
     * return (d, p, q) & (e, N)
     *
     * @param lambda key size
     */
    static List<List<BigInteger>> KeyGen(int lambda) {
        BigInteger p, q, e, d, phi;
        // Generate two random big integer (size = lambda)
        p = new BigInteger(lambda, 40, new Random());
        q = new BigInteger(lambda, 40, new Random());
        // phi(N) = (p - 1)(q - 1)
        phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));

        // 0 < _e < _phi - 1 (why _phi - 1 and not just _phi)
        e = new BigInteger(phi.bitLength(), new Random());

        while (!e.gcd(phi).equals(BigInteger.ONE)) {
            e = new BigInteger(phi.bitLength(), new Random());
        }

        d = e.modInverse(phi);

        List<BigInteger> pk = new ArrayList<>(Arrays.asList(d, p, q));
        List<BigInteger> sk = new ArrayList<>(Arrays.asList(e, p.multiply(q)));
        return new ArrayList<>(Arrays.asList(pk, sk));
    }

    /**
     * Encrypt the message
     *
     * @param (e,N) public key for encrypt
     * @param message message to encrypt
     * @return the encrypted message
     */
    static BigInteger Encrypt(BigInteger e, BigInteger N, BigInteger message) {
        return message.modPow(e, N);
    }

    /**
     * Decrypt the message
     *
     * @param (d,p,q) secret key for decrypt
     * @param message encrypted message to decrypt
     * @return the decrypted message
     */
    static BigInteger Decrypt(BigInteger d,
                              BigInteger p,
                              BigInteger q,
                              BigInteger message) {
        return message.modPow(d, p.multiply(q));
    }

    /**
     * Decrypt the message with CRT algorithm
     *
     * @param (d,p,q) secret key for decrypt
     * @param message encrypted message
     * @return decrypted message
     */
    static BigInteger Decrypt_CRT(BigInteger d,
                                  BigInteger p,
                                  BigInteger q,
                                  BigInteger message) {
        BigInteger cp = message.mod(p);
        BigInteger cq = message.mod(q);
        BigInteger dp = d.mod(p.subtract(BigInteger.ONE));
        BigInteger dq = d.mod(q.subtract(BigInteger.ONE));
        BigInteger mp = cp.modPow(dp, p);
        BigInteger mq = cq.modPow(dq, q);
        return ((q.multiply(mp).multiply(q.modInverse(p))).add(p.multiply(mq).multiply(p.modInverse(q)))).mod(p.multiply(q));
    }
}