import java.util.ArrayList;

public class Connection {
    public static int emptyValue = 10;

    private ArrayList<Integer> codes;
    private int value;

    public Connection(int code1, int code2) {
        this.codes = new ArrayList<>();
        codes.add(code1);
        codes.add(code2);
        this.value = emptyValue;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public boolean passesThrough(int code) {
        return codes.contains(code);
    }

    public boolean passesThroughDigraph(int code) {
        return codes.get(0).equals(code);
    }

    public boolean verify(int code1, int code2) {
        if (
                (codes.get(0) == code1 && codes.get(1) == code2)
                        || (codes.get(0) == code2 && codes.get(1) == code1)
        ) {
            return true;
        }

        return false;
    }

    public boolean isLoop() {
        return this.codes.get(0) == this.codes.get(1);
    }

    public int otherCode(int code) {
        if (isLoop())
            return code;

        for (int c : codes)
            if (code != c)
                return c;

        return -1;
    }

    @Override
    public String toString() {
        return this.codes.get(0) + "-" + this.codes.get(1);
    }

    public int getCode(int index) {
        return codes.get(index);
    }
}