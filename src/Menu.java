import java.util.ArrayList;

public class Menu extends ReadsInput {
    private Graph graph;

    public Menu() {
        super();
        this.graph = new Graph();
        this.initGraph();
    }

    private void printOptions(){
        System.out.println("==== Grafos Fapa N2 ====");
        System.out.println("1 - Exibir Grafo");
        System.out.println("2 - Adicionar vértice");
        System.out.println("3 - Remover vértice");
        System.out.println("4 - Adicionar Aresta");
        System.out.println("5 - Remover Aresta");
        System.out.println("6 - Limpar grafo");
        System.out.println("7 - Busca por largura");
        System.out.println("8 - Busca em profundidade");
        System.out.println("9 - Busca Dijkstra");
        System.out.println("0 - Sair");
    }

    public void initGraph(){
        graph.createNode(1, "Vértice 1");
        graph.createNode(2, "Vértice 2");
        graph.createNode(3, "Vértice 3");
        graph.createNode(4, "Vértice 4");
        graph.createNode(5, "Vértice 5");
        graph.createNode(6, "Vértice 6");
        graph.createNode(7, "Vértice 7");
        graph.createNode(8, "Vértice 8");
        graph.createNode(9, "Vértice 9");

        graph.createConnection(1, 2);
        graph.createConnection(1, 3);
        graph.createConnection(2, 4);
        graph.createConnection(3, 4);
        graph.createConnection(3, 5);
        graph.createConnection(4, 6);
        graph.createConnection(5, 7);
        graph.createConnection(5, 8);
        graph.createConnection(6, 9);
        graph.createConnection(7, 9);
    }

    public void render() throws Exception {
        boolean exit = false;

        while (!exit) {
            printOptions();

            int choice = this.readInteger("Escolha a opção: ");

            switch(choice) {
                case 1:
                    System.out.println("- Grafo -");
                    this.graph.print();
                    System.in.read();
                    break;
                case 2:
                    System.out.println("- Criar vértice - ");
                    code1 = readPositiveInteger("Digite o código do vertice: ");
                    System.out.println("Digite o nome do vertice: ");
                    name = scanner.next();
                    boolean created = this.graph.createNode(code1, name);
                    System.out.println(created ? "Vértice criado!" : "Vértice não foi criado, verifique se já existe um vértice com o mesmo código.");
                    System.in.read();
                    break;
                case 3:
                    System.out.println("- Remover vértice - ");
                    code1 = readPositiveInteger("Digite o código do vertice: ");
                    boolean remove = this.graph.removeNode(code1);
                    System.out.println(remove ? "Vértice removido!" : "Vértice não foi removido, verifique se código informado existe.");
                    break;
                case 4:
                    System.out.println("- Adicionar Aresta -");
                    this.readCodes();
                    if(this.graph.adjacentes(code1,code2)){
                        System.out.println("A aresta já existe.");
                        System.in.read();
                        break;
                    }
                    boolean added = this.graph.createConnection(code1, code2);
                    System.out.println(added ? "Aresta adicionada!" : "Algum dos códigos não existe no grafo, verifique e tente novamente.");
                    System.in.read();
                    break;
                case 5:
                    System.out.println("- Remover Aresta -");
                    this.readCodes();
                    boolean removed = this.graph.removeConnection(code1, code2);
                    System.out.println(removed ? "Aresta removida!" : "Aresta não encontrada.");
                    System.in.read();
                    break;

                case 6:
                    System.out.println("- Limpar Grafo -");
                    remove = this.graph.clearGraph();
                    System.out.println(remove ? "Grafo removido!" : "O grafo ja está vazio.");
                    System.in.read();
                    break;
                case 7:
                    System.out.println("- Busca por largura -");
                    this.readCodes();
                    this.graph.BFS(code1, code2);
                    break;
                case 8:
                    System.out.println("- Busca em Profundidade -");
                    this.readCode1();
                    this.graph.DSF(code1);
                    break;
                case 9:
                    System.out.println("- Busca Dijkstra -");
                    this.readCodes();
                    try {
                        ArrayList<Node> path = this.graph.encontrarMenorCaminhoDijkstra(code1, code2);
                        System.out.println(path);
                    }
                    catch(Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 0:
                    exit = true;
                    break;
                default:
                    System.out.println("Não implementado");
                    System.in.read();
                    break;
            }
        }
    }
}