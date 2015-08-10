/**
 * Created by kelvin on 8/8/15.
 */
package com.wavelinkllc.chiime;

import android.content.Context;
import android.graphics.Typeface;

import java.util.Arrays;

public class FontManager {

    private static String[] fontPaths = {
            "fonts/ArialRoundedBold.ttf"
    };

    private static final int NUM_OF_CUSTOM_FONTS = 1;

    private static Typeface[] fonts = new Typeface[NUM_OF_CUSTOM_FONTS];

    private static boolean fontsLoaded = false;

    public static Typeface getTypeface(Context context, String fontPath) {
        if (!fontsLoaded) {
            loadFonts(context);
        }
        return fonts[Arrays.asList(fontPaths).indexOf(fontPath)];
    }

    private static void loadFonts(Context context) {
        for (int i = 0; i < NUM_OF_CUSTOM_FONTS; i++) {
            fonts[i] = Typeface.createFromAsset(context.getAssets(), fontPaths[i]);
        }
        fontsLoaded = true;

    }
}
