import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class CoordinatorEliminator implements Runnable {

    private final Utils utils = new Utils();
    private final ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);

    public void start() {
        scheduledExecutorService.scheduleAtFixedRate(this, 100, 100, TimeUnit.SECONDS);
    }

    public void run() {
        try {
            if (Main.processCoordinatorId != null) {
                eliminateCoordinator();
            }
        } catch (final Exception exception) {
            utils.defaultErrorCatch(exception);
        }
    }

    private void eliminateCoordinator() {
        System.out.println("#######################\n");
        System.out.println("Coordinator being inactivated now...");
        System.out.println("Coordinator inactivated: " + Main.processesList
                .remove(Main.processesList.indexOf(getProcessCoordinator())).getId() + "\n");
        Main.processCoordinatorId = null;
        System.out.println("#######################\n");
    }

    private Process getProcessCoordinator() {
        return Main.processesList.stream().filter(element -> element.getId().equals(Main.processCoordinatorId)).findFirst().orElse(null);
    }

}
