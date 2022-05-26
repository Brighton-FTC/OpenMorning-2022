package org.firstinspires.ftc.teamcode.inputs;

import com.qualcomm.robotcore.hardware.Gamepad;

public class Inputs {
    /**
     * Get an X-Y pair of the right joystick position
     * @return Pair(x, y) of doubles
     */
    public static XY getRightJoystickData(){
        // NOTE: the values for "right_stick_*" are actually the values for the triggers
        Gamepad gamepad = GamepadWrapper.getGamepad();
        float right_x = -gamepad.right_stick_x;
        float right_y = -gamepad.right_stick_y;

        return new XY(right_x, right_y);
    }

    /**
     * Get an X-Y pair of the left joystick position
     * @return Pair(x, y) of doubles
     */
    public static XY getLeftJoystickData(){
        Gamepad gamepad = GamepadWrapper.getGamepad();
        float left_x = gamepad.left_stick_x;
        float left_y = gamepad.left_stick_y;

        return new XY(left_x, left_y);
    }

    /**
     * Get the extent to which the left trigger is pressed down
     * @return float
     */
    public static float getLeftTriggerData(){
        Gamepad gamepad = GamepadWrapper.getGamepad();
        return gamepad.left_trigger;
    }

    /**
     * Get the extent to which the right trigger is pressed down
     * @return float
     */
    public static float getRightTriggerData(){
        Gamepad gamepad = GamepadWrapper.getGamepad();
        return gamepad.right_trigger;
    }

    /**
     * Get if a certain GamepadButton is pressed
     * @return is pressed
     */
    public static boolean isPressed(GamepadButton button) {
        Gamepad gamepad = GamepadWrapper.getGamepad();
        boolean result = false;
        switch(button) {
            case CIRCLE:
                result = gamepad.circle;
                break;
            case CROSS:
                result = gamepad.cross;
                break;
            case SQUARE:
                result = gamepad.square;
                break;
            case TRIANGLE:
                result = gamepad.triangle;
                break;
            case L_BUMPER:
                result = gamepad.left_bumper;
                break;
            case L_STICK:
                result = gamepad.left_stick_button;
                break;
            case R_BUMPER:
                result = gamepad.right_bumper;
                break;
            case R_STICK:
                result = gamepad.right_stick_button;
                break;
            case D_UP:
                result = gamepad.dpad_up;
                break;
            case D_RIGHT:
                result = gamepad.dpad_right;
                break;
            case D_DOWN:
                result = gamepad.dpad_down;
                break;
            case D_LEFT:
                result = gamepad.dpad_left;
                break;
        }
        return result;
    }
}
