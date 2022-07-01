package org.firstinspires.ftc.teamcode.opModes.team1;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.hardware.subsystems.DiscretePositionArm;
import org.firstinspires.ftc.teamcode.hardware.subsystems.DriveTrain;
import org.firstinspires.ftc.teamcode.hardware.subsystems.DriveTrainController;
import org.firstinspires.ftc.teamcode.hardware.subsystems.ServoGrabber;
import org.firstinspires.ftc.teamcode.hardware.subsystems.joystickMappings.CosMapping;
import org.firstinspires.ftc.teamcode.hardware.subsystems.joystickMappings.RootMapping;
import org.firstinspires.ftc.teamcode.opModes.subroutines.AutonomousDriveForward;
import org.firstinspires.ftc.teamcode.opModes.subroutines.autonomous.Deliver;
import org.firstinspires.ftc.teamcode.opModes.subroutines.autonomous.DriveForwardCounts;
import org.firstinspires.ftc.teamcode.opModes.subroutines.autonomous.TurnCounts;
import org.firstinspires.ftc.teamcode.opModes.subroutines.autonomous.team1.Team1Deposit;
import org.firstinspires.ftc.teamcode.wrappers.LinearOpModeWrapper;

public abstract class AutonomousGeneric extends LinearOpModeWrapper {
    protected boolean isSpinnerReversed;
    @Override
    public void run() throws InterruptedException {
        setup();
        waitForStart();
        DiscretePositionArm arm = new DiscretePositionArm(
                hardwareMap.get(DcMotor.class, "arm"),
                false,
                Constants.TEAM1_ARM_FRONT_COUNTS,
                Constants.TEAM1_ARM_BACK_COUNTS
        );
        DriveTrainController driveTrain = new DriveTrainController(new DriveTrain(
                hardwareMap.get(DcMotor.class, "left_drivetrain_motor"),
                hardwareMap.get(DcMotor.class, "right_drivetrain_motor"),
                true
        ),
                new RootMapping(2),
                new CosMapping(),
                0,0
        );
        ServoGrabber grabber = new ServoGrabber(hardwareMap.get(Servo.class, "grabber"), Constants.TEAM1_GRABBER_CLOSED_POS, Constants.TEAM1_GRABBER_OPEN_POS);
        arm.moveToFront(Constants.TEAM1_ARM_SPEED);
        grabber.setClosed(true);

        /* Drive forwards into the warehouse */
        new DriveForwardCounts().run(this, driveTrain, 1000, 0.20);
    }

    public abstract void setup();
}
