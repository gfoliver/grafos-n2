import java.util.*;

public class Graph {
    private ArrayList<Node> nodes;
    private ArrayList<Connection> connections;

    public Graph() {
        this.nodes = new ArrayList<>();
        this.connections = new ArrayList<>();
    }

    public boolean nodeExists(int code) {
        for (Node n : nodes) {
            if (n.getCode() == code)
                return true;
        }

        return false;
    }

    public boolean connectionExists(int code1, int code2) {
        for (Connection c : connections) {
            if (c.verify(code1, code2)){
                return true;
            }
        }

        return false;
    }

    public boolean createNode(int code, String name) {
        if (nodeExists(code) || code <= 0)
            return false;

        this.nodes.add(new Node(code, name));

        return true;
    }

    public boolean clearGraph(){
        if(nodes.size() == 0)
            return false;

        nodes = new ArrayList<>();
        connections = new ArrayList<>();
        return true;
    }

    public boolean removeNode(int code) throws Exception {
        if (!nodeExists(code))
            return false;

        this.removeConnections(code);
        this.nodes.remove(this.byCode(code));

        return true;
    }

    public Node byCode(int code) throws Exception {
        for (Node l : nodes) {
            if (l.getCode() == code)
                return l;
        }

        throw new Exception("Código não encontrado");
    }

    public boolean createConnection(int code1, int code2) {
        if (!nodeExists(code1) || !nodeExists(code2))
            return false;

        this.connections.add( new Connection(code1, code2) );

        return true;
    }

    public boolean removeConnection(int code1, int code2) {
        if (!nodeExists(code1) || !nodeExists(code2))
            return false;

        connections.removeIf(c -> c.passesThrough(code1) && c.passesThrough(code2));

        return true;
    }

    public int valueByConnection(int code1, int code2) {
        for (Connection c : connections) {
            if (c.verify(code1, code2)){
                return c.getValue();
            }
        }

        return 0;
    }

    public boolean removeConnections(int code) {
        if (!nodeExists(code))
            return false;

        for (Node n: nodes)
            connections.removeIf(c -> c.passesThrough(n.getCode()) && c.passesThrough(code));

        return true;
    }

    public ArrayList<Node> getNodes() {
        return nodes;
    }

    public boolean adjacentes(int code1, int code2) {
        for (Connection c : connections) {
            if (c.verify(code1, code2)) {
                return true;
            }
        }

        return false;
    }

    public boolean hasLoop(int code) {
        for (Connection c : connections) {
            if (c.passesThrough(code) && c.isLoop())
                return true;
        }

        return false;
    }

    public ArrayList<Integer> adjacentes(int code) {
        ArrayList<Integer> adj = new ArrayList<>();

        for (Connection c : connections) {
            if (c.passesThrough(code))
                adj.add(c.otherCode(code));
        }

        return adj;
    }

    public void printPath(int code1, int code2) throws Exception {
        // Lista de codes já visitados durante a execução recursiva
        ArrayList<Integer> visitados = new ArrayList<>();

        // Lista que guarda o caminho
        ArrayList<String> caminho = new ArrayList<>();

        // Adiciona o ponto de partida na lista de visitados e no caminho
        visitados.add(code1);
        Node current = this.byCode(code1);
        caminho.add(current.getName());

        // Inicia busca recursiva
        recursivePrintPath(code1, code2, visitados, caminho);
    }

    public void recursivePrintPath(int code1, int code2, ArrayList<Integer> visitados, ArrayList<String> caminho) throws Exception
    {
        // Verifica se o code foi encontrado e printa o caminho
        if (code1 == code2) {
            System.out.println(caminho);
            return;
        }

        // Adiciona o code atual aos visitados para evitar repetições
        visitados.add(code1);

        // Itera pelos adjacentes do vertice atual
        for (int a : this.adjacentes(code1)) {
            // Verifica se o vertice atual ja foi visitado para evitar repetições
            if (! visitados.contains(a)) {
                // Buscar local pelo código para obter o name
                Node l = this.byCode(a);
                // Adiciona name ao caminho
                caminho.add(l.getName());

                // Continua busca recursiva nos adjacentes ao atual
                recursivePrintPath(a, code2, visitados, caminho);

                // Remove o atual do caminho, pois não encontrou o destino nos seus adjacentes
                caminho.remove(l.getName());
            }
        }
    }

    public void printNodes() {
        for (Node n : this.nodes) {
            System.out.println(
                    n.getName() + " (código=" + n.getCode() + ")"
            );
        }
    }

    public ArrayList<Connection> connectionsByNode(int code) {
        ArrayList<Connection> cs = (ArrayList) connections.clone();
        cs.removeIf(c -> ! c.passesThrough(code));
        return cs;
    }

    public void print() {
        for (Node l : this.nodes) {
            System.out.println(
                    l.getName()
                            + " (código=" + l.getCode() + ")"
                            + " - " + connectionsToString(connectionsByNode(l.getCode()), l.getCode())
            );
        }
    }

    private String connectionsToString(ArrayList<Connection> cs, int code) {
        String ret = "[";

        int size = cs.size();

        for (int i = 0; i < size; i++) {
            ret += cs.get(i).otherCode(code);

            if (i < size - 1)
                ret += ",";
        }

        ret += "]";

        return ret;
    }

    public void printConnetions() {
        for (Connection c : connections) {
            String str = c.toString();

            if (c.getValue() != Connection.emptyValue) {
                str += " Valor=" + c.getValue();
            }

            System.out.println(str);
        }
    }
}
