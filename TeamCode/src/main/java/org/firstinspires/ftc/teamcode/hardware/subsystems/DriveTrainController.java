package org.firstinspires.ftc.teamcode.hardware.subsystems;

import static java.lang.Thread.sleep;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.hardware.subsystems.joystickMappings.JoystickMapping;

public class DriveTrainController {
    public final DriveTrain driveTrain;
    private final double countsPerRadian;
    private final double countPerMeter;
    private DriveTrainState driveTrainState;
    private JoystickMapping mapping;

    public DriveTrainController(DriveTrain driveTrain, double countsPerRadian, double countPerMeter, JoystickMapping mapping){
        this.driveTrain = driveTrain;
        this.countsPerRadian = countsPerRadian;
        this.countPerMeter = countPerMeter;
        this.driveTrainState = DriveTrainState.DRIVER_CONTROLLED;
        this.mapping = mapping;
    }

    public void drive_scaled(double speed, double turn) {
        driveTrainState = DriveTrainState.DRIVER_CONTROLLED;

        // scale from -1 to 1
        speed = mapping.mapSpeed(speed);
        turn = mapping.mapTurning(turn);

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
