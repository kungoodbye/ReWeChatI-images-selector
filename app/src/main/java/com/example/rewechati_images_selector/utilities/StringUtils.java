package com.example.rewechati_images_selector.utilities;

/**
 * Created by 黄焜 on 2017/4/12.
 */

public class StringUtils {
    public static String getLastPathSegment(String content) {
        if (content == null || content.length() == 0) {
            return "";
        }
        String[] segments = content.split("/");
        if (segments.length > 0) {
            return segments[segments.length - 1];
        }
        return "";
    }
}
