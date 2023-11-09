import java.util.concurrent.*;

public class ProcessResourceConsumption {

    private final Utils utils = new Utils();
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    public ProcessResourceConsumption() {}

    protected void consumeResource(final Process consumerProcess) {
        try {
            System.out.println("#######################\n");
            System.out.println("An consumption task is starting now...");
            System.out.println("Process " + consumerProcess.getId() + " is starting to consume the resource.\n");
            final Runnable consumptionTask = () -> consumptionTaskExecution(consumerProcess);
            final Future<?> future = executorService.submit(consumptionTask);
            future.get();
            System.out.println("#######################\n");
        } catch (final InterruptedException | ExecutionException exception) {
            utils.defaultErrorCatch(exception);
        }
    }

    private void consumptionTaskExecution(final Process consumerProcess) {
        try {
            TimeUnit.SECONDS.sleep(100);
            System.out.println("Consumption completed.");
            System.out.println("Process " + consumerProcess.getId() + " will be removed from the queue.\n");
            Main.processesWaitingQueue.remove(consumerProcess);
        } catch (final InterruptedException interruptedException) {
            utils.defaultErrorCatch(interruptedException);
        }
    }

}
