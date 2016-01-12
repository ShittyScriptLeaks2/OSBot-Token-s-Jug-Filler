public class OpenBankState extends State {

    public OpenBankState(Core a) {
        super(a);
    }

    @Override
    public boolean execute() throws InterruptedException {
        final boolean b = true;
        this.core.bank.open();
        return b;
    }

    @Override
    public boolean readyForExecution() throws InterruptedException {
        return Constants.BANK_AREA.contains(this.core.myPlayer()) && !this.core.bank.isOpen() && !this.core.inventory.contains("Jug");
    }

    @Override
    public long getDebugTimeout() throws InterruptedException {
        return 10L;
    }

    @Override
    public String textual() {
        return "Opening Bank";
    }
}
