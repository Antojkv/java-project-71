package hexlet.code;

import hexlet.code.formatters.PlainFormatter;
import hexlet.code.formatters.StylishFormatter;
import org.junit.jupiter.api.Test;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class DifferTest {

    private static Path getFixturePath(String fileName) {
        return Paths.get("src", "test", "resources", "fixtures", fileName)
                .toAbsolutePath()
                .normalize();
    }

    @Test
    public void testIdenticalValues() throws Exception {
        Path path1 = getFixturePath("identical1.json");
        Path path2 = getFixturePath("identical2.json");

        String result = Differ.generate(path1.toString(), path2.toString());

        assertTrue(result.contains("    setting1: Some value"));
        assertTrue(result.contains("    setting2: 200"));
        assertTrue(result.contains("    setting3: true"));
        assertTrue(result.contains("    key1: value1"));
        assertTrue(result.contains("    numbers1: [1, 2, 3, 4]"));
        assertTrue(result.contains("    numbers2: [2, 3, 4, 5]"));
        assertTrue(result.contains("    id: 45"));
        assertTrue(result.contains("    default: null"));
        assertTrue(result.contains("    checked: false"));
        assertTrue(result.contains("    numbers3: [3, 4, 5]"));
        assertTrue(result.contains("    chars1: [a, b, c]"));
        assertTrue(result.contains("    chars2: [d, e, f]"));

        assertFalse(result.contains("  - "));
        assertFalse(result.contains("  + "));

        assertTrue(result.startsWith("{"));
        assertTrue(result.endsWith("}"));
    }

    @Test
    public void testDifferentValues() throws Exception {
        Path path1 = getFixturePath("diff1.json");
        Path path2 = getFixturePath("diff2.json");

        String result = Differ.generate(path1.toString(), path2.toString());

        assertTrue(result.contains("    chars1: [a, b, c]"));
        assertTrue(result.contains("  - chars2: [d, e, f]"));
        assertTrue(result.contains("  + chars2: false"));
        assertTrue(result.contains("  - checked: false"));
        assertTrue(result.contains("  + checked: true"));
        assertTrue(result.contains("  - default: null"));
        assertTrue(result.contains("  + default: [value1, value2]"));
        assertTrue(result.contains("  - id: 45"));
        assertTrue(result.contains("  + id: null"));
        assertTrue(result.contains("  - key1: value1"));
        assertTrue(result.contains("  + key2: value2"));
        assertTrue(result.contains("    numbers1: [1, 2, 3, 4]"));
        assertTrue(result.contains("  - numbers2: [2, 3, 4, 5]"));
        assertTrue(result.contains("  + numbers2: [22, 33, 44, 55]"));
        assertTrue(result.contains("  - numbers3: [3, 4, 5]"));
        assertTrue(result.contains("  + numbers4: [4, 5, 6]"));
        assertTrue(result.contains("  + obj1: {nestedKey=value, isNested=true}"));
        assertTrue(result.contains("  - setting1: Some value"));
        assertTrue(result.contains("  + setting1: Another value"));
        assertTrue(result.contains("  - setting2: 200"));
        assertTrue(result.contains("  + setting2: 300"));
        assertTrue(result.contains("  - setting3: true"));
        assertTrue(result.contains("  + setting3: none"));

        assertTrue(result.startsWith("{"));
        assertTrue(result.endsWith("}"));
    }

    @Test
    public void testYamlIdenticalValues() throws Exception {
        Path path1 = getFixturePath("identical1yaml.yaml");
        Path path2 = getFixturePath("identical2yaml.yaml");

        String result = Differ.generate(path1.toString(), path2.toString());

        assertTrue(result.contains("    setting1: Some value"));
        assertTrue(result.contains("    setting2: 200"));
        assertTrue(result.contains("    setting3: true"));
        assertTrue(result.contains("    key1: value1"));
        assertTrue(result.contains("    numbers1: [1, 2, 3, 4]"));
        assertTrue(result.contains("    numbers2: [2, 3, 4, 5]"));
        assertTrue(result.contains("    id: 45"));
        assertTrue(result.contains("    default: null"));
        assertTrue(result.contains("    checked: false"));
        assertTrue(result.contains("    numbers3: [3, 4, 5]"));
        assertTrue(result.contains("    chars1: [a, b, c]"));
        assertTrue(result.contains("    chars2: [d, e, f]"));

        assertFalse(result.contains("  - "));
        assertFalse(result.contains("  + "));

        assertTrue(result.startsWith("{"));
        assertTrue(result.endsWith("}"));
    }

    @Test
    public void testYamlDifferentValues() throws Exception {
        Path path1 = getFixturePath("diff1yaml.yaml");
        Path path2 = getFixturePath("diff2yaml.yaml");

        String result = Differ.generate(path1.toString(), path2.toString());

        assertTrue(result.contains("    chars1: [a, b, c]"));
        assertTrue(result.contains("  - chars2: [d, e, f]"));
        assertTrue(result.contains("  + chars2: false"));
        assertTrue(result.contains("  - checked: false"));
        assertTrue(result.contains("  + checked: true"));
        assertTrue(result.contains("  - default: null"));
        assertTrue(result.contains("  + default: [value1, value2]"));
        assertTrue(result.contains("  - id: 45"));
        assertTrue(result.contains("  + id: null"));
        assertTrue(result.contains("  - key1: value1"));
        assertTrue(result.contains("  + key2: value2"));
        assertTrue(result.contains("    numbers1: [1, 2, 3, 4]"));
        assertTrue(result.contains("  - numbers2: [2, 3, 4, 5]"));
        assertTrue(result.contains("  + numbers2: [22, 33, 44, 55]"));
        assertTrue(result.contains("  - numbers3: [3, 4, 5]"));
        assertTrue(result.contains("  + numbers4: [4, 5, 6]"));
        assertTrue(result.contains("  + obj1: {nestedKey=value, isNested=true}"));
        assertTrue(result.contains("  - setting1: Some value"));
        assertTrue(result.contains("  + setting1: Another value"));
        assertTrue(result.contains("  - setting2: 200"));
        assertTrue(result.contains("  + setting2: 300"));
        assertTrue(result.contains("  - setting3: true"));
        assertTrue(result.contains("  + setting3: none"));

        assertTrue(result.startsWith("{"));
        assertTrue(result.endsWith("}"));
    }

    @Test
    public void testUnsupportedFormatThrowsException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> Parser.getFormat("file.txt"));
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

    @Test
    public void testFormatEmptyDiff() {
        List<Map<String, Object>> emptyDiff = List.of();
        String expected = "{\n}";
        assertEquals(expected, StylishFormatter.format(emptyDiff));
    }

    @Test
    public void testFormatAddedValue() {
        List<Map<String, Object>> diff = List.of(
                Map.of(
                        "key", "newKey",
                        "status", "added",
                        "value", "newValue"
                )
        );

        String expected = "{\n  + newKey: newValue\n}";

        assertEquals(expected, StylishFormatter.format(diff));
    }

    @Test
    public void testFormatRemovedValue() {
        List<Map<String, Object>> diff = List.of(
                Map.of(
                        "key", "oldKey",
                        "status", "removed",
                        "value", "oldValue"
                )
        );

        String expected = "{\n  - oldKey: oldValue\n}";

        assertEquals(expected, StylishFormatter.format(diff));
    }

    @Test
    public void testFormatUpdatedValue() {
        List<Map<String, Object>> diff = List.of(
                Map.of(
                        "key", "updatedKey",
                        "status", "changed",
                        "oldValue", "before",
                        "newValue", "after"
                )
        );

        String expected = "{\n  - updatedKey: before\n  + updatedKey: after\n}";

        assertEquals(expected, StylishFormatter.format(diff));
    }

    @Test
    public void testFormatUnchangedValue() {
        List<Map<String, Object>> diff = List.of(
                Map.of(
                        "key", "sameKey",
                        "status", "unchanged",
                        "value", "sameValue"
                )
        );

        String expected = "{\n    sameKey: sameValue\n}";

        assertEquals(expected, StylishFormatter.format(diff));
    }
}
