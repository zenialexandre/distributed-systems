import java.util.LinkedList;
import java.util.List;

public class Main {

    protected static List<Process> processesList = new LinkedList<>();

    public static void main(String[] args) {
        startSystem();
    }

    private static void startSystem() {
        System.out.println("########## distributed-systems ##########\n");
        ProcessFactory processFactory = new ProcessFactory();
        ProcessEliminator processEliminator = new ProcessEliminator();
        processFactory.start();
        processEliminator.start();
    }

}
