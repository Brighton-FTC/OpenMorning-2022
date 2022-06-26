package org.firstinspires.ftc.teamcode.hardware.subsystems;

import static java.lang.Thread.sleep;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.hardware.subsystems.joystickMappings.JoystickMapping;

public class DriveTrainController {
    public final DriveTrain driveTrain;
    private DriveTrainState driveTrainState;
    private JoystickMapping speedMapping;
    private JoystickMapping turnMapping;
    private final double forwardsGradient;
    private final double forwardsIntercept;

    public DriveTrainController(DriveTrain driveTrain, JoystickMapping speedMapping, JoystickMapping turnMapping, double forwardsGradient, double forwardsIntercept){
        this.driveTrain = driveTrain;
        this.driveTrainState = DriveTrainState.DRIVER_CONTROLLED;
        this.speedMapping = speedMapping;
        this.turnMapping = turnMapping;

        this.forwardsIntercept = forwardsIntercept;
        this.forwardsGradient = forwardsGradient;
    }

    public void drive_scaled(double speed, double turn) {
        drive_scaled(speed, turn, 1.0);
    }

    public void drive_scaled(double speed, double turn, double overallScale){
        driveTrainState = DriveTrainState.DRIVER_CONTROLLED;

        // scale from -1 to 1
        speed = speedMapping.map(speed);
        turn = turnMapping.map(turn) * 0.5;

        driveTrain.arcadeDriveScale(speed * overallScale, turn * overallScale);
    }

    public void drive_unscaled(double speed, double turn){
        driveTrainState = DriveTrainState.DRIVER_CONTROLLED;

        driveTrain.arcadeDriveScale(speed, turn);
    }

    public boolean isBusy(){
        if (driveTrainState == DriveTrainState.DRIVER_CONTROLLED) return false;
        return driveTrain.leftMotor.isBusy() || driveTrain.rightMotor.isBusy();
    }

    public JoystickMapping getSpeedMapping(){
        return speedMapping;
    }

    public JoystickMapping getTurnMapping() {
        return turnMapping;
    }

    public void setTurnMapping(JoystickMapping turnMapping){
        this.turnMapping = turnMapping;
    }

    public void setSpeedMapping(JoystickMapping speedMapping){
        this.speedMapping = speedMapping;
    }

    public void startDrivingForward(double distance, double speed) {
        if (isBusy()) throw new RuntimeException("Interrupting drivetrain operation");
        driveTrainState = DriveTrainState.AUTO_DRIVE;

        driveTrain.rightMotor.setPower(speed);
        driveTrain.leftMotor.setPower(speed);
        int countDiff = (int)(distance * forwardsGradient + forwardsIntercept);
        incrementDesiredMotorCounts(countDiff, countDiff);
    }

    /**
     * Uses counts of each motor
     * @param leftCounts number of counts to add to left drive motor
     * @param rightCounts number of counts to add to right drive motor
     * @param speed maximum speed of motors
     */
    public void startDrivingCounts(int leftCounts, int rightCounts, double speed) {
        if (isBusy()) throw new RuntimeException("Interrupting drivetrain operation");
        driveTrainState = DriveTrainState.AUTO_DRIVE;

        driveTrain.rightMotor.setPower(speed);
        driveTrain.leftMotor.setPower(speed);
        incrementDesiredMotorCounts(leftCounts, rightCounts);
    }
//
//    public void startTurn(double turnRadians, double speed) {
//        if (isBusy()) throw new RuntimeException("Interrupting drivetrain operation");
//        driveTrainState = DriveTrainState.AUTO_TURN;
//
//        driveTrain.rightMotor.setPower(speed);
//        driveTrain.leftMotor.setPower(speed);
//        incrementDesiredMotorCounts((int) (turnRadians * countsPerRadian), -(int)(turnRadians * countsPerRadian));
//    }

    private void incrementDesiredMotorCounts(int leftCountsChange, int rightCountsChange) {
        int desiredLeftCounts = driveTrain.leftMotor.getCurrentPosition() + leftCountsChange;
        int desiredRightCounts = driveTrain.rightMotor.getCurrentPosition() + rightCountsChange;

        driveTrain.leftMotor.setTargetPosition(desiredLeftCounts);
        driveTrain.rightMotor.setTargetPosition(desiredRightCounts);
        driveTrain.leftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        driveTrain.rightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public void resetEncoders(){
        DcMotor.RunMode lastMode = driveTrain.rightMotor.getMode();
        driveTrain.leftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        driveTrain.rightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        driveTrain.leftMotor.setMode(lastMode);
        driveTrain.rightMotor.setMode(lastMode);
    }

    public void waitWhileBusy() throws InterruptedException {
        while (this.isBusy()) { sleep(50); }
    }
}
