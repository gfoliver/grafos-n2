import java.util.ArrayList;

public class Menu extends ReadsInput {
    private Graph graph;

    public Menu() {
        super();
        this.graph = new Graph();
    }

    private void printOptions(){
        System.out.println("==== Grafos Fapa N2 ====");
        System.out.println("1 - Exibir Grafo");
        System.out.println("2 - Adicionar vértice");
        System.out.println("3 - Remover vértice");
        System.out.println("4 - Adicionar Aresta");
        System.out.println("5 - Remover Aresta");
        System.out.println("6 - Limpar grafo");
        System.out.println("0 - Sair");
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