import java.util.LinkedList;
import java.util.List;

public class Main {

    protected static Integer processCoordinatorId;
    protected static List<Process> processesList = new LinkedList<>();

    public static void main(String[] args) {
        startSystem();
    }

    private static void startSystem() {
        System.out.println("########## distributed-systems ##########\n");
        final ProcessFactory processFactory = new ProcessFactory();
        final ProcessRequest processRequest = new ProcessRequest();
        final ProcessEliminator processEliminator = new ProcessEliminator();
        final CoordinatorEliminator coordinatorEliminator =  new CoordinatorEliminator();
        processFactory.start();
        processRequest.start();
        processEliminator.start();
        coordinatorEliminator.start();
    }

}
