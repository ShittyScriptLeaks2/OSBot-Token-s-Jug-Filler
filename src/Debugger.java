import java.util.ArrayList;

// I'm not sure what this is for at all..
public class Debugger {

    public Core core;
    public ArrayList<StateTimestamp> timestamps = new ArrayList<>();

    public Debugger(final Core core) {
        this.core = core;
    }

    public boolean hasTimedOut(final ArrayList<StateTimestamp> al, final long a) throws InterruptedException {
        boolean timedOut = false;
        if ((a - al.get(al.size() - 1).timeInSeconds) > al.get(al.size() - 1).state.getDebugTimeout()) {
            timedOut = true;
        }
        return timedOut;
    }

    public void debug() throws InterruptedException {
        this.core.log("Attempting debug...");
        if (this.core.myPosition().getZ() != 0) {
            this.core.objects.closest("Staircase").interact("Climb-Down");
            this.core.log("Successfully debugged!");
            return;
        }

        // LOL
        if (!this.core.widgets.filter(a -> a.getRootId() == 402 && a.getSecondLevelId() == 2 && a.getThirdLevelId() == 11).isEmpty()) {
            this.core.widgets.filter(a -> a.getRootId() == 402 && a.getSecondLevelId() == 2 && a.getThirdLevelId() == 11).get(0).interact("Close");
            this.core.log("Successfully debugged!");
            return;
        }

        this.core.localWalker.walk(Constants.POTENTIAL_DEBUG_DESTINATIONS[Core.random(0, 3)]);
        this.core.log("Successfully debugged!");
    }

}
