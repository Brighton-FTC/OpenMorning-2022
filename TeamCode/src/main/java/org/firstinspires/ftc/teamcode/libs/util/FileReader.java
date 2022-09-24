package org.firstinspires.ftc.teamcode.libs.util;

import android.content.Context;
import android.content.res.Resources;

import java.io.IOException;
import java.io.InputStream;

public class FileReader {
    /**
     * Get a resource (like a license key) from its resource file - please see Discord/#resources for how to add license keys
     * @param name The path of the .txt file to read under TeamCode/src/res/PRIVATE (excluding the file extension)
     * @return The contents of the .txt file, as a string
     */
    public static String readFile(String name, Context context) throws IOException {
        StringBuilder result = new StringBuilder();
        Resources resources = context.getResources();
        InputStream fileReader = resources.openRawResource(
                resources.getIdentifier(name, "raw", context.getPackageName())
        );

        // Keep getting chars from file and adding to result
        int i;
        while((i = fileReader.read()) != -1) {
            char currentChar = (char)i;
            result.append(currentChar);
        }

        return result.toString();

    }
}