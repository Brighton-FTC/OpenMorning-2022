package org.firstinspires.ftc.teamcode.hardware.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.libs.util.TelemetryContainer;

/**
 * Team 1's arm which flips over the back of the robot to deliver freight
 * NOTE: The arm should start resting on the floor as all angles are relative to this.
 */
public class DiscretePositionArm { // TODO: Name this something better
    int frontCounts;
    int backCounts;
    /**
     * Is the arm at the back of the robot?
     */
    boolean atBack;

    DcMotor motor;
    /**
     * Set up a FlipsOverArm
     * @param motor CRServo motor
     * @param isReversed Toggle this if the motor tries to move the arm down.
     * @param frontCounts (rad) The counts relative to the starting angle of where to keep the arm when *not* flipped
     * @param backCounts (rad) The counts relative to the starting angle of where to keep the arm when *flipped*
     */
    public DiscretePositionArm(DcMotor motor, boolean isReversed, int frontCounts, int backCounts) {
        this.motor = motor;
        this.frontCounts = frontCounts;
        this.backCounts = backCounts;
        /* Initialise encoders */
        if(isReversed) motor.setDirection(DcMotorSimple.Direction.REVERSE);
        this.motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        this.motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public double getPower(){
        return this.motor.getPower();
    }

    public void powerDown() {
        this.motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        this.motor.setPower(0);
    }

    /**
     * Move to a desired position by encoders
     * @param desiredCounts number of encoder counts relative to starting pos
     */
    public void moveToCounts(int desiredCounts, double speed) {
        this.motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        this.motor.setPower(speed);
        this.motor.setTargetPosition(desiredCounts);
        this.motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    /**
     * Hover above the ground so it doesn't scrape.
     * Call this after the OpMode hs been started to prevent early moving, and also to end the flipped state.
     * @param speed (0>1) maximum speed of the motor
     */
    public void moveToFront(double speed) {
        this.moveToCounts(this.frontCounts, speed);
        this.atBack = false;
    }

    /**
     * Move to the back of the robot. Call this to start the flipped state.
     * @param speed (0>1) maximum speed of the motor
     */
    public void moveToBack(double speed) {
        this.moveToCounts(this.backCounts, speed);
        this.atBack = true;
    }

    /**
     * Move the arm to a position. if is is close enough, power it down
     * @param desiredCounts desired position in counts
     * @param speed speed of the arm
     * @param epsilon maximal deviation for the arm to be powered down
     */
    public void moveToCountsAndPowerDown(int desiredCounts, double speed, int epsilon){
        int error = Math.abs(motor.getCurrentPosition() - desiredCounts);
        Telemetry telemetry = TelemetryContainer.getTelemetry();
        telemetry.addData("error", error);
        telemetry.addData("pos", motor.getCurrentPosition());
        if (error <= epsilon) {
            this.powerDown();
            return;
        }

        // if needed, start moving
        this.moveToCounts(desiredCounts, speed);
    }

    public boolean isBusy(){
        return this.motor.isBusy();
    }
}
