package org.firstinspires.ftc.teamcode.opModes.team1;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
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
import org.firstinspires.ftc.teamcode.inputs.inputs.DebouncedButton;
import org.firstinspires.ftc.teamcode.wrappers.OpModeWrapper;

@TeleOp(name = "Team 1 - TeleOp Blue", group = "1_TeleOp")
public class TeleOpBlue extends OpModeWrapper {

    private DriveTrainController driveTrain;
    private CarouselSpinner spinner;
    private FlipsOverArm arm;
    private ServoIntake intake;

    @Override
    public void setup() {
        spinner = new CarouselSpinner(hardwareMap.get(DcMotor.class, "carousel_spinner"), false);
        driveTrain = new DriveTrainController(new DriveTrain(
                hardwareMap.get(DcMotor.class, "left_drivetrain_motor"),
                hardwareMap.get(DcMotor.class, "right_drivetrain_motor"),
                false
        ),
                Constants.TEAM1_DRIVETRAIN_COUNTS_PER_RADIAN,
                Constants.TEAM1_DRIVETRAIN_COUNTS_PER_METER
        );
        arm = new FlipsOverArm(
                hardwareMap.get(DcMotor.class, "arm"),
                false,
                Constants.TEAM1_ARM_COUNTS_PER_RADIAN,
                Constants.TEAM1_ARM_FRONT_ANGLE,
                Constants.TEAM1_ARM_BACK_ANGLE
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

    @Override
    public void loop() {
        /* Carousel Spinner*/
        // CONTROLS: use the triggers to rotate left or right
        double spinnerSpeed = Inputs.getRightTriggerData() - Inputs.getLeftTriggerData();
        spinner.spin(spinnerSpeed);

        /* Intake servo*/
        // CONTROLS: use the direction pad up/down
        double intakeSpeed = 0.0;
        if(Inputs.isPressed(GamepadButton.D_UP)) {
            intakeSpeed = 1.0;
        } else if(Inputs.isPressed(GamepadButton.D_DOWN)) {
            intakeSpeed = -1.0;
        }
        intake.spin(intakeSpeed);


        /* Arm */
        // CONTROLS: Cross to toggle
        if(Inputs.isPressed(GamepadButton.CROSS)) {
            arm.togglePosition(Constants.TEAM1_ARM_SPEED);
        }
        arm.moveToFront(Constants.TEAM1_ARM_SPEED); // Hover

        /* Drivetrain */
        // CONTROLS: Left joystick
        XY leftJoystick = Inputs.getLeftJoystickData();
        driveTrain.drive(-leftJoystick.y, leftJoystick.x);

        telemetry.update();
    }
}
