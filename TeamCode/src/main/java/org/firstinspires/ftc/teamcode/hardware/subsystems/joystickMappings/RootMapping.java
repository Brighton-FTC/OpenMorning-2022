package org.firstinspires.ftc.teamcode.hardware.subsystems.joystickMappings;

public class RootMapping extends JoystickMapping{

    final double power;
    final double boundary;

    public RootMapping(double inputScale){
        if (inputScale <= 1) throw new RuntimeException("The input scale has to be larger than 1");
        power = 1 / inputScale;
        boundary = Math.pow(0.5, power);
    }

    @Override
    protected double _map(double input){
        input -= 0.5; // goes from -0.5 to 0.5

        input = Math.pow(input, power); // goes from -boundary to boundary

        input = (input + boundary) / 2; // goes from 0 to boundary

        return input / boundary;
    }

    @Override
    public String toString(){
        return "RootMapping{inputScale:" + 1/power + "}";
    }
}
