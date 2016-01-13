import java.util.ArrayList;

public class Debugger {

    public Core core;
    public ArrayList<StateTimestamp> timestamps = new ArrayList<>();

    public Debugger(Core core) {
        this.core = core;
    }

    public boolean hasTimedOut(ArrayList<StateTimestamp> stamps, long time) throws InterruptedException {
        boolean timedOut = false;
        if ((time - stamps.get(stamps.size() - 1).timeInSeconds) > stamps.get(stamps.size() - 1).state.getDebugTimeout()) {
            timedOut = true;
        }
        return timedOut;
    }

    public void debug() throws InterruptedException {
        core.log("Attempting debug...");
        if (core.myPosition().getZ() != 0) {
            core.objects.closest("Staircase").interact("Climb-Down");
            core.log("Successfully debugged!");
            return;
        }

        if (!core.widgets.filter(a -> a.getRootId() == 402 && a.getSecondLevelId() == 2 && a.getThirdLevelId() == 11).isEmpty()) {
            core.widgets.filter(a -> a.getRootId() == 402 && a.getSecondLevelId() == 2 && a.getThirdLevelId() == 11).get(0).interact("Close");
            core.log("Successfully debugged!");
            return;
        }

        core.localWalker.walk(Constants.POTENTIAL_DEBUG_DESTINATIONS[Core.random(0, 3)]);
        core.log("Successfully debugged!");
    }

}
