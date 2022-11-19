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

    public ArrayList<Integer> adjacenteDigraph(int code) {
        ArrayList<Integer> adj = new ArrayList<>();

        for (Connection c : connections) {
            if (c.passesThroughDigraph(code))
                adj.add(c.otherCode(code));
        }

        return adj;
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

    public void DSF(int code1) throws Exception{
        // Lista de codes já visitados durante a execução recursiva
        ArrayList<Integer> visited = new ArrayList<>();

        // Lista que guarda o vertice e ordem de acesso
        ArrayList<String> accessMap = new ArrayList<>();

        // Variavel que guarda a ordem de acesso
        int valency = 1;

        // Adiciona o ponto de partida na lista de visitados e no caminho
        visited.add(code1);
        Node current = this.byCode(code1);
        accessMap.add(current.getName() + " - Código: " + current.getCode() + " - Grau de acesso: " + valency);

        // Inicia a busca em profundidade a partir do código do vértice informado
        this.DepthFirstSearch(visited, accessMap, current, valency +1);

        // Função que ordena os acessos, somente para melhor visualização
        Collections.sort(accessMap);

        // Imprime resultado da busca em profundidade
        for (String a : accessMap){
            System.out.println(a);
        }
    }

    public void DepthFirstSearch (ArrayList<Integer> visited, ArrayList<String> accessMap, Node node, int valency) throws Exception{
        for (int a : this.adjacenteDigraph(node.getCode())) {
            // Verifica se o vertice atual ja foi visitado para evitar repetições
            if (! visited.contains(a)) {
                visited.add(a);
                // Buscar local pelo código para obter o name
                Node l = this.byCode(a);
                // Adiciona name ao caminho
                accessMap.add(l.getName() + " - Código: " +  l.getCode() + " - Grau de acesso: " + valency);

                // Continua busca recursiva nos adjacentes ao atual
                this.DepthFirstSearch(visited, accessMap, l,valency+1);
            }
        }
    }

    public void BFS(int code1, int code2){
        boolean temCaminho = false;
        ArrayList<Integer> caminhoPercorrido = new ArrayList<Integer>();
    
        if(code1 == code2){
            temCaminho = true;
        }
    
        int tamanho = nodes.size();
        Boolean visited[] = new Boolean[tamanho];
        LinkedList<Integer> q = new LinkedList<Integer>();

        visited[code1] = true;
        q.add(code1);
        caminhoPercorrido.add(code1);
    
        while(q.size() > 0){
            code1 = q.poll();
    
            for(int a : this.adjacentes(code1)) {
                if(a == code2){
                    temCaminho = true;
                    caminhoPercorrido.add(a);
                    break;
                }
                if(visited[a] == null ||!visited[a]){
                    q.add(a);
                    visited[a] = true;
                    caminhoPercorrido.add(a);
                }
            }
        }
        if(temCaminho){
            boolean fim = false;
            int i = caminhoPercorrido.size()-1;
            System.out.printf("\nExiste um caminho entre %d e %d",caminhoPercorrido.get(0),code2);
    
            while(fim == false){
                if(caminhoPercorrido.get(i) == code2){
                    fim = true;
                }
                else{
                    caminhoPercorrido.remove(i);
                }
                i--;
            }
    
            System.out.println(caminhoPercorrido);
        }
    }

}
