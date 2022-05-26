package org.firstinspires.ftc.teamcode.inputs;

import com.qualcomm.robotcore.hardware.Gamepad;

public class GamepadWrapper {
    private static Gamepad gamepad;

    public static void createGamepadWrapper(Gamepad gamepad) {
        GamepadWrapper.gamepad = gamepad;
    }

    public static Gamepad getGamepad() {
        if (gamepad == null) throw new RuntimeException("You forgot to instantiate a GamepadWrapper");
        return gamepad;
    }
}
