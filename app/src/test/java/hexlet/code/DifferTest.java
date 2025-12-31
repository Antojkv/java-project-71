package hexlet.code;

import org.junit.jupiter.api.Test;
import java.nio.file.Path;
import java.nio.file.Paths;

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
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            Parser.getFormat("file.txt");
        });
        assertEquals("Unsupported file format: file.txt", exception.getMessage());
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
}
