public class Main {

    public static void main(String[] args) {
        startSystem();
    }

    private static void startSystem() {
        System.out.println("########## distributed-systems ##########\n");
        ProcessFactory processFactory = new ProcessFactory();
        processFactory.start();
    }

}
