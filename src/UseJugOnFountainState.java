import org.osbot.rs07.api.model.Entity;

public class UseJugOnFountainState extends State {

    public UseJugOnFountainState(final Core a) {
        super(a);
    }

    @Override
    public boolean readyForExecution() throws InterruptedException {
        return (Constants.FOUNTAIN_AREA.contains(this.core.myPlayer()) &&
                !this.core.isFillingJugs &&
                this.core.inventory.contains("Jug")) ||
                (System.currentTimeMillis() - this.core.lastInteractTime > 20000L &&
                        this.core.inventory.contains("Jug") &&
                        Constants.FOUNTAIN_AREA.contains(this.core.myPlayer())
                );
    }

    @Override
    public long getDebugTimeout() throws InterruptedException {
        return 10L;
    }

    @Override
    public boolean execute() throws InterruptedException {
        Entity closest = this.core.objects.closest("Fountain");
        if (closest != null) {
            this.core.inventory.getItem("Jug").interact("Use");
            closest.interact("Use");
            this.core.isFillingJugs = true;
            this.core.lastInteractTime = System.currentTimeMillis();
            this.core.totalFilled += this.core.lastFilledAmount;
            this.core.lastFilledAmount = 0L;
        }
        return true;
    }

    @Override
    public String textual() {
        return "Interacting with Fountain";
    }

}
