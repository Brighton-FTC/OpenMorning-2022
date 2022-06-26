package org.firstinspires.ftc.teamcode.hardware.subsystems.joystickMappings;

public class CubicMapping extends JoystickMapping{
    @Override
    protected double _map(double input) {
        return input * input * input;
    }
}
