package org.firstinspires.ftc.teamcode.opModes.subroutines.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.hardware.subsystems.DriveTrain;
import org.firstinspires.ftc.teamcode.hardware.subsystems.DriveTrainController;
import org.firstinspires.ftc.teamcode.hardware.subsystems.joystickMappings.CosMapping;
import org.firstinspires.ftc.teamcode.hardware.subsystems.joystickMappings.RootMapping;
import org.firstinspires.ftc.teamcode.libs.util.TelemetryContainer;

public class DriveForwardCounts {
    public void run(LinearOpMode opMode, DriveTrainController driveTrain, int counts, double speed) throws InterruptedException {
        driveTrain.startDrivingCounts(counts, counts, speed);
        while (driveTrain.isBusy()) { opMode.sleep(50); }
        Telemetry telemetry = TelemetryContainer.getTelemetry();
        telemetry.addData("Drive Forward - Is drivetrain busy", driveTrain.isBusy());
    }
}
