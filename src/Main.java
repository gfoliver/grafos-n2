public class Main {

    public static void main(String[] args) {
	    Menu menu = new Menu();
	    try {
            menu.render();
        }
	    catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
