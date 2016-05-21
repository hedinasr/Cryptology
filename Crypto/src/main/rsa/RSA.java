package rsa;

import java.math.BigInteger;
import java.util.Random;

public class RSA {

    private BigInteger privateKey;
    private BigInteger publicKey;
    private BigInteger modulus;

    /**
     * Generate public and private key
     * return (d, p, q) & (e, N)
     *
     * @param lambda key size
     */
    public void KeyGen(int lambda) {
        // Generate two random big integer (size = lambda)
        BigInteger p = new BigInteger(lambda, 40, new Random());
        BigInteger q = new BigInteger(lambda, 40, new Random());
        BigInteger phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));

        do {
            publicKey = new BigInteger(phi.bitLength(), new Random());
        } while (!publicKey.gcd(phi).equals(BigInteger.ONE));


        modulus = p.multiply(q);
        privateKey = publicKey.modInverse(phi);
    }

    /**
     * Encrypt the message
     *
     * @param message message to encrypt
     * @return the encrypted message
     */
    public BigInteger Encrypt(BigInteger message) {
        return message.modPow(publicKey, modulus);
    }

    /**
     * Decrypt the message
     *
     * @param encrypted encrypted message to decrypt
     * @return the decrypted message
     */
    public BigInteger Decrypt(BigInteger encrypted) {
        return encrypted.modPow(privateKey, modulus);
    }

    /**
     * Decrypt the message with CRT algorithm
     *
     * @param (d,p,q) secret key for decrypt
     * @param message encrypted message
     * @return decrypted message
     */
    public static BigInteger Decrypt_CRT(BigInteger d,
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