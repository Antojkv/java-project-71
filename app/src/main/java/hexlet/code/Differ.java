package hexlet.code;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import static hexlet.code.BuildDiff.buildDiff;


public class Differ {
    public static String getFormat(String fileName) {
        if (fileName.endsWith(".json")) {
            return "json";
        } else if (fileName.endsWith(".yaml") || fileName.endsWith(".yml")) {
            return "yaml";
        }
        return "";
    }

    public static String generate(String filePath1, String filePath2) throws Exception {
        return generate(filePath1, filePath2, "stylish");
    }

    public static String generate(String filePath1, String filePath2, String formatName) throws Exception {
        String content1 = Files.readString(Paths.get(filePath1));
        String content2 = Files.readString(Paths.get(filePath2));

        String format = getFormat(filePath1);
        Map<String, Object> map1 = Parser.parse(content1, format);
        Map<String, Object> map2 = Parser.parse(content2, format);

        List<Map<String, Object>> diff = buildDiff(map1, map2);
        return Formatter.format(diff, formatName);
    }
}
