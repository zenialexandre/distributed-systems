import java.security.SecureRandom;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ProcessFactory implements Runnable {

    private final Utils utils = new Utils();
    private final ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);

    public ProcessFactory() {}

    public void start() {
        scheduledExecutorService.scheduleAtFixedRate(this, 0, 30, TimeUnit.SECONDS);
    }

    public void stop() {
        scheduledExecutorService.shutdown();
    }

    public void run() {
        try {
            System.out.println("#######################\n");
            System.out.println("New process being created now...\n");
            final Process process = new Process(getRandomizedId(), true);
            Main.processesList.add(process);
            System.out.println("Process created: " + Main.processesList.get(Main.processesList.size() - 1).getId() + "\n");
            displayProcesses();
            System.out.println("#######################\n");
        } catch (final Exception exception) {
            utils.defaultErrorCatch(exception);
        }
    }

    private Integer getRandomizedId() {
        return new SecureRandom().nextInt(1000);
    }

    private void displayProcesses() {
        final StringBuilder processesDisplayed = new StringBuilder("System current processes: ");

        for (Process process : Main.processesList) {
            processesDisplayed.append(process.getId()).append(" / ");
        }
        System.out.println(processesDisplayed + "\n");
    }

}
