public class Node implements Comparable<Node> {
    private int code;
    private String name;
    private int distance;
    private Node parent;

    public Node(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    @Override
    public int compareTo(Node n) {
        if(this.getDistance() < n.getDistance())
            return -1;
        else if(this.getDistance() == n.getDistance())
            return 0;

        return 1;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    @Override
    public String toString() {
        return "{" + this.getCode() + "}";
    }
}
