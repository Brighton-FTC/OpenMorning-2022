package org.firstinspires.ftc.teamcode.inputs.inputs;

import org.firstinspires.ftc.teamcode.inputs.GamepadButton;
import org.firstinspires.ftc.teamcode.inputs.Inputs;

public class DebouncedButton {
    private final GamepadButton button;
    private boolean wasPressedLastTick;
    public DebouncedButton(GamepadButton button) {
        this.button = button;
        this.wasPressedLastTick = false;
    }

    /**
     * Return true if pressed
     * @return true if pressed
     */
    public boolean processTick() {
        boolean isDown = Inputs.isPressed(button);

        boolean ret = isDown && !wasPressedLastTick;

        this.wasPressedLastTick = isDown;

        return ret;
    }
}
