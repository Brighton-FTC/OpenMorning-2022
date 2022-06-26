package org.firstinspires.ftc.teamcode.opModes.testing;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.hardware.subsystems.DriveTrain;
import org.firstinspires.ftc.teamcode.hardware.subsystems.DriveTrainController;
import org.firstinspires.ftc.teamcode.hardware.subsystems.joystickMappings.CosMapping;
import org.firstinspires.ftc.teamcode.inputs.GamepadButton;
import org.firstinspires.ftc.teamcode.inputs.inputs.IncrementButtons;
import org.firstinspires.ftc.teamcode.inputs.inputs.ToggleableButton;
import org.firstinspires.ftc.teamcode.wrappers.OpModeWrapper;

@TeleOp(name = "Test turn counts", group = "Test")
public class TestTurnCounts extends OpModeWrapper {
    IncrementButtons numCountsSelector;
    ToggleableButton driveToggle;
    DriveTrainController driveTrain;
    double SPEED = 0.5;

    @Override
    public void setup(){
        // Inputs
        numCountsSelector = new IncrementButtons(GamepadButton.D_UP, GamepadButton.D_DOWN, 100, 10);
        driveToggle = new ToggleableButton(GamepadButton.TRIANGLE, false);
        // Actuators
        driveTrain = new DriveTrainController(new DriveTrain(
                hardwareMap.get(DcMotor.class, "motor_0"),
                hardwareMap.get(DcMotor.class, "motor_1"),
                false
        ),
                Constants.TEAM1_DRIVETRAIN_COUNTS_PER_RADIAN,
                Constants.TEAM1_DRIVETRAIN_COUNTS_PER_METER,
                new CosMapping(),
                new CosMapping()
        );
    }

    @Override
    public void loop() {
        // Get inputs
        int numCounts = (int) numCountsSelector.processTick();
        boolean isDriving = driveToggle.processTick();

        if(isDriving) {
            // Display if driving
            telemetry.addLine("Turning");
            telemetry.update();
            // Drive
            driveTrain.startDrivingCounts(numCounts, -numCounts, SPEED); // One forwards, one back
        } else {
            // Display if not driving
            telemetry.addData("Number of counts to turn by (pos. is clockwise, neg. is anticlockwise)", numCounts);
            telemetry.addLine("Use the DPAD Up/Down to change num counts");
            telemetry.addLine("Triangle to turn");
            telemetry.update();
        }
    }
}
