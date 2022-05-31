package org.firstinspires.ftc.teamcode.hardware.subsystems;

import com.qualcomm.robotcore.hardware.CRServo;

public class ServoIntake {
    private final CRServo servo;
    /**
     * Direction of servo inverted?
     */
    private final boolean isInverted;

    public ServoIntake(CRServo servo, boolean isInverted) {
        this.servo = servo;
        this.isInverted = isInverted;
    }

    public void spin(double speed){
        if(this.isInverted) speed *= -1.0;
        servo.setPower(speed);
    }
}
