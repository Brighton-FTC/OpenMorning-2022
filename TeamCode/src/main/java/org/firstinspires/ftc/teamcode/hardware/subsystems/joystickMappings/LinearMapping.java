package org.firstinspires.ftc.teamcode.hardware.subsystems.joystickMappings;

public class LinearMapping implements JoystickMapping{
    @Override
    public double mapSpeed(double input) {
        return input;
    }

    @Override
    public double mapTurning(double input) {
        return input;
    }
}
