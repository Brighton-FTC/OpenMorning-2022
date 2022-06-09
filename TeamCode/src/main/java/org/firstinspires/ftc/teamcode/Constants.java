package org.firstinspires.ftc.teamcode;

/**
 * Config constants to be passed around parts of the program.
 * All in m / rad / encoder counts
 */
public class Constants {

    // TODO: change
    // T1 - drivetrain
    public static final double TEAM1_DRIVETRAIN_WHEEL_DIAMETER = 0.09; // 90mm
    public static final int TEAM1_DRIVETRAIN_GEAR_RATIO = 20; // 20:1
    public static final int TEAM1_DRIVETRAIN_RAW_COUNTS_PER_ROTATION = 28; // HD HEX

    public static final double TEAM1_DRIVETRAIN_COUNTS_PER_RADIAN = TEAM1_DRIVETRAIN_RAW_COUNTS_PER_ROTATION / (2 * Math.PI);
    public static final double TEAM1_DRIVETRAIN_COUNTS_PER_METER = (TEAM1_DRIVETRAIN_RAW_COUNTS_PER_ROTATION * TEAM1_DRIVETRAIN_WHEEL_DIAMETER) / (Math.PI * TEAM1_DRIVETRAIN_WHEEL_DIAMETER); // counts per rotation * gear ratio  / pi * d (m) // Dev - change

    // TODO: change
    // T1 - arm
    public static final double TEAM1_ARM_COUNTS_PER_RADIAN = 30;
    public static final int TEAM1_ARM_FRONT_COUNTS = 50;
    public static final int TEAM1_ARM_BACK_COUNTS = 550;
    public static final double TEAM1_ARM_SPEED = 0.5;

    // TODO: change
    // T2 - drivetrain
    public static final double TEAM2_DRIVETRAIN_COUNTS_PER_RADIAN = 30;
    public static final double TEAM2_DRIVETRAIN_COUNTS_PER_METER = 30;

    public static final boolean IS_DEV = true; // TODO: Change in prod
}
