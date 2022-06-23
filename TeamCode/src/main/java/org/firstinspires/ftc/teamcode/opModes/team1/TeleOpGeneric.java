package org.firstinspires.ftc.teamcode.opModes.team1;

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
import org.firstinspires.ftc.teamcode.inputs.inputs.ToggleableButton;
import org.firstinspires.ftc.teamcode.wrappers.OpModeWrapper;

abstract class TeleOpGeneric extends OpModeWrapper {

    private DriveTrainController driveTrain;
    private CarouselSpinner spinner;
    private DiscretePositionArm arm;
    private ServoIntake intake;
    private ToggleableButton isArmRaisedButton;

    public void custom_setup() {
        isArmRaisedButton = new ToggleableButton(GamepadButton.CROSS, false);
        spinner = new CarouselSpinner(hardwareMap.get(DcMotor.class, "carousel_spinner"), false);
        driveTrain = new DriveTrainController(new DriveTrain(
                hardwareMap.get(DcMotor.class, "left_drivetrain_motor"),
                hardwareMap.get(DcMotor.class, "right_drivetrain_motor"),
                false
        ),
                Constants.TEAM1_DRIVETRAIN_COUNTS_PER_RADIAN,
                Constants.TEAM1_DRIVETRAIN_COUNTS_PER_METER,
                new CosMapping()
        );
        arm = new DiscretePositionArm(
                hardwareMap.get(DcMotor.class, "arm"),
                false,
                Constants.TEAM1_ARM_FRONT_COUNTS,
                Constants.TEAM1_ARM_BACK_COUNTS
        );
        intake = new ServoIntake(hardwareMap.get(CRServo.class, "intake"), false);
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
        double intakeSpeed = 0.0;
        if(Inputs.isPressed(GamepadButton.D_UP)) {
            intakeSpeed = 1.0;
        } else if(Inputs.isPressed(GamepadButton.D_DOWN)) {
            intakeSpeed = -1.0;
        }
        telemetry.addData("Intake speed", intakeSpeed);
        intake.spin(intakeSpeed);

        /* Arm */
        // CONTROLS: Cross to toggle
        if(isArmRaisedButton.processTick()) arm.moveToBack(Constants.TEAM1_ARM_SPEED);
        else arm.moveToFront(Constants.TEAM1_ARM_SPEED);

        /* Drivetrain */
        // CONTROLS: Left joystick
        XY leftJoystick = Inputs.getLeftJoystickData();
        driveTrain.drive_scaled(-leftJoystick.y, leftJoystick.x);

        telemetry.update();
    }
}
