package hexlet.code.formatters;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StylishFormatterTest {

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
    public void testFormatChangedValue() {
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
