import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ProcessResourceRequest implements Runnable {

    private final Utils utils = new Utils();
    private final ProcessResourceConsumption processResourceConsumption =  new ProcessResourceConsumption();
    private final ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);

    public void start() {
        scheduledExecutorService.scheduleAtFixedRate(this, 40, 15, TimeUnit.SECONDS);
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
        if (Main.processesWaitingQueue.isEmpty() && !processResourceConsumption.getIsConsumptionTaskRunning()
                && !Objects.deepEquals(requesterProcess, processResourceConsumption.getResourceConsumingProcess())) {
            System.out.println("The queue for resource consumption is empty.");
            System.out.println("Process " + requesterProcess.getId() + " getting confirmation to allocate resource.\n");
            processResourceConsumption.consumeResource(requesterProcess);
        } else if (!Main.processesWaitingQueue.contains(requesterProcess)
            && !Objects.deepEquals(requesterProcess, processResourceConsumption.getResourceConsumingProcess())) {
            System.out.println("The queue for resource consumption isn't empty or some process is already consuming.");
            System.out.println("The queue has " + Main.processesWaitingQueue.size() + " processes waiting.\n");
            Main.processesWaitingQueue.offer(requesterProcess);
            System.out.println("Process " + requesterProcess.getId() + " added to the queue.\n");
        } else if (!Main.processesWaitingQueue.contains(requesterProcess)
            && Objects.deepEquals(requesterProcess, processResourceConsumption.getResourceConsumingProcess())) {
            System.out.println("Process " + requesterProcess.getId() + " that made the request is already consuming the resource.\n");
        } else {
            handleProcessAlreadyOnQueue(requesterProcess);
        }
    }

    private void handleProcessAlreadyOnQueue(final Process requesterProcess) {
        if (Objects.deepEquals(Main.processesWaitingQueue.getFirst(), requesterProcess)
                && !processResourceConsumption.getIsConsumptionTaskRunning()
                && !Objects.deepEquals(requesterProcess, processResourceConsumption.getResourceConsumingProcess())) {
            System.out.println("Process now is the first of the queue.");
            System.out.println("Process " + requesterProcess.getId() + " getting confirmation to allocate resource.\n");
            processResourceConsumption.consumeResource(requesterProcess);
        } else {
            System.out.println("Process isn't the first of the queue yet.");
            System.out.println("Process " + requesterProcess.getId() + " need to wait for the other processes.\n");
        }
    }

}
