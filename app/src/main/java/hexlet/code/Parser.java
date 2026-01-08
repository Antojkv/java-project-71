package hexlet.code;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.util.Map;

public class Parser {

    public static Map<String, Object> parse(String content, String dataFormat) throws JsonProcessingException {
        ObjectMapper mapper;
        switch (dataFormat.toLowerCase()) {
            case "json":
                mapper = new ObjectMapper();
                break;
            case "yaml":
            case "yml":
                mapper = new ObjectMapper(new YAMLFactory());
                break;
            default:
                throw new IllegalArgumentException("Unsupported format: " + dataFormat);
        }
        return mapper.readValue(content, Map.class);
    }
}
