package org.firstinspires.ftc.teamcode.hardware.subsystems.joystickMappings;

public class SquareMapping extends JoystickMapping{
    @Override
    public double _map(double input) {
        return input * input;
    }
}
