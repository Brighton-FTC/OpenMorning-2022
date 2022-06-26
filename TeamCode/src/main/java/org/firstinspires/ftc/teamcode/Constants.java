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
    public static final int TEAM1_DRIVETRAIN_COUNTS_PER_ROTATION = TEAM1_DRIVETRAIN_RAW_COUNTS_PER_ROTATION * TEAM1_DRIVETRAIN_GEAR_RATIO; // HD HEX

    public static final double TEAM1_DRIVETRAIN_COUNTS_PER_RADIAN = TEAM1_DRIVETRAIN_COUNTS_PER_ROTATION / (2 * Math.PI);
    public static final double TEAM1_DRIVETRAIN_COUNTS_PER_METER = TEAM1_DRIVETRAIN_COUNTS_PER_ROTATION / (Math.PI * TEAM1_DRIVETRAIN_WHEEL_DIAMETER); // counts per rotation * [distance per rotation]

    // TODO: change
    // T1 - arm
    public static final int TEAM1_ARM_FRONT_COUNTS = 50;
    public static final int TEAM1_ARM_BACK_COUNTS = 550;
    public static final double TEAM1_ARM_SPEED = 0.5;
    public static final int TEAM1_ARM_POSITION_EPSILON = 5;

    // TODO: change
    // T2 - drivetrain
    public static final double TEAM2_DRIVETRAIN_COUNTS_PER_RADIAN = 30;
    public static final double TEAM2_DRIVETRAIN_COUNTS_PER_METER = 30;

    public static final double TEAM2_DRIVETRAIN_FORWARDS_GRADIENT = 1380;
    public static final double TEAM2_DRIVETRAIN_FORWARDS_INTERCEPT = 55.4;

    // T2 - arm
    public static final int TEAM2_SLIDE_FRONT_COUNTS = 50;
    public static final int TEAM2_SLIDE_BACK_COUNTS = 176;
    public static final double TEAM2_SLIDE_SPEED = 0.5;

    // T1 - grabber
    public static final double TEAM1_GRABBER_CLOSED_POS = 0.0;
    public static final double TEAM1_GRABBER_OPEN_POS = 0.2;

    // T2 - intake
    public static final double TEAM2_INTAKE_SPEED = 1.0;
}
