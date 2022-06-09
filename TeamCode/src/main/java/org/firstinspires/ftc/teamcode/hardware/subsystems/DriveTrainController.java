package org.firstinspires.ftc.teamcode.hardware.subsystems;

import static java.lang.Thread.sleep;

import com.qualcomm.robotcore.hardware.DcMotor;

public class DriveTrainController {
    private final DriveTrain driveTrain;
    private final double countsPerRadian;
    private final double countPerMeter;
    private DriveTrainState driveTrainState;

    public DriveTrainController(DriveTrain driveTrain, double countsPerRadian, double countPerMeter){
        this.driveTrain = driveTrain;
        this.countsPerRadian = countsPerRadian;
        this.countPerMeter = countPerMeter;
        driveTrainState = DriveTrainState.DRIVER_CONTROLLED;
    }

    public void drive(double speed, double turn) {
        driveTrainState = DriveTrainState.DRIVER_CONTROLLED;
        driveTrain.arcadeDriveScale(speed, turn);
    }

    public boolean isBusy(){
        if (driveTrainState == DriveTrainState.DRIVER_CONTROLLED) return false;
        return driveTrain.leftMotor.isBusy() || driveTrain.rightMotor.isBusy();
    }

    public void startDrivingForward(double distance, double speed) {
        if (isBusy()) throw new RuntimeException("Interrupting drivetrain operation");
        driveTrainState = DriveTrainState.AUTO_DRIVE;

        driveTrain.rightMotor.setPower(speed);
        driveTrain.leftMotor.setPower(speed);
        int countDiff = (int)(distance * countPerMeter);
        incrementDesiredMotorCounts(countDiff, countDiff);
    }

    public void startTurn(double turnRadians, double speed) {
        if (isBusy()) throw new RuntimeException("Interrupting drivetrain operation");
        driveTrainState = DriveTrainState.AUTO_TURN;

        driveTrain.rightMotor.setPower(speed);
        driveTrain.leftMotor.setPower(speed);
        incrementDesiredMotorCounts((int) (turnRadians * countsPerRadian), -(int)(turnRadians * countsPerRadian));
    }

    private void incrementDesiredMotorCounts(int leftCountsChange, int rightCountsChange) {
        int desiredLeftCounts = driveTrain.leftMotor.getCurrentPosition() + leftCountsChange;
        int desiredRightCounts = driveTrain.rightMotor.getCurrentPosition() + rightCountsChange;

        driveTrain.leftMotor.setTargetPosition(desiredLeftCounts);
        driveTrain.rightMotor.setTargetPosition(desiredRightCounts);
        driveTrain.leftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        driveTrain.rightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public void waitWhileBusy() throws InterruptedException {
        while (this.isBusy()) { sleep(50); }
    }
}
