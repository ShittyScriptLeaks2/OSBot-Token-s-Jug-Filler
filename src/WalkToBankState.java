public class WalkToBankState extends State {

    public WalkToBankState(Core a) {
        super(a);
    }

    @Override
    public String textual() {
        return "Walking to Bank";
    }

    @Override
    public boolean readyForExecution() throws InterruptedException {
        return !core.inventory.contains("Jug") && !Constants.BANK_AREA.contains(core.myPlayer()) && !core.myPlayer().isMoving();
    }

    @Override
    public boolean execute() throws InterruptedException {
        core.isFillingJugs = false;
        core.localWalker.walk(Constants.POTENTIAL_BANK_DESTINATIONS[Core.random(0, 6)]);
        core.lastFilledAmount = core.inventory.getAmount("Jug of water");
        return true;
    }

    @Override
    public long getDebugTimeout() throws InterruptedException {
        return 25L;
    }

}
