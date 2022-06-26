package org.firstinspires.ftc.teamcode.opModes.team1;

import static org.firstinspires.ftc.teamcode.Constants.TEAM1_ARM_POSITION_EPSILON;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.hardware.subsystems.CarouselSpinner;
import org.firstinspires.ftc.teamcode.hardware.subsystems.DiscretePositionArm;
import org.firstinspires.ftc.teamcode.hardware.subsystems.DriveTrain;
import org.firstinspires.ftc.teamcode.hardware.subsystems.DriveTrainController;
import org.firstinspires.ftc.teamcode.hardware.subsystems.ServoGrabber;
import org.firstinspires.ftc.teamcode.hardware.subsystems.joystickMappings.CosMapping;
import org.firstinspires.ftc.teamcode.hardware.subsystems.joystickMappings.RootMapping;
import org.firstinspires.ftc.teamcode.inputs.GamepadButton;
import org.firstinspires.ftc.teamcode.inputs.Inputs;
import org.firstinspires.ftc.teamcode.inputs.XY;
import org.firstinspires.ftc.teamcode.inputs.inputs.DebouncedButton;
import org.firstinspires.ftc.teamcode.inputs.inputs.ToggleableButton;
import org.firstinspires.ftc.teamcode.libs.util.Maths;
import org.firstinspires.ftc.teamcode.wrappers.OpModeWrapper;

abstract class TeleOpGeneric extends OpModeWrapper {

    private DriveTrainController driveTrain;
    private CarouselSpinner spinner;
    private DiscretePositionArm arm;
    private ServoGrabber grabber;

    Team1ArmState armState = Team1ArmState.FLOATING;

    DebouncedButton raiseArmButton;
    DebouncedButton floatArmButton;
    DebouncedButton powerDownArmButton;
    ToggleableButton grabberToggle;

    public void custom_setup() {
        raiseArmButton = new DebouncedButton(GamepadButton.TRIANGLE);
        floatArmButton = new DebouncedButton(GamepadButton.CIRCLE);
        powerDownArmButton = new DebouncedButton(GamepadButton.CROSS);
        grabberToggle = new ToggleableButton(GamepadButton.R_BUMPER, false);
        spinner = new CarouselSpinner(hardwareMap.get(DcMotor.class, "carousel_spinner"), false);
        driveTrain = new DriveTrainController(new DriveTrain(
                hardwareMap.get(DcMotor.class, "left_drivetrain_motor"),
                hardwareMap.get(DcMotor.class, "right_drivetrain_motor"),
                true
        ),
                new RootMapping(2),
                new CosMapping(),
                0.0,
                0.0
        );
        arm = new DiscretePositionArm(
                hardwareMap.get(DcMotor.class, "arm"),
                false,
                Constants.TEAM1_ARM_FRONT_COUNTS,
                Constants.TEAM1_ARM_BACK_COUNTS
        );
        grabber = new ServoGrabber(hardwareMap.get(Servo.class, "grabber"), Constants.TEAM1_GRABBER_CLOSED_POS, Constants.TEAM1_GRABBER_OPEN_POS);
    }

    /**
     * First movement done here.
     */
    @Override
    public void start() {
        arm.moveToFront(Constants.TEAM1_ARM_SPEED); // Hover
    }

    public void custom_loop(boolean isSpinnerInverted) {
        /* Carousel Spinner*/
        // CONTROLS: use the triggers to rotate left or right
        double spinnerSpeed = Inputs.getRightTriggerData() - Inputs.getLeftTriggerData();
        if (isSpinnerInverted) spinnerSpeed *= -1;
        spinner.spin(spinnerSpeed);

        /* Intake servo*/
        // CONTROLS: use the direction pad up/down

        if (raiseArmButton.processTick()) armState = Team1ArmState.FLIPPED;
        if (floatArmButton.processTick()) armState = Team1ArmState.FLOATING;
        if (powerDownArmButton.processTick()) armState = Team1ArmState.ON_GROUND;

        switch (armState) {
            case FLIPPED:
                arm.moveToBack(Constants.TEAM1_ARM_SPEED);
                break;
            case FLOATING:
                arm.moveToFront(Constants.TEAM1_ARM_SPEED);
                break;
            case ON_GROUND:
                arm.moveToCountsAndPowerDown(0, Constants.TEAM1_ARM_SPEED, TEAM1_ARM_POSITION_EPSILON);
                break;
        }

        grabber.setClosed(grabberToggle.processTick());

        // if arm powered down, slow down for more control
        boolean isArmPoweredDown = armState == Team1ArmState.ON_GROUND;

        double scale = isArmPoweredDown ? 0.5 : 1.0;

        /* Drivetrain */
        // CONTROLS: Left joystick
        XY leftJoystick = Inputs.getLeftJoystickData();
        XY rightJoystick = Inputs.getRightJoystickData();

        double speed = Maths.clamp(-leftJoystick.y - rightJoystick.y, -1, 1);
        double turn = Maths.clamp(-leftJoystick.x + rightJoystick.x, -1, 1);

        driveTrain.drive_scaled(speed, turn, scale, 0.75);

        telemetry.update();
    }
}
