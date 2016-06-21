package key;

import key.Key;

import java.math.BigInteger;

public abstract class Cryptosystem {

    // TODO add public & secret key here (?)

    public abstract void KeyGen(int modulus);

    public abstract BigInteger Encrypt(BigInteger message, Key publicKey);

    public abstract BigInteger Decrypt(BigInteger encrypted, Key secretKey);
}
