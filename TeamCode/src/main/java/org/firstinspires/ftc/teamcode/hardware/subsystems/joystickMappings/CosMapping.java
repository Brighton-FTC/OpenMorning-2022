package org.firstinspires.ftc.teamcode.hardware.subsystems.joystickMappings;

public class CosMapping implements JoystickMapping{
    private double scale(double input){
        return Math.signum(input) * 0.5 * (1.0 - Math.cos(input * Math.PI));
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
