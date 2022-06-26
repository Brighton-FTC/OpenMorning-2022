package org.firstinspires.ftc.teamcode.opModes.team2;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.checkerframework.checker.units.qual.C;
import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.hardware.subsystems.DiscretePositionArm;
import org.firstinspires.ftc.teamcode.opModes.subroutines.autonomous.Deliver;
import org.firstinspires.ftc.teamcode.opModes.subroutines.autonomous.DriveForwardCounts;
import org.firstinspires.ftc.teamcode.opModes.subroutines.autonomous.TurnCounts;
import org.firstinspires.ftc.teamcode.opModes.subroutines.autonomous.team2.Team2Deposit;
import org.firstinspires.ftc.teamcode.wrappers.LinearOpModeWrapper;

public abstract class AutonomousGeneric extends LinearOpModeWrapper {
    protected boolean isSpinnerReversed;
    @Override
    public void run() throws InterruptedException {
        setup();
        waitForStart();
        DiscretePositionArm arm = new DiscretePositionArm(
                        hardwareMap.get(DcMotor.class, "slide"),
                        false,
                        Constants.TEAM2_SLIDE_FRONT_COUNTS,
                        Constants.TEAM2_SLIDE_BACK_COUNTS
                );
        arm.moveToFront(Constants.TEAM2_SLIDE_SPEED);

        new DriveForwardCounts().run(this, 2000, 0.5);
        new TurnCounts().run(this, -420, 0.5);
        new Deliver().run(this, 1000, 1, isSpinnerReversed);
        new Team2Deposit().run(this, arm);
    }

    public abstract void setup();
}
