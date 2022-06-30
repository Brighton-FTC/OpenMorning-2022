package org.firstinspires.ftc.teamcode.opModes.subroutines.autonomous.team2;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.Timer;
import org.firstinspires.ftc.teamcode.hardware.subsystems.CarouselSpinner;
import org.firstinspires.ftc.teamcode.hardware.subsystems.DiscretePositionArm;

public class Team2Deposit {
    public void run(LinearOpMode opMode, DiscretePositionArm arm) throws InterruptedException {
        // Get hardwareMap
        HardwareMap hardwareMap = opMode.hardwareMap;

        arm.moveToBack(Constants.TEAM2_SLIDE_SPEED);

        while (arm.isBusy()) {
            if (opMode.isStopRequested()) return;
            opMode.sleep(50);
        }

        arm.moveToFront(Constants.TEAM2_SLIDE_SPEED);

        while (arm.isBusy()){
            if (opMode.isStopRequested()) return;
            opMode.sleep(50);
        }
    }
}
