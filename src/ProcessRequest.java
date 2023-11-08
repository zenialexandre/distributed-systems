import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ProcessRequest implements Runnable {

    private final Utils utils = new Utils();
    private final ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);

    public ProcessRequest() {}

    public void start() {
        scheduledExecutorService.scheduleAtFixedRate(this, 0, 25, TimeUnit.SECONDS);
    }

    public void stop() {
        scheduledExecutorService.shutdown();
    }

    public void run() {
        try {
            if (!Main.processesList.isEmpty()) {
                executeRequest();
            }
        } catch (final Exception exception) {
            utils.defaultErrorCatch(exception);
        }
    }

    private void executeRequest() {
        final Process processRequester = utils.getProcessRandomly();

        if (Main.processCoordinatorId == null) {
            System.out.println("#######################\n");
            System.out.println("An election has started...");
            bullyElection(processRequester);
            System.out.println("Process that initialized the election: "
                    + processRequester.getId() + " - Process that won: " + Main.processCoordinatorId + "\n");
            System.out.println("#######################\n");
        }
    }

    private void bullyElection(final Process processRequester) {
        for (final Process process : Main.processesList) {
            if (process != processRequester && process.getId() > processRequester.getId()) {
                System.out.println("Process " + processRequester.getId() + " calls process " + process.getId() + " for an election.");
                System.out.println("Process " + process.getId() + " responds with OK.");
                Main.processCoordinatorId = process.getId();
                System.out.println("Process " + process.getId() + " now is the coordinator.");
            } else {
                System.out.println("Process " + processRequester.getId() + " called for an election, but no one responded.");
                Main.processCoordinatorId = processRequester.getId();
                System.out.println("Process " + processRequester.getId() + " now is the coordinator.");
            }
        }
    }

}
