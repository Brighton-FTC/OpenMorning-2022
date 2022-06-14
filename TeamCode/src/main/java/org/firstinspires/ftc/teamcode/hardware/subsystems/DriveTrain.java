package org.firstinspires.ftc.teamcode.hardware.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.libs.util.Maths;
import org.firstinspires.ftc.teamcode.libs.util.TelemetryContainer;

/**
 * A class that represents a drivetrain subsystem of a robot
 */
public class DriveTrain {

  /**
   * The motors on the drivetrain
   */

  public final DcMotor leftMotor;
  public final DcMotor rightMotor;

  /**
   * Create drivetrain from two DcMotors
   * @param leftMotor The left DcMotor
   * @param rightMotor The right DcMotor
   */
  public  DriveTrain(DcMotor leftMotor, DcMotor rightMotor, boolean drivetrainReversed) {
    this.leftMotor = leftMotor;
    this.rightMotor = rightMotor;

    // Most robots need the motor on one side to be reversed to drive forward
    // Reverse the motor that runs backwards when connected directly to the
    // battery
    if (drivetrainReversed) {
      this.leftMotor.setDirection(DcMotor.Direction.FORWARD);
      this.rightMotor.setDirection(DcMotor.Direction.REVERSE);
    } else {
      this.leftMotor.setDirection(DcMotor.Direction.REVERSE);
      this.rightMotor.setDirection(DcMotor.Direction.FORWARD);
    }
  }

  /**
   * Set raw powers of motors
   * @param leftPower Power for left motor (-1 to 1)
   * @param rightPower Power for right motor (-1 to 1)
   */
  public void setMotorPowers(double leftPower, double rightPower) {
    if (Math.abs(leftPower) > 1 || Math.abs(rightPower) > 1)
      throw new RuntimeException(
            "The drivetrain powers are out of the range -1 to 1: " + leftPower +
            " " + rightPower);
    this.leftMotor.setPower(leftPower);
    this.rightMotor.setPower(rightPower);
  }

  /**
   * Turn while driving. If a motor needs to run at more than 100% power, both motor powers are scaled down
   * That means that turning at high speeds might slow down the robot
   * @param turn how much to turn
   * @param speed speed of the robot
   */
  public void arcadeDriveScale(double speed, double turn) {
    // Calculate values
    double leftPower = speed - turn;
    double rightPower = speed + turn;

    double scalingFactor = Math.max(Math.abs(leftPower), Math.abs(rightPower));
    // if it is < 1, it would force a scale-up, which is not what we want
    if (scalingFactor > 1) {
      leftPower /= scalingFactor;
      rightPower /= scalingFactor;
    }

    TelemetryContainer.getTelemetry().addData("Powers", leftPower + " "+ rightPower);

    // Return values
    setMotorPowers(leftPower, rightPower);
  }

  /**
   * Turn while driving. If a motor needs to run at more than 100% power, it is clamped.
   * That means that it may not turn as much on larger speeds.
   * @param turn how much to turn
   * @param speed speed of the robot
   */
  public void arcadeDriveClamp(double speed, double turn) {
    // Calculate values
    double leftPower = speed - turn;
    double rightPower = speed + turn;
    // Keep values within -1 to 1
    leftPower = Maths.clamp(leftPower, -1.0d, 1.0d);
    rightPower = Maths.clamp(rightPower, -1.0d, 1.0d);
    // Return values
    setMotorPowers(leftPower, rightPower);
  }
}
