package hexlet.code;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.Objects;

public class Differ {

    private static final String STATUS = "status";
    private static final String VALUE = "value";

    public static String generate(String filePath1, String filePath2) throws Exception {
        return generate(filePath1, filePath2, "stylish");
    }

    public static String generate(String filePath1, String filePath2, String formatName) throws Exception {
        String content1 = Files.readString(Paths.get(filePath1));
        String content2 = Files.readString(Paths.get(filePath2));

        String format = Parser.getFormat(filePath1);
        Map<String, Object> map1 = Parser.parse(content1, format);
        Map<String, Object> map2 = Parser.parse(content2, format);

        List<Map<String, Object>> diff = buildDiff(map1, map2);
        return Formatter.format(diff, formatName);
    }

    public static List<Map<String, Object>> buildDiff(Map<String, Object> map1, Map<String, Object> map2) {
        TreeSet<String> allKeys = new TreeSet<>(map1.keySet());
        allKeys.addAll(map2.keySet());
        List<Map<String, Object>> result = new ArrayList<>();
        for (var key : allKeys) {
            boolean inMap1 = map1.containsKey(key);
            boolean inMap2 = map2.containsKey(key);
            var value1 = map1.get(key);
            var value2 = map2.get(key);
            if (inMap1 && !inMap2) {
                Map<String, Object> diff1 = new HashMap<>();
                diff1.put("key", key);
                diff1.put(STATUS, "removed");
                diff1.put(VALUE, value1);
                result.add(diff1);
            } else if (!inMap1 && inMap2) {
                Map<String, Object> diff2 = new HashMap<>();
                diff2.put("key", key);
                diff2.put(STATUS, "added");
                diff2.put(VALUE, value2);
                result.add(diff2);
            } else if (Objects.equals(value1, value2)) {
                Map<String, Object> diff3 = new HashMap<>();
                diff3.put("key", key);
                diff3.put(STATUS, "unchanged");
                diff3.put(VALUE, value2);
                result.add(diff3);
            } else {
                Map<String, Object> diff4 = new HashMap<>();
                diff4.put("key", key);
                diff4.put(STATUS, "changed");
                diff4.put("oldValue", value1);
                diff4.put("newValue", value2);
                result.add(diff4);
            }
        }
        return result;
    }
}
