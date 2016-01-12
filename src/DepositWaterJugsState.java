public class DepositWaterJugsState extends State {

    public DepositWaterJugsState(Core a) {
        super(a);
    }

    @Override
    public long getDebugTimeout() throws InterruptedException {
        return 10L;
    }

    @Override
    public String textual() {
        return "Depositing Items";
    }

    @Override
    public boolean readyForExecution() throws InterruptedException {
        return this.core.bank.isOpen() && this.core.inventory.contains("Jug of water");
    }

    @Override
    public boolean execute() throws InterruptedException {
        this.core.bank.depositAll();
        return true;
    }

}
