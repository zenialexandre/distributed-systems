public class Process {

    private Integer id;

    public Process(final Integer id) {
        setId(id);
    }

    protected void setId(final Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

}
