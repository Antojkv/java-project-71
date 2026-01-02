package hexlet.code.formatters;

import hexlet.code.Differ;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PlainFormatterTest {
    private static Path getFixturePath(String fileName) {
        return Paths.get("src", "test", "resources", "fixtures", fileName)
                .toAbsolutePath()
                .normalize();
    }

    @Test
    public void testPlainFormat() throws Exception {
        Path path1 = getFixturePath("diff1.json");
        Path path2 = getFixturePath("diff2.json");

        String result = Differ.generate(path1.toString(), path2.toString(), "plain");

        String[] expectedLines = {
            "Property 'chars2' was updated. From [complex value] to false",
            "Property 'checked' was updated. From false to true",
            "Property 'default' was updated. From null to [complex value]",
            "Property 'id' was updated. From 45 to null",
            "Property 'key1' was removed",
            "Property 'key2' was added with value: 'value2'",
            "Property 'numbers2' was updated. From [complex value] to [complex value]",
            "Property 'numbers3' was removed",
            "Property 'numbers4' was added with value: [complex value]",
            "Property 'obj1' was added with value: [complex value]",
            "Property 'setting1' was updated. From 'Some value' to 'Another value'",
            "Property 'setting2' was updated. From 200 to 300",
            "Property 'setting3' was updated. From true to 'none'"
        };

        for (String line : expectedLines) {
            assertTrue(result.contains(line), "Missing: " + line);
        }
    }

    @Test
    public void testPlainFormatter() {
        assertEquals("null", PlainFormatter.formatValue(null));
        assertEquals("'text'", PlainFormatter.formatValue("text"));
        assertEquals("123", PlainFormatter.formatValue(123));
        assertEquals("[complex value]", PlainFormatter.formatValue(List.of(1, 2, 3)));
        assertEquals("[complex value]", PlainFormatter.formatValue(Map.of("key", "value")));
    }

    @Test
    public void testFormatAdded() {
        Map<String, Object> diff = new HashMap<>();
        diff.put("key", "newKey");
        diff.put("status", "added");
        diff.put("value", "newValue");

        List<Map<String, Object>> diff1 = List.of(diff);
        String result = PlainFormatter.format(diff1);

        assertEquals("Property 'newKey' was added with value: 'newValue'", result.trim());
    }

    @Test
    public void testFormatRemoved() {
        Map<String, Object> diff = new HashMap<>();
        diff.put("key", "oldKey");
        diff.put("status", "removed");

        List<Map<String, Object>> diff1 = List.of(diff);
        String result = PlainFormatter.format(diff1);

        assertEquals("Property 'oldKey' was removed", result.trim());
    }

    @Test
    public void testFormatChanged() {
        Map<String, Object> diff = new HashMap<>();
        diff.put("key", "timeout");
        diff.put("status", "changed");
        diff.put("oldValue", 50);
        diff.put("newValue", 20);

        List<Map<String, Object>> diff1 = List.of(diff);
        String result = PlainFormatter.format(diff1);

        assertEquals("Property 'timeout' was updated. From 50 to 20", result.trim());
    }

    @Test
    public void testFormatChangedWithComplexValue() {
        Map<String, Object> diff = new HashMap<>();
        diff.put("key", "data");
        diff.put("status", "changed");
        diff.put("oldValue", Arrays.asList(1, 2, 3));
        diff.put("newValue", Map.of("key", "value"));

        List<Map<String, Object>> diff1 = List.of(diff);
        String result = PlainFormatter.format(diff1);

        assertEquals("Property 'data' was updated. From [complex value] to [complex value]",
                result.trim());
    }
}
