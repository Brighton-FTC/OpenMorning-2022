package org.firstinspires.ftc.teamcode.opModes.team2;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.hardware.subsystems.CarouselSpinner;
import org.firstinspires.ftc.teamcode.hardware.subsystems.DriveTrain;
import org.firstinspires.ftc.teamcode.hardware.subsystems.DriveTrainController;
import org.firstinspires.ftc.teamcode.hardware.subsystems.FlipsOverArm;
import org.firstinspires.ftc.teamcode.hardware.subsystems.ServoIntake;
import org.firstinspires.ftc.teamcode.inputs.GamepadButton;
import org.firstinspires.ftc.teamcode.inputs.Inputs;
import org.firstinspires.ftc.teamcode.inputs.XY;
import org.firstinspires.ftc.teamcode.inputs.inputs.ToggleableButton;
import org.firstinspires.ftc.teamcode.wrappers.OpModeWrapper;

abstract class TeleOpGeneric extends OpModeWrapper {

    private DriveTrainController driveTrain;
    private CarouselSpinner spinner;
    private FlipsOverArm arm;
    private ServoIntake intake;
    private ToggleableButton isArmRaisedButton;
    private ToggleableButton intakeModeToggle;

    public void custom_setup() {
        isArmRaisedButton = new ToggleableButton(GamepadButton.CROSS, false);
        intakeModeToggle = new ToggleableButton(GamepadButton.SQUARE, false);
        spinner = new CarouselSpinner(hardwareMap.get(DcMotor.class, "carousel_spinner"), false);
        driveTrain = new DriveTrainController(new DriveTrain(
                hardwareMap.get(DcMotor.class, "left_drivetrain_motor"),
                hardwareMap.get(DcMotor.class, "right_drivetrain_motor"),
                false
        ),
                Constants.TEAM2_DRIVETRAIN_COUNTS_PER_RADIAN,
                Constants.TEAM2_DRIVETRAIN_COUNTS_PER_METER
        );
        arm = new FlipsOverArm(
                hardwareMap.get(DcMotor.class, "slide"),
                false,
                Constants.TEAM2_SLIDE_FRONT_COUNTS,
                Constants.TEAM2_SLIDE_BACK_COUNTS
        );
        intake = new ServoIntake(hardwareMap.get(CRServo.class, "intake"), true);
    }

    /**
     * First movement done here.
     */
    @Override
    public void start() {
        arm.moveToFront(Constants.TEAM2_SLIDE_SPEED); // Hover
    }

    public void custom_loop(boolean isSpinnerInverted) {
        /* Carousel Spinner*/
        // CONTROLS: use the triggers to rotate left or right
        double spinnerSpeed = Inputs.getRightTriggerData() - Inputs.getLeftTriggerData();
        if (isSpinnerInverted) spinnerSpeed *= -1;
        spinner.spin(spinnerSpeed);

        /* Intake servo*/
        // CONTROLS: use the direction pad up/down

        if (intakeModeToggle.processTick()) {
            intake.spin(Constants.TEAM2_INTAKE_SPEED);
            arm.powerDown();
        }
        else {
            intake.spin(0.0);
            // Toggle arm position
            if(isArmRaisedButton.processTick()) arm.moveToBack(Constants.TEAM2_SLIDE_SPEED);
            else arm.moveToFront(Constants.TEAM2_SLIDE_SPEED);
        }

        /* Drivetrain */
        // CONTROLS: Left joystick
        XY leftJoystick = Inputs.getLeftJoystickData();
        driveTrain.drive_scaled(-leftJoystick.y, leftJoystick.x);

        telemetry.update();
    }
}
