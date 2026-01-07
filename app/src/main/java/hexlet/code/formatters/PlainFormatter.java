package hexlet.code.formatters;

import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

public class PlainFormatter {
    private static final String PROPERTY = "Property";
    public static String format(List<Map<String, Object>> result) {
        StringJoiner output = new StringJoiner("\n");

        for (var diff : result) {
            String key = (String) diff.get("key");
            String status = (String) diff.get("status");

            switch (status) {
                case "removed":
                    output.add(PROPERTY + " '" + key + "' was removed");
                    break;
                case "added":
                    output.add(PROPERTY + " '" + key + "' was added with value: "
                            + formatValue(diff.get("value")));
                    break;
                case "changed":
                    output.add(PROPERTY + " '" + key + "' was updated. From "
                            + formatValue(diff.get("oldValue")) + " to "
                            + formatValue(diff.get("newValue")));
                    break;
                case "unchanged":
                    break;
                default:
                    throw new IllegalArgumentException("Unknown status: " + status);
            }
        }

        return output.toString();

    }

    public static String formatValue(Object value) {
        if (value == null) {
            return "null";
        }
        if (value instanceof List || value instanceof Map) {
            return "[complex value]";
        }
        if (value instanceof String) {
            return "'" + value + "'";
        }
        return value.toString();
    }
}
