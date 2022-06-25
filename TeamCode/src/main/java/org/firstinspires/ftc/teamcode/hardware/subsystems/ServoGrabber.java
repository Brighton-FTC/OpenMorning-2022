package org.firstinspires.ftc.teamcode.hardware.subsystems;


import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.libs.util.TelemetryContainer;

public class ServoGrabber {
    private final Servo servo;
    /**
     * Direction of servo inverted?
     */
    private final double closedPos;
    private final double openPos;

    public ServoGrabber(Servo servo, double closedPos, double openPos) {
        this.servo = servo;
        this.openPos = openPos;
        this.closedPos = closedPos;
    }

    public void setClosed(boolean isClosed) {
        double desiredPos = isClosed ? this.closedPos : this.openPos;
        TelemetryContainer.getTelemetry().addData("Servo desired pos", desiredPos);
        servo.setPosition(desiredPos);
    }

    public void rotateTo(double rotation){
        servo.setPosition(rotation);
    }

    public void setServoReversed(boolean isReversed){
        if (isReversed) servo.setDirection(Servo.Direction.REVERSE);
        else servo.setDirection(Servo.Direction.FORWARD);
    }

    public double getRotation(){
        return servo.getPosition();
    }
}
