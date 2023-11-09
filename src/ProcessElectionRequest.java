import java.util.List;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Collections;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ProcessElectionRequest implements Runnable {

    private final Utils utils = new Utils();
    private final ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
    private Boolean isElectionRunning = false;

    public ProcessElectionRequest() {}

    public void start() {
        scheduledExecutorService.scheduleAtFixedRate(this, 0, 25, TimeUnit.SECONDS);
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
        final Process requesterProcess = utils.getProcessRandomly();

        if (Objects.nonNull(Main.coordinatorProcessId)) {
            System.out.println("#######################\n");
            System.out.println("An election has started...");
            bullyElection(requesterProcess);
            System.out.println("Process that initialized the election: "
                    + requesterProcess.getId() + " - Process that won: " + Main.coordinatorProcessId + "\n");
            System.out.println("#######################\n");
        }
    }

    private void bullyElection(final Process requesterProcess) {
        final List<Integer> processesIdsThatResponded = new LinkedList<>();
        establishElection(requesterProcess, processesIdsThatResponded);
        selectCoordinator(processesIdsThatResponded);
    }

    private void establishElection(final Process requesterProcess, final List<Integer> processesIdsThatResponded) {
        if (!getIsElectionRunning()) {
            setIsElectionRunning(true);
            sendRequests(requesterProcess, processesIdsThatResponded);
        } else {
            System.out.println("An election was trying to get in place, but it doesn't worked.");
        }
    }

    private void sendRequests(final Process requesterProcess, final List<Integer> processesIdsThatResponded) {
        for (final Process process : Main.processesList) {
            if (!Objects.deepEquals(process, requesterProcess) && process.getId() > requesterProcess.getId()) {
                System.out.println("Process " + requesterProcess.getId() + " calls process "
                        + process.getId() + " for an election.");
                System.out.println("Process " + process.getId() + " responds with OK.");
                processesIdsThatResponded.add(process.getId());
            } else if (!Objects.deepEquals(process, requesterProcess) && process.getId() < requesterProcess.getId()) {
                System.out.println("Process " + requesterProcess.getId() + " called process " + process.getId()
                        + " for an election, but it has no responses.");
            } else if (Objects.deepEquals(process, requesterProcess)) {
                System.out.println("Process " + requesterProcess.getId() + " called himself for an election.");
                processesIdsThatResponded.add(requesterProcess.getId());
            }
        }
    }

    private void selectCoordinator(final List<Integer> processesIdsThatResponded) {
        final Integer selectedProcessId = Collections.max(processesIdsThatResponded);
        Main.coordinatorProcessId = selectedProcessId;
        System.out.println("Process " + selectedProcessId + " now is the coordinator.");
        setIsElectionRunning(false);
    }

    private void setIsElectionRunning(final Boolean isElectionRunning) {
        this.isElectionRunning = isElectionRunning;
    }

    private Boolean getIsElectionRunning() {
        return isElectionRunning;
    }

}
