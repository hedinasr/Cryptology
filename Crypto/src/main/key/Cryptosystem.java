package key;

import key.Key;

import java.math.BigInteger;

public abstract class Cryptosystem {

    protected Key secretKey;

    protected Key publicKey;

    public abstract void KeyGen(int modulus);

    public abstract BigInteger Encrypt(BigInteger message, Key publicKey);

    public abstract BigInteger Decrypt(BigInteger encrypted, Key secretKey);
}
