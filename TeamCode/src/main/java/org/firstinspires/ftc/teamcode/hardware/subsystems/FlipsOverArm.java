package org.firstinspires.ftc.teamcode.hardware.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

/**
 * Team 1's arm which flips over the back of the robot to deliver freight
 * NOTE: The arm should start resting on the floor as all angles are relative to this.
 */
public class FlipsOverArm { // TODO: Name this something better
    double frontAngle;
    double backAngle;
    /**
     * Is the arm at the back of the robot?
     */
    boolean atBack;

    DcMotor motor;
    /**
     * Number of counts per radian of the arm motor
     */
    double countsPerRadian;
    /**
     * Set up a FlipsOverArm
     * @param motor CRServo motor
     * @param isReversed Toggle this if the motor tries to move the arm down.
     * @param countsPerRadian (counts) Number of counts per radian of the arm motor
     * @param frontAngle (rad) The angle relative to the starting angle of where to keep the arm when *not* flipped
     * @param backAngle (rad) The angle relative to the starting angle of where to keep the arm when *flipped*
     */
    public FlipsOverArm(DcMotor motor, boolean isReversed, double countsPerRadian, double frontAngle, double backAngle) {
        this.motor = motor;
        this.frontAngle = frontAngle;
        this.backAngle = backAngle;
        /* Initialise encoders */
        if(isReversed) motor.setDirection(DcMotorSimple.Direction.REVERSE);
        this.motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        this.motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        this.countsPerRadian = countsPerRadian;
    }

    /**
     * Move to a desired position by encoders
     * @param desiredCounts number of encoder counts relative to starting pos
     */
    public void moveToCounts(int desiredCounts) {
        this.motor.setTargetPosition(desiredCounts);
        this.motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
    /**
     * Move to a desired position by radians
     * @param desiredRadians number of radians relative to starting pos
     */
    public void moveToRadians(double desiredRadians) {
        this.moveToCounts((int) (desiredRadians * countsPerRadian));
    }

    /**
     * Hover above the ground so it doesn't scrape.
     * Call this after the OpMode hs been started to prevent early moving, and also to end the flipped state.
     * @param speed (0>1) maximum speed of the motor
     */
    public void moveToFront(double speed) {
        this.motor.setPower(speed);
        this.motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        this.moveToRadians(this.frontAngle);
        this.atBack = false;
    }

    /**
     * Move to the back of the robot. Call this to start the flipped state.
     * @param speed (0>1) maximum speed of the motor
     */
    public void moveToBack(double speed) {
        this.motor.setPower(speed);
        this.motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        this.moveToRadians(this.backAngle);
        this.atBack = true;
    }

    /**
     * Toggle whether the arm is at the front or back of the robot
     * @param speed (0>1) maximum speed of the motor
     */
    public void togglePosition(double speed) {
        if(this.atBack) {
            this.moveToFront(speed);
        } else {
            this.moveToBack(speed);
        }
    }
}
