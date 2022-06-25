package org.firstinspires.ftc.teamcode.opModes.team1;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.hardware.subsystems.ServoGrabber;
import org.firstinspires.ftc.teamcode.inputs.GamepadButton;
import org.firstinspires.ftc.teamcode.inputs.Inputs;
import org.firstinspires.ftc.teamcode.inputs.XY;
import org.firstinspires.ftc.teamcode.inputs.inputs.ToggleableButton;
import org.firstinspires.ftc.teamcode.wrappers.OpModeWrapper;

@TeleOp(name = "Team 1 - Move grabber", group = "1_TeleOp")
public class TeleOpMoveGrabber extends OpModeWrapper {
    private ServoGrabber grabber;
    private ToggleableButton dirToggle;

    @Override
    public void setup() {
        grabber = new ServoGrabber(hardwareMap.get(Servo.class, "grabber"), Constants.TEAM1_GRABBER_CLOSED_POS, Constants.TEAM1_GRABBER_OPEN_POS);
        dirToggle = new ToggleableButton(GamepadButton.CROSS, false);
    }

    @Override
    public void loop() {
        XY joystickInfo = Inputs.getRightJoystickData();

        boolean isReversed = dirToggle.processTick();

        grabber.setServoReversed(isReversed);

        grabber.rotateTo(joystickInfo.y * 0.4);
        telemetry.addData("Joystick", joystickInfo.y);
        telemetry.addData("isReversed", isReversed);
        telemetry.addData("Pos", grabber.getRotation());

        telemetry.update();
    }
}
