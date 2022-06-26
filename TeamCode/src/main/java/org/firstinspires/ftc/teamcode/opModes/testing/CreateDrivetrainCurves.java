package org.firstinspires.ftc.teamcode.opModes.testing;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.hardware.subsystems.DriveTrain;
import org.firstinspires.ftc.teamcode.hardware.subsystems.DriveTrainController;
import org.firstinspires.ftc.teamcode.hardware.subsystems.joystickMappings.CosMapping;
import org.firstinspires.ftc.teamcode.hardware.subsystems.joystickMappings.LinearMapping;
import org.firstinspires.ftc.teamcode.inputs.GamepadButton;
import org.firstinspires.ftc.teamcode.inputs.Inputs;
import org.firstinspires.ftc.teamcode.inputs.XY;
import org.firstinspires.ftc.teamcode.inputs.inputs.DebouncedButton;
import org.firstinspires.ftc.teamcode.inputs.inputs.ToggleableButton;
import org.firstinspires.ftc.teamcode.wrappers.OpModeWrapper;

@TeleOp(name = "Create drivetrain curves", group = "Test")
public class CreateDrivetrainCurves extends OpModeWrapper {
    DriveTrainController driveTrain;

    DebouncedButton freezeReadings;

    double storedX;
    double storedY;
    double storedDesiredX;
    double storedDesiredY;

    @Override
    public void setup() {
        driveTrain = new DriveTrainController(new DriveTrain(
                hardwareMap.get(DcMotor.class, "motor_0"),
                hardwareMap.get(DcMotor.class, "motor_1"),
                false
        ),
                Constants.TEAM1_DRIVETRAIN_COUNTS_PER_RADIAN,
                Constants.TEAM1_DRIVETRAIN_COUNTS_PER_METER,
                new LinearMapping(),
                new LinearMapping()
        );
        freezeReadings = new DebouncedButton(GamepadButton.R_BUMPER);
    }

    @Override
    public void loop() {
        XY leftJoystick = Inputs.getLeftJoystickData();
        XY rightJoystick = Inputs.getRightJoystickData();
        double speed = -leftJoystick.y;
        double turn = leftJoystick.x;

        driveTrain.drive_scaled(speed, turn);

        telemetry.addData("x", leftJoystick.x);
        telemetry.addData("Desired x", rightJoystick.x);
        telemetry.addData("y", -leftJoystick.y);
        telemetry.addData("Desired y", -rightJoystick.y);

        if(freezeReadings.processTick()) {
            storedX = leftJoystick.x;
            storedY = -leftJoystick.y;
            storedDesiredX = rightJoystick.x;
            storedDesiredY = -rightJoystick.y;
        }

        telemetry.addData("Stored x", storedX);
        telemetry.addData("Stored desired x", storedDesiredX);
        telemetry.addData("Stored y", storedY);
        telemetry.addData("Stored desired y", storedDesiredY);

        telemetry.update();
    }
}
