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

    public void stop() {
        scheduledExecutorService.shutdown();
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
        System.out.println("Process inactivated: " + Main.processesList.remove(Main.processesList.indexOf(utils.getProcessRandomly())).getId() + "\n");
        System.out.println("#######################\n");
    }

}
