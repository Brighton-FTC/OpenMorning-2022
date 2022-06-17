package org.firstinspires.ftc.teamcode.opModes.testing;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.hardware.subsystems.DriveTrain;
import org.firstinspires.ftc.teamcode.hardware.subsystems.DriveTrainController;
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
@TeleOp(name = "Test motors", group = "Test")
public class TestMotors extends OpModeWrapper {


    String[] motorNames = {
            "motor_0",
            "motor_1",
            "motor_2",
            "motor_3",
    };  int motorId = 0;

    String[] servoNames = {
            "servo_0",
            "servo_1",
            "servo_2",
            "servo_3",
    };
    CRServo[] crServos;
    DcMotor[] motors;
    DebouncedButton[] selectionButtons;
    DebouncedButton resetDrivetrainEncoders;
    ToggleableButton selectServosToggleButton;
    ToggleableButton driveToggleButton;
    ToggleableButton driveTrainLockHorizontalComponentButton;

    DriveTrainController driveTrain;
    int driveLeftLastPos;
    int driveRightLastPos;

    @Override
    public void setup() {
        motors = new DcMotor[4];
        crServos = new CRServo[4];

        resetDrivetrainEncoders = new DebouncedButton(GamepadButton.CIRCLE);
        driveTrainLockHorizontalComponentButton = new ToggleableButton(GamepadButton.CROSS, false);

        selectServosToggleButton = new ToggleableButton(GamepadButton.R_BUMPER, false);
        driveToggleButton = new ToggleableButton(GamepadButton.L_BUMPER, true);

        driveTrain = new DriveTrainController(new DriveTrain(
                hardwareMap.get(DcMotor.class, motorNames[0]),
                hardwareMap.get(DcMotor.class, motorNames[1]),
                false
        ),
                Constants.TEAM1_DRIVETRAIN_COUNTS_PER_RADIAN,
                Constants.TEAM1_DRIVETRAIN_COUNTS_PER_METER
        );
        // Number of counts since start
        driveLeftLastPos = driveTrain.driveTrain.leftMotor.getCurrentPosition();
        driveRightLastPos = driveTrain.driveTrain.leftMotor.getCurrentPosition();

        for (int i = 0; i < motorNames.length; i++) {
            crServos[i] = hardwareMap.get(CRServo.class, servoNames[i]);
        }
        for (int i = 0; i < motorNames.length; i++) {
            motors[i] = hardwareMap.get(DcMotor.class, motorNames[i]);
        }
        selectionButtons = new DebouncedButton[] {
                new DebouncedButton(GamepadButton.TRIANGLE),
                new DebouncedButton(GamepadButton.CIRCLE),
                new DebouncedButton(GamepadButton.CROSS),
                new DebouncedButton(GamepadButton.SQUARE)
        };
    }

    @Override
    public void loop() {
        boolean isDriving = driveToggleButton.processTick();
        if (isDriving) {
            XY leftJoystick = Inputs.getLeftJoystickData();

            if (resetDrivetrainEncoders.processTick()) {
                // Reset position
                driveLeftLastPos = driveTrain.driveTrain.leftMotor.getCurrentPosition();
                driveRightLastPos = driveTrain.driveTrain.rightMotor.getCurrentPosition();
            }

            double speed = -leftJoystick.y * 0.5;
            double turn = leftJoystick.x;

            if (driveTrainLockHorizontalComponentButton.processTick()) turn = 0.0;

            telemetry.addLine("Driving");

            driveTrain.drive_scaled(speed, turn);

            telemetry.addData("Drivetrain L Counts", driveTrain.driveTrain.leftMotor.getCurrentPosition() - driveLeftLastPos);
            telemetry.addData("Drivetrain R Counts", driveTrain.driveTrain.rightMotor.getCurrentPosition() - driveRightLastPos);

            telemetry.update();
            return;
        }

        boolean isSelectingServos = selectServosToggleButton.processTick();
        telemetry.addData("Selected "+ (isSelectingServos ? "servo": "motor") + ": ", (isSelectingServos ? servoNames : motorNames)[motorId]);
        telemetry.addLine("Setting motor powers");
        telemetry.addLine("Press triangle/circle/etc buttons");

        // select one of the motors
        for (int i = 0; i < selectionButtons.length; i++) if (selectionButtons[i].processTick()) motorId = i;

        double power = Inputs.getRightJoystickData().y;
        if (isSelectingServos) {
            CRServo servo = crServos[motorId];

            servo.setPower(power);

            telemetry.addData("Power", power);
        } else {
            DcMotor motor = motors[motorId];
            motor.setPower(power);
            telemetry.addData("Power", power);
            telemetry.addData("Counts number", motor.getCurrentPosition());
        }

        telemetry.update();
    }
}
