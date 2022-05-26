package org.firstinspires.ftc.teamcode.libs.util;

public class Maths {
    /**
     * Keep the value of a double within boundaries (e.g. for keeping motor powers within -1 to 1)
     * @param n Number to be adjusted to fit in boundaries
     * @param min Minimum boundary
     * @param max Maximum boundary
     * @return Number inside boundaries
     */
    public static double clamp(double n, double min, double max) {
        if(n < min) return min;
        else if(n > max) return max;
        return n;
    }

    /**
     * Interpolates the value n onto the range minOut to maxOut, clamping it if needed
     * @param n the input value
     * @param minN the lower bound of n
     * @param maxN the upper bound of n
     * @param minOut the lower bound of output
     * @param maxOut the upper bound of the output
     * @return the interpolated value
     */
    public static double mapClamped(double n, double minN, double maxN, double minOut, double maxOut){
        n = clamp(n, minN, maxN) - minN; // a value from 0 to maxN - minN
        n /= maxN - minN; // a value between 0 and 1

        return minOut + (maxOut - minOut) * n;
    }
}
