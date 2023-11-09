import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ProcessResourceConsumption {

    private final Utils utils = new Utils();
    private final ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(1);
    private Process resourceConsumingProcess;
    private Boolean isConsumptionTaskRunning = false;

    public ProcessResourceConsumption() {}

    protected void consumeResource(final Process consumerProcess) {
        try {
            System.out.println("#######################\n");
            System.out.println("An consumption task is starting now...");
            System.out.println("Process " + consumerProcess.getId() + " is starting to consume the resource.\n");
            setIsConsumptionTaskRunning(true);
            setResourceConsumingProcess(consumerProcess);
            executeConsumptionTask(consumerProcess);
            System.out.println("#######################\n");
        } catch (final Exception exception) {
            utils.defaultErrorCatch(exception);
        }
    }

    private void executeConsumptionTask(final Process consumerProcess) {
        scheduledThreadPoolExecutor.schedule(() -> {
            try {
                setIsConsumptionTaskRunning(false);
                System.out.println("Consumption completed.");
                System.out.println("Process " + consumerProcess.getId() + " will be removed from the queue.\n");
                Main.processesWaitingQueue.remove(consumerProcess);
            } catch (final Exception exception) {
                utils.defaultErrorCatch(exception);
            }
        }, 100, TimeUnit.SECONDS);
    }

    private void setResourceConsumingProcess(final Process resourceConsumingProcess) {
        this.resourceConsumingProcess = resourceConsumingProcess;
    }

    public Process getResourceConsumingProcess() {
        return resourceConsumingProcess;
    }

    private void setIsConsumptionTaskRunning(final Boolean isConsumptionTaskRunning) {
        this.isConsumptionTaskRunning = isConsumptionTaskRunning;
    }

    public Boolean getIsConsumptionTaskRunning() {
        return isConsumptionTaskRunning;
    }

}
