package hexlet.code.formatters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonFormatterTest {
    @Test
    public void testFormatEmptyDiff() {
        List<Map<String, Object>> emptyDiff = List.of();
        String expected = "[ ]";
        assertEquals(expected, JsonFormatter.format(emptyDiff));
    }

    @Test
    public void testFormatAddedValue() throws JsonProcessingException {
        List<Map<String, Object>> diff = List.of(
                Map.of(
                        "value", "newValue",
                        "key", "newKey",
                        "status", "added"
                )
        );

        String expected = "[\n"
                          + "  {\n"
                          + "    \"value\": \"newValue\",\n"
                          + "    \"key\": \"newKey\",\n"
                          + "    \"status\": \"added\"\n"
                          + "  }\n"
                          + "]";

        String actual = JsonFormatter.format(diff);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode expectedJson = mapper.readTree(expected);
        JsonNode actualNode = mapper.readTree(actual);

        assertEquals(expectedJson, actualNode);
    }

    @Test
    public void testFormatRemovedValue() throws JsonProcessingException {
        List<Map<String, Object>> diff = List.of(
                Map.of(
                        "value", "oldValue",
                        "key", "oldKey",
                        "status", "removed"
                )
        );

        String expected = "[\n"
                          + "  {\n"
                          + "    \"value\": \"oldValue\",\n"
                          + "    \"key\": \"oldKey\",\n"
                          + "    \"status\": \"removed\"\n"
                          + "  }\n"
                          + "]";

        String actual = JsonFormatter.format(diff);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode expectedJson = mapper.readTree(expected);
        JsonNode actualNode = mapper.readTree(actual);

        assertEquals(expectedJson, actualNode);
    }

    @Test
    public void testFormatChangedValue() throws JsonProcessingException {
        List<Map<String, Object>> diff = List.of(
                Map.of(
                        "newValue", "after",
                        "oldValue", "before",
                        "key", "newKey",
                        "status", "changed"
                )
        );

        String expected = "[\n"
                          + "  {\n"
                          + "    \"newValue\": \"after\",\n"
                          + "    \"oldValue\": \"before\",\n"
                          + "    \"key\": \"newKey\",\n"
                          + "    \"status\": \"changed\"\n"
                          + "  }\n"
                          + "]";

        String actual = JsonFormatter.format(diff);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode expectedJson = mapper.readTree(expected);
        JsonNode actualNode = mapper.readTree(actual);

        assertEquals(expectedJson, actualNode);
    }

    @Test
    public void testFormatUnchangedValue() throws JsonProcessingException {
        List<Map<String, Object>> diff = List.of(
                Map.of(
                        "value", "oldValue",
                        "key", "oldKey",
                        "status", "unchanged"
                )
        );

        String expected = "[\n"
                          + "  {\n"
                          + "    \"value\": \"oldValue\",\n"
                          + "    \"key\": \"oldKey\",\n"
                          + "    \"status\": \"unchanged\"\n"
                          + "  }\n"
                          + "]";

        String actual = JsonFormatter.format(diff);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode expectedJson = mapper.readTree(expected);
        JsonNode actualNode = mapper.readTree(actual);

        assertEquals(expectedJson, actualNode);
    }
}
