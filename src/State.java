public abstract class State {
    public Core core;

    public State(Core a) {
        this.core = a;
    }

    public String textual() {
        return "";
    }

    public abstract boolean execute() throws InterruptedException;

    public abstract long getDebugTimeout() throws InterruptedException;

    public abstract boolean readyForExecution() throws InterruptedException;

}
