package org.firstinspires.ftc.teamcode.opModes.testing;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.Timer;
import org.firstinspires.ftc.teamcode.hardware.subsystems.CarouselSpinner;
import org.firstinspires.ftc.teamcode.hardware.subsystems.DiscretePositionArm;
import org.firstinspires.ftc.teamcode.hardware.subsystems.DriveTrain;
import org.firstinspires.ftc.teamcode.hardware.subsystems.DriveTrainController;
import org.firstinspires.ftc.teamcode.hardware.subsystems.joystickMappings.CosMapping;
import org.firstinspires.ftc.teamcode.hardware.subsystems.joystickMappings.RootMapping;
import org.firstinspires.ftc.teamcode.inputs.GamepadButton;
import org.firstinspires.ftc.teamcode.inputs.Inputs;
import org.firstinspires.ftc.teamcode.inputs.XY;
import org.firstinspires.ftc.teamcode.inputs.inputs.DebouncedButton;
import org.firstinspires.ftc.teamcode.inputs.inputs.ToggleableButton;
import org.firstinspires.ftc.teamcode.opModes.subroutines.autonomous.team2.Team2Deposit;
import org.firstinspires.ftc.teamcode.wrappers.LinearOpModeWrapper;
import org.firstinspires.ftc.teamcode.wrappers.OpModeWrapper;

@TeleOp(name = "Puppeteer test autonomous", group = "Test")
public class PuppeteerTestAutonomous extends LinearOpModeWrapper {
    DebouncedButton startDriveForward;
    DebouncedButton startTurn;
    DebouncedButton startDeposit;
    DebouncedButton startDeliver;

    ToggleableButton isTeam1Button;

    PuppeteerTestAutonomousState state = PuppeteerTestAutonomousState.FORWARD;

    private DriveTrainController driveTrain;
    private CarouselSpinner spinner;

    private long lastActionTime = 0;

    private final Timer actionTimer = new Timer();


    @Override
    public void run() throws InterruptedException {
        // Inputs
        isTeam1Button = new ToggleableButton(GamepadButton.R_BUMPER, true);
        startDriveForward = new DebouncedButton(GamepadButton.D_UP);
        startTurn = new DebouncedButton(GamepadButton.D_LEFT);
        startDeposit = new DebouncedButton(GamepadButton.D_RIGHT);
        startDeliver = new DebouncedButton(GamepadButton.D_DOWN);

        spinner = new CarouselSpinner(hardwareMap.get(DcMotor.class, "motor_3"), false);

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

        waitForStart();

        while (opModeIsActive()) {
            long currentTime = System.currentTimeMillis();

            boolean isTeam1 = isTeam1Button.processTick();

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

            telemetry.addData("Is team 1:", isTeam1);
            telemetry.addData("Time to complete the last action:", lastActionTime);
            telemetry.addData("Time since last action:", actionTimer.getTimePassed());
            telemetry.addData("Current action:", state);

            XY leftJoystick = Inputs.getLeftJoystickData();

            if (isSwitchToForward || isSwitchToTurn || isSwitchToDeposit || isSwitchToDeliver)
            {
                lastActionTime = actionTimer.getTimePassed();
                // stop all
                spinner.spin(0);

                actionTimer.reset();
            }

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
                    spinner.spin(0.5);
                    break;
                case DEPOSIT:
                    if (isTeam1){

                    } else {
                        new Team2Deposit().run(this,
                                new DiscretePositionArm(
                                        hardwareMap.get(DcMotor.class, "motor_2"),
                                        false,
                                        Constants.TEAM2_SLIDE_FRONT_COUNTS,
                                        Constants.TEAM2_SLIDE_BACK_COUNTS
                                )
                        );
                    }
                    state = PuppeteerTestAutonomousState.FORWARD;
                    break;
            }

            telemetry.update();
            idle();
        }
    }
}
