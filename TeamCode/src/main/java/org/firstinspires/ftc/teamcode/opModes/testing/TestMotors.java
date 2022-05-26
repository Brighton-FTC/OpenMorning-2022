package org.firstinspires.ftc.teamcode.opModes.testing;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.inputs.GamepadButton;
import org.firstinspires.ftc.teamcode.inputs.Inputs;
import org.firstinspires.ftc.teamcode.inputs.inputs.DebouncedButton;
import org.firstinspires.ftc.teamcode.wrappers.OpModeWrapper;

@TeleOp(name = "Test motors", group = "Test")
public class TestMotors extends OpModeWrapper {

    int motorId = 0;

    String[] motorNames = {
            "motor_0",
            "motor_1",
            "motor_2",
            "motor_3",
    };
    DcMotor[] motors;
    DebouncedButton[] motorButtons;

    @Override
    public void setup() {
        motors = new DcMotor[4];
        for (int i = 0; i < motorNames.length; i++) {
            motors[i] = hardwareMap.get(DcMotor.class, motorNames[i]);
        }
        motorButtons = new DebouncedButton[] {
                new DebouncedButton(GamepadButton.TRIANGLE),
                new DebouncedButton(GamepadButton.CIRCLE),
                new DebouncedButton(GamepadButton.CROSS),
                new DebouncedButton(GamepadButton.SQUARE)
        };
    }

    @Override
    public void loop() {
        telemetry.addData("Selected motor: ", motorNames[motorId]);
        telemetry.addLine("Press triangle/circle/etc buttons");

        // select one of the motors
        for (int i = 0; i < motorButtons.length; i++) if (motorButtons[i].processTick()) motorId = i;

        DcMotor motor = motors[motorId];
        double power = Inputs.getRightJoystickData().y;
        telemetry.addData("Power", power);
        motor.setPower(power);

        telemetry.update();
    }
}
