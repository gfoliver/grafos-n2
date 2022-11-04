import java.util.Scanner;

public class ReadsInput {
    protected int code1;
    protected int code2;
    protected String name;
    protected Scanner scanner;

    public ReadsInput() {
        this.code1 = 0;
        this.code2 = 0;
        this.name = "";
        this.scanner = new Scanner(System.in);
    }

    protected void readCodes() {
        code1 = readPositiveInteger("Digite o código do primeiro local: ");
        code2 = readPositiveInteger("Digite o código do segundo local: ");
    }

    protected int readPositiveInteger(String message){
        int value;
        do {
            System.out.println(message);
            while (!scanner.hasNextInt()) {
                System.out.println(message);
                scanner.next();
            }
            value = scanner.nextInt();
        } while (value <= 0);
        return value;
    }

    protected int readInteger(String message){
        int value;
        System.out.println(message);
        while (!scanner.hasNextInt()) {
            System.out.println(message);
            scanner.next();
        }
        value = scanner.nextInt();
        return value;
    }
}
