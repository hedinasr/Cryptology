package rsa;

import key.Cryptosystem;
import key.Key;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * Security of RSA depends on the difficulty of factoring large integers.
 */
public final class RSA extends Cryptosystem {

    private Key secretKey;

    private Key publicKey;

    /**
     * Generate public and private key
     * return (d, p, q) & (e, N)
     *
     * @param modulus key size
     */
    @Override
    public void KeyGen(int modulus) {
        BigInteger p = new BigInteger(modulus, 40, new Random());
        BigInteger q = new BigInteger(modulus, 40, new Random());
        BigInteger phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));

        BigInteger e = new BigInteger(phi.bitLength(), new Random());

        while (!e.gcd(phi).equals(BigInteger.ONE)) {
            e = new BigInteger(phi.bitLength(), new Random());
        }

        BigInteger d = e.modInverse(phi);
        BigInteger N = p.multiply(q);

        secretKey = new Key(new ArrayList<>(Arrays.asList(d, p, q)));
        publicKey = new Key(new ArrayList<>(Arrays.asList(e, N)));
    }

    /**
     * Encrypt the message
     *
     * @param message BigInteger message to encrypt
     * @return the encrypted message
     */
    @Override
    public BigInteger Encrypt(BigInteger message, Key publicKey) {
        return message.modPow(publicKey.getKey().get(0), publicKey.getKey().get(1));
    }

    /**
     * Decrypt the message
     *
     * @param encrypted encrypted message to decrypt
     * @return the decrypted message
     */
    @Override
    public BigInteger Decrypt(BigInteger encrypted, Key secretKey) {
        return encrypted.modPow(secretKey.getKey().get(0), secretKey.getKey().get(1).multiply(secretKey.getKey().get(2)));
    }

    /**
     * Decrypt the message with CRT algorithm
     *
     * @param encrypted encrypted message
     * @param secretKey key for decrypt
     * @return decrypted message
     */
    public static BigInteger Decrypt_CRT(BigInteger encrypted,
                                         Key secretKey) {
        BigInteger d = secretKey.getKey().get(0);
        BigInteger p = secretKey.getKey().get(1);
        BigInteger q = secretKey.getKey().get(2);

        BigInteger cp = encrypted.mod(p);
        BigInteger cq = encrypted.mod(q);
        BigInteger dp = d.mod(p.subtract(BigInteger.ONE));
        BigInteger dq = d.mod(q.subtract(BigInteger.ONE));
        BigInteger mp = cp.modPow(dp, p);
        BigInteger mq = cq.modPow(dq, q);

        return ((q.multiply(mp).multiply(q.modInverse(p))).add(p.multiply(mq).multiply(p.modInverse(q)))).mod(p.multiply(q));
    }

    public Key getPublicKey() {
        return publicKey;
    }

    public Key getSecretKey() {
        return secretKey;
    }
}