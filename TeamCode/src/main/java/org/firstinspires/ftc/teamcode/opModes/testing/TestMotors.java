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

@TeleOp(name = "Test motors", group = "Test")
public class TestMotors extends OpModeWrapper {

    int motorId = 0;

    String[] motorNames = {
            "motor_0",
            "motor_1",
            "motor_2",
            "motor_3",
    };
    String[] servoNames = {
            "servo_0",
            "servo_1",
            "servo_2",
            "servo_3",
    };
    CRServo[] crServos;
    DcMotor[] motors;
    DebouncedButton[] selectionButtons;
    ToggleableButton drivetrainToggleButton;

    DriveTrainController driveTrain;

    @Override
    public void setup() {
        motors = new DcMotor[4];
        crServos = new CRServo[4];

        drivetrainToggleButton = new ToggleableButton(GamepadButton.L_BUMPER, false);

        driveTrain = new DriveTrainController(new DriveTrain(
                hardwareMap.get(DcMotor.class, motorNames[0]),
                hardwareMap.get(DcMotor.class, motorNames[1]),
                false
        ),
                Constants.TEAM1_DRIVETRAIN_COUNTS_PER_RADIAN,
                Constants.TEAM1_DRIVETRAIN_COUNTS_PER_METER
        );

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
        boolean drivetrainButton = drivetrainToggleButton.processTick();
        boolean isSelectingServos = Inputs.isPressed(GamepadButton.R_BUMPER);
        telemetry.addData("Selected "+ (isSelectingServos ? "servo": "motor") + ": ", (isSelectingServos ? servoNames : motorNames)[motorId]);
        telemetry.addData("Drivetrain enabled?", drivetrainButton);
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

        if (drivetrainButton) {
            XY leftJoystick = Inputs.getLeftJoystickData();
            driveTrain.drive(-leftJoystick.y, leftJoystick.x);
        }

        telemetry.update();
    }
}
