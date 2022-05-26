package org.firstinspires.ftc.teamcode.hardware.customPID;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.libs.util.TelemetryContainer;

import java.util.Date;

public class CustomPIDEController {
    public static final double AVERAGE_DT = 0.0035;
    public double Kp;
    public double Ki;
    public double Kd;
    public double Ke;
    double target;
    double lastError;
    double lastMeasurementTime;
    double integralTerm = 0;

    public CustomPIDEController (double Kp, double Ki, double Kd, double Ke){
        this.Kp = Kp;
        this.Ki = Ki;
        this.Kd = Kd;
        this.Ke = Ke;
    }

    public void setTarget(double target) {
        this.target = target;
    }

    /** Update the PID
     * @param currentValue the current value of the sensor
     * @param externalTerm an additional term that can be supplied to be added to the result
     * @return the "correction" provided by PID
     */
    public double update(double currentValue, double externalTerm){
        double error = target - currentValue;

        Date now = new Date();
        double newMeasurementTime = now.getTime() / 1000d;
        double dt = (newMeasurementTime - lastMeasurementTime);

        // if it was interrupted or the last time was unreliable in some way, reset dt
        if (dt > 1) dt = AVERAGE_DT;

        double proportionalTerm = error;
        integralTerm += (error + lastError) / 2d * dt;
        double derivativeTerm = (error - lastError) / dt;

        lastError = error;
        lastMeasurementTime = newMeasurementTime;

        Telemetry telemetry  = TelemetryContainer.getTelemetry();
        telemetry.addData("PIDE:", Kp +"," + Ki + ",", Kd + "," + Ke);

        return (Kp * proportionalTerm + Ki * integralTerm + Kd * derivativeTerm + Ke * externalTerm) / 100; // make it into a percentage
    }
}
