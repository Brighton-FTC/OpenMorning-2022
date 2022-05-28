package org.firstinspires.ftc.teamcode.wrappers;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.inputs.GamepadWrapper;
import org.firstinspires.ftc.teamcode.libs.util.TelemetryContainer;

public abstract class LinearOpModeWrapper extends LinearOpMode {
    public abstract void run() throws InterruptedException;

    /**
     * A class that wraps a LinearOpMode to set up inputs and outputs so they're accessible by classes
     */
    @Override
    public void runOpMode() throws InterruptedException {
        GamepadWrapper.createGamepadWrapper(gamepad1);
        TelemetryContainer.setTelemetry(telemetry);
        run();
    }
}
