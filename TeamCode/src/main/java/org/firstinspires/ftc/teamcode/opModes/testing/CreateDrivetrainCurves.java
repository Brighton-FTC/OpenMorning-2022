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

/**
 * An opmode for testing motors
 * Press L_BUMPER to toggle drivetrain operation (note that it might override motors 0 and 1
 * Drivetrain:
 *  Left joystick to control
 *  Circle to reset counts on telemetry
 * Motors:
 *  Press R_BUMPER to toggle between selecting motors and servos
 *  Press the circle, square, etc. buttons to select a motor
 *  Cross to switch off motor
 *  Move the left joystick to change the power given to the motor;
 *  if you change motors with the joystick held, the motor will stay at that power
 */
@TeleOp(name = "Create drivetrain curves", group = "Test")
public class CreateDrivetrainCurves extends OpModeWrapper {
    DriveTrainController driveTrain;
    @Override
    public void setup() {
        driveTrain = new DriveTrainController(new DriveTrain(
                hardwareMap.get(DcMotor.class, "motor_0"),
                hardwareMap.get(DcMotor.class, "motor_1"),
                false
        ),
                Constants.TEAM1_DRIVETRAIN_COUNTS_PER_RADIAN,
                Constants.TEAM1_DRIVETRAIN_COUNTS_PER_METER,
                new LinearMapping()
        );
    }

    @Override
    public void loop() {
        XY leftJoystick = Inputs.getLeftJoystickData();
        XY rightJoystick = Inputs.getRightJoystickData();
        double speed = -leftJoystick.y;
        double turn = leftJoystick.x;

        driveTrain.drive_scaled(speed, turn);

        telemetry.addData("Speed", speed);
        telemetry.addData("Turn", turn);
        telemetry.addData("Current x", leftJoystick.x);
        telemetry.addData("Wanted x", rightJoystick.x);
        telemetry.addData("Current y", leftJoystick.y);
        telemetry.addData("Wanted y", rightJoystick.y);

        telemetry.update();
    }
}
