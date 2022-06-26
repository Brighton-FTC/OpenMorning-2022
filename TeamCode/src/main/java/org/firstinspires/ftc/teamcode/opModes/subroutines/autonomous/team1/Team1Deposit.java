package org.firstinspires.ftc.teamcode.opModes.subroutines.autonomous.team1;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.hardware.subsystems.DiscretePositionArm;
import org.firstinspires.ftc.teamcode.hardware.subsystems.ServoGrabber;

public class Team1Deposit {
    public void run(LinearOpMode opMode, DiscretePositionArm arm, ServoGrabber grabber) throws InterruptedException {
        // Get hardwareMap
        HardwareMap hardwareMap = opMode.hardwareMap;

        arm.moveToBack(Constants.TEAM1_ARM_SPEED);
        while (arm.isBusy()) opMode.sleep(50);

        grabber.setClosed(false);

        opMode.sleep(700);

        grabber.setClosed(true);

        arm.moveToFront(Constants.TEAM1_ARM_SPEED);
        while (arm.isBusy()) opMode.sleep(50);
    }
}
