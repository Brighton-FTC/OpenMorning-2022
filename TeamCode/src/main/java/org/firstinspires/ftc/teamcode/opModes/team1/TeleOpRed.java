package org.firstinspires.ftc.teamcode.opModes.team1;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "Team 1 - TeleOp Red", group = "1_TeleOp")
public class TeleOpRed extends TeleOpGeneric {
    @Override
    public void setup() {
        custom_setup();
    }

    @Override
    public void loop() {
        custom_loop(true);
    }
}
