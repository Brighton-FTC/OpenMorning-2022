package org.firstinspires.ftc.teamcode.inputs.inputs;

import org.firstinspires.ftc.teamcode.inputs.GamepadButton;

public class IncrementButtons {

    public double value;
    public double step;
    private DebouncedButton incrementButton;
    private DebouncedButton decrementButton;

    public IncrementButtons (GamepadButton incrementButton, GamepadButton decrementButton, double initialValue, double step) {
        this.incrementButton = new DebouncedButton(incrementButton);
        this.decrementButton = new DebouncedButton(decrementButton);
        this.value = initialValue;
        this.step = step;
    }

    public double processTick() {
        if (incrementButton.processTick()) value += step;
        if (decrementButton.processTick()) value -= step;
        return value;
    }
}
