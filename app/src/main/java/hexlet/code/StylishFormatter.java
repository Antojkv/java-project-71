package hexlet.code;

import java.util.List;
import java.util.Map;

public class StylishFormatter {
    private static final String VALUE = "value";
    public static String format(List<Map<String, Object>> result) {
        StringBuilder output = new StringBuilder("{\n");

        for (var diff : result) {
            String key = (String) diff.get("key");
            String status = (String) diff.get("status");

            switch (status) {
                case "removed":
                    output.append("  - ").append(key).append(": ")
                            .append(formatValue(diff.get(VALUE))).append("\n");
                    break;
                case "added":
                    output.append("  + ").append(key).append(": ")
                            .append(formatValue(diff.get(VALUE))).append("\n");
                    break;
                case "unchanged":
                    output.append("    ").append(key).append(": ")
                            .append(formatValue(diff.get(VALUE))).append("\n");
                    break;
                case "changed":
                    output.append("  - ").append(key).append(": ")
                            .append(formatValue(diff.get("oldValue"))).append("\n");
                    output.append("  + ").append(key).append(": ")
                            .append(formatValue(diff.get("newValue"))).append("\n");
                    break;
                default:
                    throw new IllegalArgumentException("Unknown status: " + status);
            }
        }

        output.append("}");
        return output.toString();
    }

    private static String formatValue(Object value) {
        if (value == null) {
            return "null";
        }
        if (value instanceof List) {
            return value.toString();
        }
        if (value instanceof Map) {
            return value.toString();
        }
        return value.toString();
    }
}
