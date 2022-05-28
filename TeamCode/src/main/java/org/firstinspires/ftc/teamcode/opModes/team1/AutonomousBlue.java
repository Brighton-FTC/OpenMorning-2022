package org.firstinspires.ftc.teamcode.opModes.team1;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.opModes.team1.subroutines.AutonomousDriveForward;
import org.firstinspires.ftc.teamcode.wrappers.LinearOpModeWrapper;

@Autonomous(name="Team 1 - Autonomous Blue", group="1_Autonomous")
public class AutonomousBlue extends LinearOpModeWrapper {

    @Override
    public void run() throws InterruptedException {
        new AutonomousDriveForward().run(hardwareMap);
    }
}
