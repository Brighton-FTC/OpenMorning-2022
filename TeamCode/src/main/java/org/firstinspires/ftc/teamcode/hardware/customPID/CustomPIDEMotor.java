package org.firstinspires.ftc.teamcode.hardware.customPID;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

public class CustomPIDEMotor {
    public CustomPIDEController controller;
    DcMotorEx motor;

    public CustomPIDEMotor(CustomPIDEController controller, DcMotor motor, boolean isReversed){
        this.controller = controller;
        this.motor = (DcMotorEx) motor;

        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        if (isReversed) motor.setDirection(DcMotorSimple.Direction.REVERSE);
        else motor.setDirection(DcMotorSimple.Direction.FORWARD);

        // make sure that we keep the angular position constant
        this.motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void setTargetPosition(int targetValue) {
        this.controller.setTarget(targetValue);
    }

    /** Update the PID
     * @param externalTerm an additional term that can be supplied to be added to the result
     * @return the output of PID
     */
    public void update(double externalTerm){
        double correction = controller.update(motor.getCurrentPosition(), externalTerm);
        motor.setPower(correction);
    }
}
