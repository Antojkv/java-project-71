package hexlet.code;

import hexlet.code.formatters.PlainFormatter;
import hexlet.code.formatters.StylishFormatter;

import java.util.List;
import java.util.Map;

public class Formatter {
    public static String format(List<Map<String, Object>> diff, String formatName) {
        switch (formatName) {
            case "stylish":
                return StylishFormatter.format(diff);
            case "plain":
                return PlainFormatter.format(diff);
            default:
                throw new IllegalArgumentException("Unknown format: " + formatName);
        }
    }
}
