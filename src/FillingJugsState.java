public class FillingJugsState extends State {

    public FillingJugsState(Core a) {
        super(a);
    }

    @Override
    public boolean readyForExecution() throws InterruptedException {
        return this.core.isFillingJugs && this.core.inventory.contains("Jug") && Constants.FOUNTAIN_AREA.contains(this.core.myPlayer()) && System.currentTimeMillis() - this.core.lastInteractTime < 20000L;
    }

    @Override
    public String textual() {
        return "Filling Jugs";
    }

    @Override
    public long getDebugTimeout() throws InterruptedException {
        return 40L;
    }

    @Override
    public boolean execute() throws InterruptedException {
        if (this.core.lastFilledAmount != this.core.inventory.getAmount("Jug of water")) {
            this.core.lastFilledTime = System.currentTimeMillis();
        }

        this.core.lastFilledAmount = this.core.inventory.getAmount("Jug of water");
        if (System.currentTimeMillis() - this.core.lastFilledTime > 3500L) {
            this.core.isFillingJugs = false;
        }

        return true;
    }

}
