public class Utils {

    public Utils() {}

    protected void defaultErrorCatch(final Exception exception) {
        System.out.println("#######################\n");
        System.out.println("ERROR: " + exception.getMessage());
        System.out.println("#######################\n");
    }

}
