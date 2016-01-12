public class WalkToFountainState extends State {

    public WalkToFountainState(Core a) {
        super(a);
    }

    @Override
    public String textual() {
        return "Walking to Fountain";
    }

    @Override
    public boolean execute() throws InterruptedException {
        this.core.localWalker.walk(Constants.POTENTIAL_FOUNTAIN_DESTINATIONS[Core.random(0, 6)]);
        this.core.isFillingJugs = false;
        return true;
    }

    @Override
    public boolean readyForExecution() throws InterruptedException {
        return this.core.inventory.contains("Jug") && !Constants.FOUNTAIN_AREA.contains(this.core.myPlayer()) && !this.core.myPlayer().isMoving();
    }

    @Override
    public long getDebugTimeout() throws InterruptedException {
        return 25L;
    }

}
