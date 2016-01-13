public class WalkToFountainState extends State {

    public WalkToFountainState(Core a) {
        super(a);
    }

    @Override
    public String textual() {
        return "Walking to Fountain";
    }

    @Override
    public boolean readyForExecution() throws InterruptedException {
        return core.inventory.contains("Jug") && !Constants.FOUNTAIN_AREA.contains(core.myPlayer()) && !core.myPlayer().isMoving();
    }

    @Override
    public boolean execute() throws InterruptedException {
        core.localWalker.walk(Constants.POTENTIAL_FOUNTAIN_DESTINATIONS[Core.random(0, 6)]);
        core.isFillingJugs = false;
        return true;
    }

    @Override
    public long getDebugTimeout() throws InterruptedException {
        return 25L;
    }

}
