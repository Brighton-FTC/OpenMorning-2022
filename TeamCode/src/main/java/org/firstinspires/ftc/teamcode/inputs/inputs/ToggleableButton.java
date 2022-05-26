package org.firstinspires.ftc.teamcode.inputs.inputs;

import org.firstinspires.ftc.teamcode.inputs.GamepadButton;
import org.firstinspires.ftc.teamcode.inputs.Inputs;

public class ToggleableButton {
    private DebouncedButton button;
    private boolean state;
    public ToggleableButton (GamepadButton button, boolean initialState) {
        this.button = new DebouncedButton(button);
        state = initialState;
    }

    public boolean processTick() {
        if (button.processTick()) state = !state;
        return state;
    }
}
