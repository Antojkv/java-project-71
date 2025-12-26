package hexlet.code;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

import java.util.Map;

public class Parser {
    public static String getFormat(String fileName) {
        if (fileName.endsWith(".json")) {
            return "json";
        } else if (fileName.endsWith(".yaml") || fileName.endsWith(".yml")) {
            return "yaml";
        }
        throw new IllegalArgumentException("Unsupported file format: " + fileName);
    }

    public static Map<String, Object> parse(String content, String fileFormat) throws Exception {
        switch (fileFormat.toLowerCase()) {
            case "json":
                return parseJson(content);
            case "yaml":
            case "yml":
                return parseYaml(content);
            default:
                throw new IllegalArgumentException("Unsupported format: " + fileFormat);
        }
    }

    public static Map<String, Object> parseJson(String content) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(content, Map.class);
    }

    public static Map<String, Object> parseYaml(String content) throws Exception {
        ObjectMapper mapper = new YAMLMapper();
        return mapper.readValue(content, Map.class);
    }
}
