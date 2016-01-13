public class DepositWaterJugsState extends State {

    public DepositWaterJugsState(Core a) {
        super(a);
    }

    @Override
    public String textual() {
        return "Depositing Items";
    }

    @Override
    public boolean readyForExecution() throws InterruptedException {
        return core.bank.isOpen() && core.inventory.contains("Jug of water");
    }

    @Override
    public boolean execute() throws InterruptedException {
        core.bank.depositAll();
        return true;
    }

    @Override
    public long getDebugTimeout() throws InterruptedException {
        return 10L;
    }

}
