import java.security.SecureRandom;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ProcessFactory implements Runnable {

    private final List<Process> processesList = new LinkedList<>();
    private final ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);

    public void start() {
        scheduledExecutorService.scheduleAtFixedRate(this, 0, 30, TimeUnit.SECONDS);
    }

    public void stop() {
        scheduledExecutorService.shutdown();
    }

    public void run() {
        System.out.println("New process being created now...\n");
        final Process process = new Process(getRandomizedId());
        processesList.add(process);
        System.out.println("Process created: " + processesList.get(processesList.size() - 1).getId() + "\n");
        displayProcesses();
    }

    private Integer getRandomizedId() {
        return new SecureRandom().nextInt(1000);
    }

    private void displayProcesses() {
        final StringBuilder processesDisplayed = new StringBuilder("System current processes: ");

        for (Process process : processesList) {
            processesDisplayed.append(process.getId()).append(" / ");
        }
        System.out.println(processesDisplayed + "\n");
    }

}
