public class WithdrawJugsState extends State {

    public WithdrawJugsState(Core a) {
        super(a);
    }

    @Override
    public String textual() {
        return "WithdrawJugsState";
    }

    @Override
    public boolean readyForExecution() throws InterruptedException {
        return core.inventory.isEmpty() && core.bank.isOpen();
    }

    @Override
    public boolean execute() throws InterruptedException {
        if (!core.bank.contains("Jug")) {
            core.stop();
        }

        core.bank.withdraw("Jug", 28);
        return true;
    }

    @Override
    public long getDebugTimeout() throws InterruptedException {
        return 10L;
    }

}
