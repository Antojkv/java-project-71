package hexlet.code.formatters;

import java.util.List;
import java.util.Map;

public class PlainFormatter {
    public static String format(List<Map<String, Object>> result) {
        StringBuilder output = new StringBuilder();

        for (var diff : result) {
            String key = (String) diff.get("key");
            String status = (String) diff.get("status");

            switch (status) {
                case "removed":
                    output.append("Property '").append(key).append("' was removed\n");
                    break;
                case "added":
                    output.append("Property '").append(key).append("' was added with value: ")
                            .append(formatValue(diff.get("value"))).append("\n");
                    break;
                case "changed":
                    output.append("Property '").append(key).append("' was updated. From ")
                            .append(formatValue(diff.get("oldValue"))).append(" to ")
                            .append(formatValue(diff.get("newValue"))).append("\n");
                    break;
                case "unchanged":
                    break;
                default:
                    throw new IllegalArgumentException("Unknown status: " + status);
            }
        }
        return output.toString();
    }

    private static String formatValue(Object value) {
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
