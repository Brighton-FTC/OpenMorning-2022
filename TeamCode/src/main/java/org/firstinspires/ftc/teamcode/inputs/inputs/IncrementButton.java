package org.firstinspires.ftc.teamcode.inputs.inputs;

import org.firstinspires.ftc.teamcode.inputs.GamepadButton;

public class IncrementButton {

    public double value;
    public double step;
    private DebouncedButton button;

    public IncrementButton (GamepadButton button, double initialValue, double step) {
        this.button = new DebouncedButton(button);
        this.value = initialValue;
        this.step = step;
    }

    public double processTick() {
        if (button.processTick()) value += step;
        return value;
    }
}
