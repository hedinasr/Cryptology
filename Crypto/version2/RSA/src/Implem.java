/**
 * Antoine Roux p1409394
 * Clement Ricotta p1201800
 */

import javafx.util.Pair;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Random;

public class Implem {
    //  1. La méthode de génération de clés Elgamal fonctionne de la façon suivante :
    //      (a) tirer aléatoirement un premier p à l’aide de la méthode getPrime. Celui-ci est de la forme p = 2p'+1
    //          avec p' premier,
    //      (b) tirer aléatoirement un générateur de (Z/pZ) ∗ d’ordre p' ;
    //      (c) tirer aléatoirement un entier x de [0, p' − 1] ;
    //      (d) calculer h = g^x dans Z/pZ ;
    //      (e) la clef secrète est le couple (p, x), la clef publique est (p, g, h).
    public ArrayList<BigInteger> generateKey(int nbBit) {
        Elgamal el = new Elgamal();
        Random r = new Random();

        // b[0];        p'
        // b[1];        p
        BigInteger[] b = el.getPrime(nbBit, 40, r);
        BigInteger g = new BigInteger(nbBit+100, r).mod(b[1]);

        BigInteger x = el.randNum(b[0], r);         // tirage entre 1 et p-1
        BigInteger h = g.modPow(x, b[1]);

        // clef [0] = private // clef [1] = publique
        // la clef secrète est le couple (p, x), la clef publique est (p, g, h).
        ArrayList<BigInteger> clef = new ArrayList<>();
        clef.add(b[1]);
        clef.add(x);

        clef.add(b[1]);
        clef.add(g);
        clef.add(h);

        // la clef secrète est le couple (p, x), la clef publique est (p, g, h).
        return clef;
    }

    private BigInteger StringToBig(String s) {
        return new BigInteger(s.getBytes());
    }

    private String BigToString(BigInteger b) {
        return new String(b.toByteArray());
    }

    //  Le chiffrement fonctionne de la façon suivante : pour chiffrer m ∈ Z/pZ, tirer r aléatoirement dans
    //  [1, p' − 1], et produire comme chiffré le couple (g^r , m · h^r ), où h est la clé publique.
    //  Comment déchiffre-t-on ?
    public Pair<BigInteger, BigInteger> chiffre(String m, ArrayList<BigInteger> pub) {
        Pair t;
        Elgamal el = new Elgamal();
        Random r = new Random();

        BigInteger x = el.randNum(pub.get(0), r);

//        pub[0] = p
//        pub[1] = g
//        pub[2] = h
//        ICI r est un Random object et x correspond au r qui appartient a Z/pZ
//        t[0] = g^r [p]
//        t[1] = h^r * g^m
        t = new Pair(pub.get(1).modPow(x, pub.get(0)),
                pub.get(2).modPow(x, pub.get(0)).multiply(pub.get(1).modPow(StringToBig(m), pub.get(0))));

        return t;
    }

    public String dechiffre(Pair<BigInteger, BigInteger> c, Pair<BigInteger, BigInteger> prive) {
//        c2/c1^x
        BigInteger m = c.getValue().divide(c.getKey().modPow(prive.getValue(), prive.getKey()));

        return BigToString(m);
    }
}
