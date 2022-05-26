package org.firstinspires.ftc.teamcode.libs.util;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class TelemetryContainer {
    private static Telemetry telemetry;


    public static Telemetry getTelemetry() {
        if (telemetry == null) throw new RuntimeException("Telemetry not initialised");
        return telemetry;
    }

    public static void setTelemetry(Telemetry telemetry) {
        TelemetryContainer.telemetry = telemetry;
    }
}
