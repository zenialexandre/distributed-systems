import java.security.SecureRandom;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;

public class ProcessResourceConsumption {

    private final ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    public ProcessResourceConsumption() {}

    protected void consumeResource(final Process consumerProcess) {
        System.out.println("#######################\n");
        System.out.println("An consumption task is starting now...");
        System.out.println("Process " + consumerProcess.getId() + " is starting to consume the resource.\n");
        final Runnable consumptionTask = () -> consumptionTaskExecution(consumerProcess);
        final Future<?> future = executorService.submit(consumptionTask);

        try {
            future.get();
        } catch (InterruptedException | ExecutionException exception) {
            exception.printStackTrace();
        }

        System.out.println("#######################\n");
    }

    private void consumptionTaskExecution(final Process consumerProcess) {
        try {
            Thread.sleep(new SecureRandom().nextInt(15000 - 5000) + 5000);
            System.out.println("Consumption completed.");
            System.out.println("Process " + consumerProcess.getId() + " will be removed from the queue.");
            Main.processesWaitingQueue.remove(consumerProcess);
        } catch (final InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
    }

}
