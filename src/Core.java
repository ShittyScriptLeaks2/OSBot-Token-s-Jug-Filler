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
        if (this.inDebugMode && (this.runtimeInSeconds - this.lastDebugTime) > 20L) {
            this.inDebugMode = false;
        }

        if (!this.debugger.timestamps.isEmpty() && this.debugger.hasTimedOut(this.debugger.timestamps, this.runtimeInSeconds) && !this.inDebugMode) {
            this.log("Attempting debug...");
            this.debugger.debug();
            this.lastDebugTime = this.runtimeInSeconds;
            this.inDebugMode = true;
        }

        for (State state : this.states) {
            if (state.readyForExecution()) {
                this.textualState = state.textual();
                if (!state.execute()) {
                    continue;
                }

                if (this.debugger.timestamps.isEmpty() || this.debugger.timestamps.get(this.debugger.timestamps.size() - 1).state != state) {
                    this.debugger.timestamps.add(new StateTimestamp(state, this.runtimeInSeconds));
                }

                // wow how fucking retarded can you get lol
                if (state.textual() == "Deposit Jugs") {
                    return random(700, 800);
                } else if (state.textual() == "Interacting with Fountain") {
                    return random(1300, 1900);
                }

                return random(200, 400);
            }
        }

        return random(300, 500);
    }

    public void onPaint(final Graphics2D a) {
        if (this.isPaintEnabled) {
            a.drawImage(this.backgroundImage, 290, 345, null);
            this.runtimeInSeconds = System.currentTimeMillis() / 1000L - this.startTimeInSeconds;
            this.filledPerHour = Math.round((this.totalFilled + this.lastFilledAmount) / this.runtimeInSeconds * 3600.0f);
            a.setColor(Color.BLACK);
            a.setFont(new Font("Arial", Font.BOLD, 15));
            a.drawString(this.textualState, 295, 365);
            a.drawString("Jugs filled: " + (this.totalFilled + this.lastFilledAmount) + "(" + this.filledPerHour + ")", 295, 390);
            a.drawString("Runtime: " + String.format("%02d", this.runtimeInSeconds / 3600L) + ":" + String.format("%02d", this.runtimeInSeconds % 3600L / 60L) + ":" + String.format("%02d", this.runtimeInSeconds % 3600L % 60L), 295, 415);
            this.profits = (this.totalFilled + this.lastFilledAmount) * (this.jugOfWaterPrice - this.jugPrice);
            this.profitsPerHour = this.filledPerHour * (this.jugOfWaterPrice - this.jugPrice);
            a.drawString("Profit: " + this.profits + "(" + String.valueOf(this.profitsPerHour) + ")", 295, 440);
        }
    }

    public void onExit() {

    }

    public void onStart() {
        try {
            this.jugPrice = Utils.getBuyingPrice(1935);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        try {
            this.jugOfWaterPrice = Utils.getSellingPrice(1937);
        } catch (IOException ex2) {
            ex2.printStackTrace();
        }

        try {
            this.backgroundImage = Constants.imageFromURL("http://imgur.com/4GaLPML.png");
        } catch (IOException ex3) {
            ex3.printStackTrace();
        }

        this.debugger = new Debugger(this);
        this.startTimeInSeconds = System.currentTimeMillis() / 1_000L;
        this.isPaintEnabled = true;
        Collections.addAll(this.states, new DepositWaterJugsState(this), new FillingJugsState(this), new UseJugOnFountainState(this), new OpenBankState(this), new WalkToBankState(this), new WalkToFountainState(this), new WithdrawJugsState(this));
    }
}
