import java.security.SecureRandom;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ProcessResourceRequest implements Runnable {

    private final Utils utils = new Utils();
    private final ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);

    public void start() {
        scheduledExecutorService.scheduleAtFixedRate(this, 25,
                new SecureRandom().nextInt(25 - 10) + 10, TimeUnit.SECONDS);
    }

    public void run() {
        try {
            requestResource();
        } catch (final Exception exception) {
            utils.defaultErrorCatch(exception);
        }
    }

    private void requestResource() {
        if (Objects.nonNull(Main.coordinatorProcessId)) {
            final Process requesterProcess = utils.getProcessRandomlyCheckingCoordinator();

            System.out.println("#######################\n");
            System.out.println("An request for resource consumption has started...");
            System.out.println("Process " + requesterProcess.getId() + " is sending a request for the coordinator " + Main.coordinatorProcessId + "\n");
            handleResourceRequest(requesterProcess);
            System.out.println("#######################\n");
        }
    }

    private void handleResourceRequest(final Process requesterProcess) {
        if (Main.processesWaitingQueue.isEmpty()) {
            System.out.println("The queue for resource consumption is empty.");
            System.out.println("Process " + requesterProcess.getId() + " getting confirmation to allocate resource.");
        } else {
            System.out.println("The queue for resource consumption isn't empty.");
            System.out.println("The queue has " + Main.processesWaitingQueue.size() + " processes waiting.");
            Main.processesWaitingQueue.add(requesterProcess);
            System.out.println("Process " + requesterProcess.getId() + " added to the queue.");
        }
    }

}
