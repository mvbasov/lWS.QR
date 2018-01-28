package net.basov.util;

/**
 * Created by mvb on 6/18/17.
 */

public class TextTools {

    //http://stackoverflow.com/a/13006907
    public static String byteArrayToHexString(byte[] a) {
        StringBuilder sb = new StringBuilder(a.length * 2);
        for (byte b : a)
            sb.append(String.format("%02x", b));
        return sb.toString();
    }

    public static String escapeCharsForJSON(String src) {
        return src
                .replace("\n", "\\n")
                .replace("\t", "\\t")
                .replace("'", "&#39;")
                .replace("\"", "\\\"");
    }

}
