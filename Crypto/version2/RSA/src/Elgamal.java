/**
 * Antoine Roux p1409394
 * Clement Ricotta p1201800
 */

import java.math.BigInteger;
import java.util.Random;

public class Elgamal {

    /**
     * get a primary number
     * @param nb_bits
     * @param certainty
     * @param prg
     * @return
     */
    BigInteger[] getPrime(int nb_bits, int certainty, Random prg) {
        BigInteger[] tab = new BigInteger[2];

        do {
            BigInteger b = new BigInteger(nb_bits, certainty, prg);

            tab[0] = b;             // p'
            tab[1] = b.multiply(new BigInteger(String.valueOf(2))).add(BigInteger.ONE);  // p
        }while (!tab[0].multiply(new BigInteger(String.valueOf(2))).add(BigInteger.ONE).isProbablePrime(certainty));

        return tab;
    }

    /*
        get the order of g modulus p
     */
    BigInteger ordre(BigInteger g, BigInteger p) {
        BigInteger res = BigInteger.ZERO;
        BigInteger ordre = BigInteger.ONE;
        while(!res.equals(BigInteger.ONE)) {
            ordre = ordre.add(BigInteger.ONE);
            res = g.modPow(ordre, p);
        }
        return ordre;
    }

    int ordre_p(BigInteger v) {
        int oP =0;
        for(BigInteger i = BigInteger.ONE; i.compareTo(v.add(BigInteger.ONE)) == -1; i=i.add(BigInteger.ONE)) {
            if(v.gcd(i).equals(BigInteger.ONE)) {
                oP++;
            }
        }
        return oP;
    }

    BigInteger randNum(BigInteger N, Random prg) {
        int nlen = N.bitLength();
        BigInteger nm1 = N.subtract(BigInteger.ONE);
        BigInteger r, s;
        do {
            s = new BigInteger(nlen + 100, prg);
            r = s.mod(N);
        } while (s.subtract(r).add(nm1).bitLength() >= nlen + 100);

        return r;
    }
}
