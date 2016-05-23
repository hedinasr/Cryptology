package key;

import java.math.BigInteger;
import java.util.List;

public abstract class Key {

    protected List<BigInteger> key;

    public List<BigInteger> getKey() {
        return key;
    }

    public void setKey(List<BigInteger> key) {
        this.key = key;
    }
}
