package org.firstinspires.ftc.teamcode.opModes.testing;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.hardware.subsystems.DriveTrain;
import org.firstinspires.ftc.teamcode.hardware.subsystems.DriveTrainController;
import org.firstinspires.ftc.teamcode.hardware.subsystems.joystickMappings.CosMapping;
import org.firstinspires.ftc.teamcode.hardware.subsystems.joystickMappings.CubicMapping;
import org.firstinspires.ftc.teamcode.hardware.subsystems.joystickMappings.JoystickMapping;
import org.firstinspires.ftc.teamcode.hardware.subsystems.joystickMappings.LinearMapping;
import org.firstinspires.ftc.teamcode.hardware.subsystems.joystickMappings.RootMapping;
import org.firstinspires.ftc.teamcode.inputs.GamepadButton;
import org.firstinspires.ftc.teamcode.inputs.Inputs;
import org.firstinspires.ftc.teamcode.inputs.XY;
import org.firstinspires.ftc.teamcode.inputs.inputs.IncrementButtons;
import org.firstinspires.ftc.teamcode.wrappers.OpModeWrapper;

@TeleOp(name = "Test drivetrain curves", group = "Test")
public class TestDrivetrainCurves extends OpModeWrapper {
    DriveTrainController driveTrain;

    final JoystickMapping[] mappings = new JoystickMapping[] {
            new CosMapping(),
            new CubicMapping(),
            new LinearMapping(),
            new RootMapping(2),
            new RootMapping(3),
            new RootMapping(4),
    };

    final IncrementButtons speedMappingSelector = new IncrementButtons(GamepadButton.D_UP, GamepadButton.D_DOWN, 0, 1);
    final IncrementButtons turningMappingSelector = new IncrementButtons(GamepadButton.D_RIGHT, GamepadButton.D_LEFT, 0, 1);

    @Override
    public void setup(){
         driveTrain = new DriveTrainController(new DriveTrain(
                hardwareMap.get(DcMotor.class, "motor_0"),
                hardwareMap.get(DcMotor.class, "motor_1"),
                false
        ),
                 new RootMapping(2),
                 new CosMapping(),
                 0.0,
                 0.0
        );
    }

    @Override
    public void loop() {
        int speedMappingIndex = (int) speedMappingSelector.processTick();
        int turnMappingIndex = (int) turningMappingSelector.processTick();

        // make it very hard to overflow into the negatives, and make it wrap around

        speedMappingIndex = (speedMappingIndex + mappings.length * 1000) % mappings.length;
        turnMappingIndex = (turnMappingIndex + mappings.length * 1000) % mappings.length;

        driveTrain.setSpeedMapping(mappings[speedMappingIndex]);
        driveTrain.setTurnMapping(mappings[turnMappingIndex]);

        XY leftJoystick = Inputs.getLeftJoystickData();
        double speed = -leftJoystick.y;
        double turn = -leftJoystick.x;

        driveTrain.drive_scaled(speed, turn);

        telemetry.addData("speed", speed);
        telemetry.addData("turn", turn);
        telemetry.addData("Speed mapping:", driveTrain.getSpeedMapping().toString());
        telemetry.addData("Turn mapping:", driveTrain.getTurnMapping().toString());

        telemetry.update();
    }
}
