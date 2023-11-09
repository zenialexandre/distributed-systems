import java.security.SecureRandom;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class Utils {

    public Utils() {}

    protected void defaultErrorCatch(final Exception exception) {
        System.out.println("#######################\n");
        System.out.println("ERROR: " + exception.getMessage());
        System.out.println("#######################\n");
    }

    protected Process getProcessRandomly() {
        return Main.processesList.get(new SecureRandom().nextInt(Main.processesList.size()));
    }

    protected Process getProcessRandomlyCheckingCoordinator() {
        final List<Process> processesExcludingCoordinator = new LinkedList<>();

        Main.processesList.forEach(process -> {
            if (!Objects.deepEquals(process.getId(), Main.coordinatorProcessId)) {
                processesExcludingCoordinator.add(process);
            }
        });
        return processesExcludingCoordinator.get(new SecureRandom().nextInt(processesExcludingCoordinator.size()));
    }

}
