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

public class TurnCounts {
    public void run(LinearOpMode opMode, int counts, double speed) throws InterruptedException {
        // Get hardwareMap
        HardwareMap hardwareMap = opMode.hardwareMap;

        // BODY
        DriveTrainController driveTrain = new DriveTrainController(new DriveTrain(
                hardwareMap.get(DcMotor.class, "left_drivetrain_motor"),
                hardwareMap.get(DcMotor.class, "right_drivetrain_motor"),
                false
        ),
                new RootMapping(2),
                new CosMapping(),
                0,0
        );

        driveTrain.startDrivingCounts(counts, -counts, speed);
        driveTrain.waitWhileBusy();
    }
}
