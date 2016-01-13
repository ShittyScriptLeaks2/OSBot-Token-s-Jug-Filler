public class FillingJugsState extends State {

    public FillingJugsState(Core a) {
        super(a);
    }

    @Override
    public String textual() {
        return "Filling Jugs";
    }

    @Override
    public boolean readyForExecution() throws InterruptedException {
        return core.isFillingJugs && core.inventory.contains("Jug") && Constants.FOUNTAIN_AREA.contains(core.myPlayer()) && System.currentTimeMillis() - core.lastInteractTime < 20000L;
    }

    @Override
    public boolean execute() throws InterruptedException {
        if (core.lastFilledAmount != core.inventory.getAmount("Jug of water")) {
            core.lastFilledTime = System.currentTimeMillis();
        }

        core.lastFilledAmount = core.inventory.getAmount("Jug of water");
        if (System.currentTimeMillis() - core.lastFilledTime > 3500L) {
            core.isFillingJugs = false;
        }

        return true;
    }

    @Override
    public long getDebugTimeout() throws InterruptedException {
        return 40L;
    }

}
