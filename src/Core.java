import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

@ScriptManifest(name = "Token's Jug Filler", author = "Token", version = 1.1, info = "Fills jugs of water", logo = "http://imgur.com/5AduBGV.png")
public class Core extends Script {

    public long jugPrice;
    public long lastDebugTime;
    public long totalFilled;
    public long lastFilledAmount;
    public long startTimeInSeconds;
    public Debugger debugger;
    public long jugOfWaterPrice;
    public long profits;
    public boolean isPaintEnabled;
    public long runtimeInSeconds;
    public boolean inDebugMode;
    public long lastFilledTime;
    public long profitsPerHour;
    public long lastInteractTime;
    public BufferedImage backgroundImage;
    public boolean isFillingJugs;
    public long filledPerHour;
    private String textualState = "";
    private ArrayList<State> states = new ArrayList<>();

    public int onLoop() throws InterruptedException {
        if (inDebugMode && (runtimeInSeconds - lastDebugTime) > 20L) {
            inDebugMode = false;
        }

        if (!debugger.timestamps.isEmpty() && debugger.hasTimedOut(debugger.timestamps, runtimeInSeconds) && !inDebugMode) {
            log("Attempting debug...");
            debugger.debug();
            lastDebugTime = runtimeInSeconds;
            inDebugMode = true;
        }

        for (State state : states) {
            if (state.readyForExecution()) {
                textualState = state.textual();
                if (!state.execute()) {
                    continue;
                }

                if (debugger.timestamps.isEmpty() || debugger.timestamps.get(debugger.timestamps.size() - 1).state != state) {
                    debugger.timestamps.add(new StateTimestamp(state, runtimeInSeconds));
                }

                // wow how fucking retarded can you get lol
                if (state.textual() == "Deposit Jugs") {
                    return random(700, 800);
                }

                if (state.textual() == "Interacting with Fountain") {
                    return random(1300, 1900);
                }

                return random(200, 400);
            }
        }

        return random(300, 500);
    }

    public void onPaint(Graphics2D g) {
        if (isPaintEnabled) {
            g.drawImage(backgroundImage, 290, 345, null);
            runtimeInSeconds = System.currentTimeMillis() / 1000L - startTimeInSeconds;
            filledPerHour = Math.round((totalFilled + lastFilledAmount) / runtimeInSeconds * 3600.0f);
            g.setColor(Color.BLACK);
            g.setFont(new Font("Arial", Font.BOLD, 15));
            g.drawString(textualState, 295, 365);
            g.drawString("Jugs filled: " + (totalFilled + lastFilledAmount) + "(" + filledPerHour + ")", 295, 390);
            g.drawString("Runtime: " + String.format("%02d", runtimeInSeconds / 3600L) + ":" + String.format("%02d", runtimeInSeconds % 3600L / 60L) + ":" + String.format("%02d", runtimeInSeconds % 3600L % 60L), 295, 415);
            profits = (totalFilled + lastFilledAmount) * (jugOfWaterPrice - jugPrice);
            profitsPerHour = filledPerHour * (jugOfWaterPrice - jugPrice);
            g.drawString("Profit: " + profits + "(" + String.valueOf(profitsPerHour) + ")", 295, 440);
        }
    }

    public void onExit() {

    }

    public void onStart() {
        try {
            jugPrice = Utils.getBuyingPrice(1935);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        try {
            jugOfWaterPrice = Utils.getSellingPrice(1937);
        } catch (IOException ex2) {
            ex2.printStackTrace();
        }

        try {
            backgroundImage = Constants.imageFromURL("http://imgur.com/4GaLPML.png");
        } catch (IOException ex3) {
            ex3.printStackTrace();
        }

        debugger = new Debugger(this);
        startTimeInSeconds = System.currentTimeMillis() / 1_000L;
        isPaintEnabled = true;
        Collections.addAll(states, new DepositWaterJugsState(this), new FillingJugsState(this), new UseJugOnFountainState(this), new OpenBankState(this), new WalkToBankState(this), new WalkToFountainState(this), new WithdrawJugsState(this));
    }
}
