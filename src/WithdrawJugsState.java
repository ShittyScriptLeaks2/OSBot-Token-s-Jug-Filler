public class WithdrawJugsState extends State {

    public WithdrawJugsState(Core a) {
        super(a);
    }

    @Override
    public boolean readyForExecution() throws InterruptedException {
        return this.core.inventory.isEmpty() && this.core.bank.isOpen();
    }

    @Override
    public long getDebugTimeout() throws InterruptedException {
        return 10L;
    }

    @Override
    public String textual() {
        return "WithdrawJugsState";
    }

    @Override
    public boolean execute() throws InterruptedException {
        if (!this.core.bank.contains("Jug")) {
            this.core.stop();
        }

        this.core.bank.withdraw("Jug", 28);
        return true;
    }

}
