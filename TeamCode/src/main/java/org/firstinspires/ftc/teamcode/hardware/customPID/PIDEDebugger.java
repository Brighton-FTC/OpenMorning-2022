package org.firstinspires.ftc.teamcode.hardware.customPID;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.inputs.GamepadButton;
import org.firstinspires.ftc.teamcode.inputs.Inputs;
import org.firstinspires.ftc.teamcode.libs.util.TelemetryContainer;

public class PIDEDebugger {
    private boolean allButtonsReleased = true;

    private double p;
    private double i;
    private double d;
    private double e;

    CustomPIDEController controller;

    public PIDEDebugger(CustomPIDEController controller){
        this.controller = controller;
        p = controller.Kp;
        i = controller.Ki;
        d = controller.Kd;
        e = controller.Ke;
    }

    public void tweakPID() {
        // change the P, I, D and E
        // square is P, triangle is I, cross is D, circle is E
        // up is increase, down is decrease
        double pidChange = 0;
        double step = 0.5;

        boolean squarePressed = Inputs.isPressed(GamepadButton.SQUARE);
        boolean trianglePressed = Inputs.isPressed(GamepadButton.TRIANGLE);
        boolean crossPressed = Inputs.isPressed(GamepadButton.CROSS);
        boolean circlePressed = Inputs.isPressed(GamepadButton.CIRCLE);

        boolean upPressed = Inputs.isPressed(GamepadButton.D_UP);
        boolean downPressed = Inputs.isPressed(GamepadButton.D_DOWN);

        // do not count one press as multiple presses
        if (upPressed) pidChange += step;
        if (downPressed) pidChange -= step;
        if (allButtonsReleased) {
            if (squarePressed) p += pidChange;
            if (trianglePressed) i += pidChange;
            if (crossPressed) d += pidChange;
            if (circlePressed) e += pidChange;
        }

        allButtonsReleased = !upPressed && !downPressed;

        controller.Kp = p;
        controller.Ki = i;
        controller.Kd = d;
        controller.Ke = e;

        Telemetry telemetry = TelemetryContainer.getTelemetry();
        telemetry.addData("PIDE coefficients", p + ", " + i + ", " + d + ", " + e);
    }
}
