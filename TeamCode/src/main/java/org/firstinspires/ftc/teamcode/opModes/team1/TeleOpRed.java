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
import org.firstinspires.ftc.teamcode.inputs.inputs.ToggleableButton;
import org.firstinspires.ftc.teamcode.wrappers.OpModeWrapper;

@TeleOp(name = "Team 1 - TeleOp Red", group = "1_TeleOp")
public class TeleOpRed extends TeleOpGeneric {
    @Override
    public void setup() {
        custom_setup();
    }

    @Override
    public void loop() {
        custom_loop(true);
    }
}
