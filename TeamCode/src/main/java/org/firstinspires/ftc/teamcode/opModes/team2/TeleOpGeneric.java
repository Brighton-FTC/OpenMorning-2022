package org.firstinspires.ftc.teamcode.opModes.team2;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.hardware.subsystems.CarouselSpinner;
import org.firstinspires.ftc.teamcode.hardware.subsystems.DriveTrain;
import org.firstinspires.ftc.teamcode.hardware.subsystems.DriveTrainController;
import org.firstinspires.ftc.teamcode.hardware.subsystems.DiscretePositionArm;
import org.firstinspires.ftc.teamcode.hardware.subsystems.ServoIntake;
import org.firstinspires.ftc.teamcode.hardware.subsystems.joystickMappings.CosMapping;
import org.firstinspires.ftc.teamcode.inputs.GamepadButton;
import org.firstinspires.ftc.teamcode.inputs.Inputs;
import org.firstinspires.ftc.teamcode.inputs.XY;
import org.firstinspires.ftc.teamcode.inputs.inputs.DebouncedButton;
import org.firstinspires.ftc.teamcode.inputs.inputs.ToggleableButton;
import org.firstinspires.ftc.teamcode.wrappers.OpModeWrapper;

abstract class TeleOpGeneric extends OpModeWrapper {

    private DriveTrainController driveTrain;
    private CarouselSpinner spinner;
    private DiscretePositionArm arm;
    private ServoIntake intake;
    
    DebouncedButton raiseArmButton;
    DebouncedButton floatArmButton;
    DebouncedButton powerDownArmButton;
    ToggleableButton isIntakeEnabledButton;

    public void custom_setup() {
        raiseArmButton = new DebouncedButton(GamepadButton.TRIANGLE);
        floatArmButton = new DebouncedButton(GamepadButton.CIRCLE);
        powerDownArmButton = new DebouncedButton(GamepadButton.CROSS);
        isIntakeEnabledButton = new ToggleableButton(GamepadButton.SQUARE, false);
        spinner = new CarouselSpinner(hardwareMap.get(DcMotor.class, "carousel_spinner"), false);
        driveTrain = new DriveTrainController(new DriveTrain(
                hardwareMap.get(DcMotor.class, "left_drivetrain_motor"),
                hardwareMap.get(DcMotor.class, "right_drivetrain_motor"),
                false
        ),
                Constants.TEAM2_DRIVETRAIN_COUNTS_PER_RADIAN,
                Constants.TEAM2_DRIVETRAIN_COUNTS_PER_METER,
                new CosMapping(),
                new CosMapping()
        );
        arm = new DiscretePositionArm(
                hardwareMap.get(DcMotor.class, "slide"),
                false,
                Constants.TEAM2_SLIDE_FRONT_COUNTS,
                Constants.TEAM2_SLIDE_BACK_COUNTS
        );
//        arm.setPower(0.5);
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

        if(raiseArmButton.processTick()) {
            arm.moveToBack(Constants.TEAM2_SLIDE_SPEED);
            isIntakeEnabledButton.set(false);
        }
        if(floatArmButton.processTick()) arm.moveToFront(Constants.TEAM2_SLIDE_SPEED);
        if(powerDownArmButton.processTick()) {
            arm.powerDown();
            isIntakeEnabledButton.set(true);
        }

        if (isIntakeEnabledButton.processTick()) intake.spin(Constants.TEAM2_INTAKE_SPEED);
        else intake.spin(0.0);

        // if arm powered down, slow down for more control
        boolean isArmPoweredDown = arm.getPower() == 0;

        double speedMultiplier = isArmPoweredDown ? 0.5 : 1.0;
        double turnMultiplier = isArmPoweredDown ? 0.5 : 1.0;

        /* Drivetrain */
        // CONTROLS: Left joystick
        XY leftJoystick = Inputs.getLeftJoystickData();
        driveTrain.drive_scaled(-leftJoystick.y * speedMultiplier, -leftJoystick.x * turnMultiplier);

        telemetry.update();
    }
}
