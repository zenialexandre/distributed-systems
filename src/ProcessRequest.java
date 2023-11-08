import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
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
        final List<Integer> processesIdsThatResponded = new ArrayList<>();
        sendRequests(processRequester, processesIdsThatResponded);
        selectCoordinator(processesIdsThatResponded);
    }

    private void sendRequests(final Process processRequester, final List<Integer> processesIdsThatResponded) {
        for (final Process process : Main.processesList) {
            if (!Objects.deepEquals(process, processRequester) && process.getId() > processRequester.getId()) {
                System.out.println("Process " + processRequester.getId() + " calls process " + process.getId() + " for an election.");
                System.out.println("Process " + process.getId() + " responds with OK.");
                processesIdsThatResponded.add(process.getId());
            } else if (!Objects.deepEquals(process, processRequester) && process.getId() < processRequester.getId()) {
                System.out.println("Process " + processRequester.getId() + " called process " + process.getId() + " for an election, but it has no responses.");
            } else if (Objects.deepEquals(process, processRequester)) {
                System.out.println("Process " + processRequester.getId() + " called himself for an election.");
                processesIdsThatResponded.add(processRequester.getId());
            }
        }
    }

    private void selectCoordinator(final List<Integer> processesIdsThatResponded) {
        final Integer selectedProcessId = Collections.max(processesIdsThatResponded);
        Main.processCoordinatorId = selectedProcessId;
        System.out.println("Process " + selectedProcessId + " now is the coordinator.");
    }

}
