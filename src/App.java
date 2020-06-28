import Rechner.Rechner;

public class App {
    public static void main(String[] args) {
        Rechner r = new Rechner();

        try {
            r.add(5);
            r.add(5);
            r.undo();
            r.subtract(1);
            r.add(5);
            r.undo();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        System.out.println("Rechner Status: " + r.get());
    }
}
