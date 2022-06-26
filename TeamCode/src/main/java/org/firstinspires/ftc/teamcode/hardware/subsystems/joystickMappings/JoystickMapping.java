package org.firstinspires.ftc.teamcode.hardware.subsystems.joystickMappings;

public abstract class JoystickMapping {
    public double map(double input){
        return Math.signum(input) * _map(Math.abs(input));
    }
    /**
     * Map a value 0-1 to a scaled speed value 0-1
     * @param input
     * @return
     */
    protected abstract double _map(double input);
}
