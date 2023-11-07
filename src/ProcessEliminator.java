import java.security.SecureRandom;
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
            System.out.println("#######################\n");
            System.out.println("Process being inactivated now...\n");
            final Process choosedProcess = getProcessRandomly();
            choosedProcess.setIsSituationActive(false);
            System.out.println("Process inactivated: " + choosedProcess.getId() + "\n");
            System.out.println("#######################\n");
        } catch (final Exception exception) {
            utils.defaultErrorCatch(exception);
        }
    }

    private Process getProcessRandomly() {
        return Main.processesList.get(new SecureRandom().nextInt(Main.processesList.size()));
    }

}
