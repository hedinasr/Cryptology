import javafx.util.Pair;

import java.io.UnsupportedEncodingException;
import java.security.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Antoine on 01/06/16.
 */
public class RSA_Signature_Elgamal {

    private static String BigToString(BigInteger b) {
        return new String(b.toByteArray());
    }

    private static BigInteger StringToBig(String s) {
        return new BigInteger(s.getBytes());
    }

    // generate key comme pour le chiffrement de rsa
    public static ArrayList<BigInteger> generateKey(int bitlen, int _e) {

        BigInteger p, q;
        BigInteger phi_N;
        BigInteger e = new BigInteger(String.valueOf(_e));

        do {
            p = new BigInteger(bitlen, 40, new Random());
            q = new BigInteger(bitlen, 40, new Random());

            //        N = p*q
            //        p-1 * q-1
            phi_N = p.subtract(BigInteger.ONE);
            BigInteger tmp = q.subtract(BigInteger.ONE);
            phi_N = phi_N.multiply(tmp);
        }
        // pgcd(e, fi(N)) == 1
        while (!e.gcd(phi_N).equals(BigInteger.ONE));

        BigInteger N = p.multiply(q);
        BigInteger d = e.modInverse(phi_N);

        ArrayList<BigInteger> res = new ArrayList<>();
        res.add(e);
        res.add(N);
        res.add(d);
        res.add(p);
        res.add(q);
        return res;
    }

    public static BigInteger RSA_Sign(BigInteger d, BigInteger p, BigInteger q, String m) {
        return StringToBig(m).modPow(d, p.multiply(q));
    }

    private static byte[] generateh(String m, int l, BigInteger r) throws NoSuchAlgorithmException {
        byte[] h = new byte[0];

        BigInteger mPrime = StringToBig(m);

        // concatenate r and m
        byte[] rByte = r.toByteArray();
        byte[] mByte = mPrime.toByteArray();

        byte[] res = new byte[rByte.length+ mByte.length];
        System.arraycopy(mByte, 0, res, 0, mByte.length);
        System.arraycopy(rByte, 0, res, mByte.length, rByte.length);

        MessageDigest md = MessageDigest.getInstance("SHA-256");

        for(int i = 1; i <= l; i++) {

            BigInteger tempBig = BigInteger.valueOf(l);
            byte[] lByte = tempBig.toByteArray();

            byte[] restmp = new byte[res.length+lByte.length];
            System.arraycopy(res, 0, restmp, 0, res.length);
            System.arraycopy(lByte, 0, restmp, res.length, lByte.length);

            byte[] sha256 = md.digest(restmp);

            if(i != 1) {
                byte[] hTemp = new byte[sha256.length + h.length];
                System.arraycopy(h, 0, hTemp, 0, h.length);
                System.arraycopy(sha256, 0, hTemp, h.length, sha256.length);
                h = new byte[hTemp.length];
                System.arraycopy(hTemp, 0, h, 0, hTemp.length);
            }
            else {
                h = new byte[sha256.length];
                System.arraycopy(sha256, 0, h, 0, sha256.length);
            }
        }
        return h;
    }

    public static ArrayList<BigInteger> RSA_Sign_Hash(BigInteger d, BigInteger p, BigInteger q, String m)
            throws NoSuchAlgorithmException, UnsupportedEncodingException {

        int n = p.multiply(q).bitLength();
        int l = 0;
        while(l*256 < n+100) {
            l++;
        }

        BigInteger r = new BigInteger(256, new Random());

        byte[] h = generateh(m, l, r);
        BigInteger b = new BigInteger(h);

        ArrayList<BigInteger> result = new ArrayList<>();
        result.add(b.mod(p.multiply(q)).modPow(d, p.multiply(q)));
        result.add(r);

        return result;
    }

    public static boolean RSA_Verify(BigInteger e, BigInteger N, String msg, BigInteger signature) {
        if(signature.modPow(e, N).equals(StringToBig(msg))) {
            return true;
        }
        else {
            return false;
        }
    }

    public static boolean RSA_Verify_Hash(BigInteger e, BigInteger N, String msg, BigInteger signature, BigInteger r) throws NoSuchAlgorithmException {
        int l = 0;
        while(l*256 < N.bitLength()+100) {
            l++;
        }

        BigInteger h = new BigInteger(generateh(msg, l, r));

        if(signature.modPow(e, N).equals(h.mod(N))) {
            return true;
        }
        else {
            return false;
        }
    }

    public static void main(String[] args) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String message = "TOTO";

        System.out.println("*************************** RSA Classique ***************************");
        ArrayList<BigInteger> key = generateKey(512, (2^16)-1);
        BigInteger signature = RSA_Sign(key.get(2), key.get(3), key.get(4), message);
        System.out.println("Signature du message : "+signature);

        Boolean res = RSA_Verify(key.get(0), key.get(1), message, signature);
        System.out.println("verification : "+res);

//        printKey(key);

        //******* fonction de hashage ********//
        System.out.println("*************************** RSA SHA256 ***************************");
        ArrayList<BigInteger> signatureHash = RSA_Sign_Hash(key.get(2), key.get(3), key.get(4), message);
        System.out.println("Signature du message : "+signatureHash.get(0));

        res = RSA_Verify_Hash(key.get(0), key.get(1), message, signatureHash.get(0), signatureHash.get(1));
        System.out.println("verification : "+res);


        //*********** partie sur Elgamal ***********//
        System.out.println("*************************** Elgamal ***************************");
        Implem i = new Implem();
        ArrayList<BigInteger> c =  i.generateKey(512);

        ArrayList<BigInteger> pubKey = new ArrayList<>();
        ArrayList<BigInteger> privKey = new ArrayList<>();

        pubKey.add(c.get(2));
        pubKey.add(c.get(3));
        pubKey.add(c.get(4));

        privKey.add(c.get(0));
        privKey.add(c.get(1));

        Pair chif = i.chiffre(message, pubKey);
        System.out.println("chiffré Elgamal du message "+ message +" : "+chif.getValue());

    }


    //*************************************** pour debug *******************************
    public static void printKey(ArrayList<BigInteger> key) {
        int i = 0;
        for(BigInteger it : key) {
            System.out.println("["+i+"]" + it);
            i++;
        }
    }
}
