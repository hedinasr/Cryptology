package key;

import java.math.BigInteger;
import java.util.List;

public class Key {

    protected List<BigInteger> key;

    public Key(List<BigInteger> key) {
        this.key = key;
    }

    public List<BigInteger> getKey() {
        return key;
    }

    public void setKey(List<BigInteger> key) {
        this.key = key;
    }
}
