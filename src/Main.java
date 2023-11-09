import java.util.LinkedList;
import java.util.List;

public class Main {

    protected static Integer coordinatorProcessId;
    protected static List<Process> processesList = new LinkedList<>();
    protected static List<Process> processesWaitingQueue = new LinkedList<>();

    public static void main(String[] args) {
        startSystem();
    }

    private static void startSystem() {
        System.out.println("########## distributed-systems ##########\n");
        final ProcessFactory processFactory = new ProcessFactory();
        final ProcessElectionRequest processElectionRequest = new ProcessElectionRequest();
        final ProcessResourceRequest processResourceRequest = new ProcessResourceRequest();
        final ProcessEliminator processEliminator = new ProcessEliminator();
        final CoordinatorEliminator coordinatorEliminator =  new CoordinatorEliminator();
        processFactory.start();
        processElectionRequest.start();
        processResourceRequest.start();
        processEliminator.start();
        coordinatorEliminator.start();
    }

}
