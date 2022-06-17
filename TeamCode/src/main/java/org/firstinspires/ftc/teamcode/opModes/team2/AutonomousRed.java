package org.firstinspires.ftc.teamcode.opModes.team2;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.opModes.subroutines.AutonomousDriveForward;
import org.firstinspires.ftc.teamcode.wrappers.LinearOpModeWrapper;

@Autonomous(name="Team 2 - Autonomous Red", group="2_Autonomous")
public class AutonomousRed extends LinearOpModeWrapper {

    @Override
    public void run() throws InterruptedException {
        {
            // INIT
        }
        waitForStart();
        {
            // RUN
            new AutonomousDriveForward().run(this);
        }
    }
}