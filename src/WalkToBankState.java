public class WalkToBankState extends State {

    public WalkToBankState(Core a) {
        super(a);
    }

    @Override
    public long getDebugTimeout() throws InterruptedException {
        return 25L;
    }

    @Override
    public boolean execute() throws InterruptedException {
        this.core.isFillingJugs = false;
        this.core.localWalker.walk(Constants.POTENTIAL_BANK_DESTINATIONS[Core.random(0, 6)]);
        this.core.lastFilledAmount = this.core.inventory.getAmount("Jug of water");
        return true;
    }

    @Override
    public String textual() {
        return "Walking to Bank";
    }

    @Override
    public boolean readyForExecution() throws InterruptedException {
        return !this.core.inventory.contains("Jug") && !Constants.BANK_AREA.contains(this.core.myPlayer()) && !this.core.myPlayer().isMoving();
    }

}
