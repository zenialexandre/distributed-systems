import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ProcessEliminator implements Runnable {

    private final Utils utils = new Utils();
    private final ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);

    public ProcessEliminator() {}

    public void start() {
        scheduledExecutorService.scheduleAtFixedRate(this, 80, 80, TimeUnit.SECONDS);
    }

    public void run() {
        try {
            eliminateProcess();
        } catch (final Exception exception) {
            utils.defaultErrorCatch(exception);
        }
    }

    private void eliminateProcess() {
        System.out.println("#######################\n");
        System.out.println("Process being inactivated now...");
        final Process processToInactive  = utils.getProcessRandomly();
        System.out.println("Process inactivated: " + Main.processesList
                .remove(Main.processesList.indexOf(processToInactive)).getId() + "\n");
        Main.processesWaitingQueue.remove(processToInactive);
        validateCoordinatorProcess(processToInactive);
        System.out.println("#######################\n");
    }

    private void validateCoordinatorProcess(final Process processToInactive) {
        if (Objects.equals(processToInactive.getId(), Main.coordinatorProcessId)) {
            Main.coordinatorProcessId = null;
            Main.processesWaitingQueue.clear();
            System.out.println("And it was the coordinator.\n");
        }
    }

}
