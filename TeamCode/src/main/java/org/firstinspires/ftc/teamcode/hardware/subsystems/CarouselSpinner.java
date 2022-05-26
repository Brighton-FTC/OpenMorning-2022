package org.firstinspires.ftc.teamcode.hardware.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

public class CarouselSpinner {
    private DcMotor motor;

    public CarouselSpinner(DcMotor motor, boolean isInverted) {
        this.motor = motor;
        if(isInverted) motor.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    public void spin(double speed){
        motor.setPower(speed);
    }
}
