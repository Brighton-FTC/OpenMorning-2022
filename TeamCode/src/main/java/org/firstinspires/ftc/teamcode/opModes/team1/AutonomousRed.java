package org.firstinspires.ftc.teamcode.opModes.team1;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name="Team 1 - Autonomous Red", group="1_Autonomous")
public class AutonomousRed extends AutonomousGeneric {
    @Override
    public void setup() {
        this.isSpinnerReversed = true;
    }
}
