package org.firstinspires.ftc.teamcode.opModes.testing;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.hardware.subsystems.DriveTrain;
import org.firstinspires.ftc.teamcode.hardware.subsystems.DriveTrainController;
import org.firstinspires.ftc.teamcode.hardware.subsystems.joystickMappings.CosMapping;
import org.firstinspires.ftc.teamcode.hardware.subsystems.joystickMappings.RootMapping;
import org.firstinspires.ftc.teamcode.inputs.GamepadButton;
import org.firstinspires.ftc.teamcode.inputs.Inputs;
import org.firstinspires.ftc.teamcode.inputs.XY;
import org.firstinspires.ftc.teamcode.inputs.inputs.DebouncedButton;
import org.firstinspires.ftc.teamcode.inputs.inputs.IncrementButtons;
import org.firstinspires.ftc.teamcode.wrappers.OpModeWrapper;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;

@TeleOp(name = "Puppeteer test autonomous", group = "Test")
public class PuppeteerTestAutonomous extends OpModeWrapper {
    DebouncedButton startDriveForward;
    DebouncedButton startTurn;
    DebouncedButton startDeposit;
    DebouncedButton startDeliver;

    PuppeteerTestAutonomousState state = PuppeteerTestAutonomousState.FORWARD;

    private DriveTrainController driveTrain;

    private long lastActionTime = -1;

    @Override
    public void setup(){
        // Inputs
        startDriveForward = new DebouncedButton(GamepadButton.D_UP);
        startTurn = new DebouncedButton(GamepadButton.D_LEFT);
        startDeposit = new DebouncedButton(GamepadButton.D_RIGHT);
        startDeliver = new DebouncedButton(GamepadButton.D_DOWN);

        // Actuators
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
        long currentTime = System.currentTimeMillis();
        if (lastActionTime < 0) lastActionTime = currentTime;

        boolean isSwitchToForward = startDriveForward.processTick();
        boolean isSwitchToTurn = startTurn.processTick();
        boolean isSwitchToDeposit = startDeposit.processTick();
        boolean isSwitchToDeliver = startDeliver.processTick();

        if (isSwitchToForward) {
            state = PuppeteerTestAutonomousState.FORWARD;
            driveTrain.resetEncoders();
        }
        if (isSwitchToTurn) {
            state = PuppeteerTestAutonomousState.TURN;
            driveTrain.resetEncoders();
        }
        if (isSwitchToDeposit) state = PuppeteerTestAutonomousState.DEPOSIT;
        if (isSwitchToDeliver) state = PuppeteerTestAutonomousState.DELIVER;

        telemetry.addData("Time since last action:", currentTime - lastActionTime);
        telemetry.addData("Current action:", state);

        XY leftJoystick = Inputs.getLeftJoystickData();

        if (isSwitchToForward || isSwitchToTurn || isSwitchToDeposit || isSwitchToDeliver)
            lastActionTime = currentTime;

        switch (state){
            case FORWARD:
                driveTrain.drive_unscaled(-leftJoystick.y, 0);
                telemetry.addData("Right pos", driveTrain.driveTrain.rightMotor.getCurrentPosition());
                telemetry.addData("Left pos", driveTrain.driveTrain.leftMotor.getCurrentPosition());
                break;
            case TURN:
                driveTrain.drive_unscaled(0, -leftJoystick.x);
                telemetry.addData("Right pos", driveTrain.driveTrain.rightMotor.getCurrentPosition());
                telemetry.addData("Left pos", driveTrain.driveTrain.leftMotor.getCurrentPosition());
                break;
            case DELIVER:
                break;
            case DEPOSIT:
                break;
        }

        telemetry.update();
    }
}
