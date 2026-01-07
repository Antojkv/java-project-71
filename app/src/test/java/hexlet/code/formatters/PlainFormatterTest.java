package hexlet.code.formatters;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlainFormatterTest {

    @Test
    public void testPlainFormatter() {
        assertEquals("null", PlainFormatter.formatValue(null));
        assertEquals("'text'", PlainFormatter.formatValue("text"));
        assertEquals("[complex value]", PlainFormatter.formatValue(List.of("a", "b", "c")));
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
        final int oldTimeout = 50;
        final int newTimeout = 20;
        diff.put("key", "timeout");
        diff.put("status", "changed");
        diff.put("oldValue", oldTimeout);
        diff.put("newValue", newTimeout);

        List<Map<String, Object>> diff1 = List.of(diff);
        String result = PlainFormatter.format(diff1);

        assertEquals("Property 'timeout' was updated. From 50 to 20", result.trim());
    }

    @Test
    public void testFormatChangedWithComplexValue() {
        Map<String, Object> diff = new HashMap<>();
        diff.put("key", "data");
        diff.put("status", "changed");
        diff.put("oldValue", List.of("a", "b", "c"));
        diff.put("newValue", Map.of("key", "value"));

        List<Map<String, Object>> diff1 = List.of(diff);
        String result = PlainFormatter.format(diff1);

        assertEquals("Property 'data' was updated. From [complex value] to [complex value]",
                result.trim());
    }
}
