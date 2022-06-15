package org.firstinspires.ftc.teamcode.opModes.subroutines;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.hardware.subsystems.DriveTrain;
import org.firstinspires.ftc.teamcode.hardware.subsystems.DriveTrainController;
import org.firstinspires.ftc.teamcode.libs.util.TelemetryContainer;

public class AutonomousDriveForward {
    // TODO: Should we move this to OpModes/subroutines as I think it is not team-specific?
    public void run(LinearOpMode opMode) {
        // Get hardwareMap
        HardwareMap hardwareMap = opMode.hardwareMap;

        // BODY
        DriveTrainController driveTrain = new DriveTrainController(new DriveTrain(
                hardwareMap.get(DcMotor.class, "left_drivetrain_motor"),
                hardwareMap.get(DcMotor.class, "right_drivetrain_motor"),
                false
        ),
                Constants.TEAM1_DRIVETRAIN_COUNTS_PER_RADIAN,
                Constants.TEAM1_DRIVETRAIN_COUNTS_PER_METER
        );
        Telemetry telemetry = TelemetryContainer.getTelemetry();

        driveTrain.startDrivingForward(3, 0.5);
        opMode.sleep(3000);
//        driveTrain.waitWhileBusy();
        telemetry.addData("Subroutine", "completed");
    }
}
