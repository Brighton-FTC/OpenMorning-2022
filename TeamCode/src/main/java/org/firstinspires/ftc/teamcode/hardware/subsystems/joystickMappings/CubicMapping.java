package org.firstinspires.ftc.teamcode.hardware.subsystems.joystickMappings;

public class CubicMapping implements JoystickMapping{
    private double scale(double input){
        return input * input * input;
    }

    @Override
    public double mapSpeed(double input) {
        return scale(input);
    }

    @Override
    public double mapTurning(double input) {
        return scale(input);
    }
}
