package org.firstinspires.ftc.teamcode;

public class Timer {
    private long lastTime = -1;

    public void reset(){
        lastTime = System.currentTimeMillis();
    }
    public long getTimePassed(){
        long currentTime = System.currentTimeMillis();
        if (lastTime < 0) lastTime = currentTime;

        return currentTime - lastTime;
    }
}
