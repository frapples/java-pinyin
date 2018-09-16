package io.github.frapples.javapinyin.utils;


/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 18-9-16
 */

public class HexUtils {

    public static char chr(String hexCode) {
        if (hexCode.length() > 4) {
            throw new IllegalArgumentException(
                "Since the char type is two bytes, the encoding cannot exceed two bytes.");
        }

        return (char)(Integer.parseInt(hexCode, 16));
    }

}
