package org.firstinspires.ftc.teamcode.opModes.team2;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "Team 2 - TeleOp Blue", group = "2_TeleOp")
public class TeleOpBlue extends TeleOpGeneric {
    @Override
    public void setup() {
        custom_setup();
    }

    @Override
    public void loop() {
        custom_loop(false);
    }
}
