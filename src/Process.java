public class Process {

    private Integer id;
    private boolean isSituationActive;

    public Process(final Integer id, final boolean isSituationActive) {
        setId(id);
        setIsSituationActive(isSituationActive);
    }

    protected void setId(final Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    protected void setIsSituationActive(final boolean isSituationActive) {
        this.isSituationActive = isSituationActive;
    }

    public boolean getIsSituationActive() {
        return isSituationActive;
    }

}
