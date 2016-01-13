public class OpenBankState extends State {

    public OpenBankState(Core a) {
        super(a);
    }

    @Override
    public String textual() {
        return "Opening Bank";
    }

    @Override
    public boolean readyForExecution() throws InterruptedException {
        return Constants.BANK_AREA.contains(core.myPlayer()) && !core.bank.isOpen() && !core.inventory.contains("Jug");
    }

    @Override
    public boolean execute() throws InterruptedException {
        core.bank.open();
        return true;
    }

    @Override
    public long getDebugTimeout() throws InterruptedException {
        return 10L;
    }

}
