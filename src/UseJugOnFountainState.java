import org.osbot.rs07.api.model.Entity;

public class UseJugOnFountainState extends State {

    public UseJugOnFountainState(Core a) {
        super(a);
    }

    @Override
    public String textual() {
        return "Interacting with Fountain";
    }

    @Override
    public boolean readyForExecution() throws InterruptedException {
        return (Constants.FOUNTAIN_AREA.contains(core.myPlayer()) &&
                !core.isFillingJugs &&
                core.inventory.contains("Jug")) ||
                (System.currentTimeMillis() - core.lastInteractTime > 20000L &&
                        core.inventory.contains("Jug") &&
                        Constants.FOUNTAIN_AREA.contains(core.myPlayer())
                );
    }

    @Override
    public boolean execute() throws InterruptedException {
        Entity closest = core.objects.closest("Fountain");
        if (closest != null) {
            core.inventory.getItem("Jug").interact("Use");
            closest.interact("Use");
            core.isFillingJugs = true;
            core.lastInteractTime = System.currentTimeMillis();
            core.totalFilled += core.lastFilledAmount;
            core.lastFilledAmount = 0L;
        }
        return true;
    }

    @Override
    public long getDebugTimeout() throws InterruptedException {
        return 10L;
    }

}
