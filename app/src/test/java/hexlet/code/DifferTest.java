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

        assertTrue(result.contains("    host: hexlet.io"));
        assertTrue(result.contains("    timeout: 50"));
        assertTrue(result.contains("    proxy: 123.234.53.22"));
        assertTrue(result.contains("    follow: false"));

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

        assertTrue(result.contains("    host: hexlet.io"));
        assertTrue(result.contains("  + timeout: 20"));
        assertTrue(result.contains("  - proxy: 123.234.53.22"));
        assertTrue(result.contains("  + follow: true"));
        assertTrue(result.contains("  + verbose: true"));

        assertTrue(result.startsWith("{"));
        assertTrue(result.endsWith("}"));
    }
    @Test
    public void testYamlIdenticalValues() throws Exception {
        Path path1 = getFixturePath("identical1yaml.yaml");
        Path path2 = getFixturePath("identical2yaml.yaml");

        String result = Differ.generate(path1.toString(), path2.toString());

        assertTrue(result.contains("    host: hexlet.io"));
        assertTrue(result.contains("    timeout: 50"));
        assertTrue(result.contains("    proxy: 123.234.53.22"));
        assertTrue(result.contains("    follow: false"));

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

        assertTrue(result.contains("    host: hexlet.io"));
        assertTrue(result.contains("  + timeout: 20"));
        assertTrue(result.contains("  - proxy: 123.234.53.22"));
        assertTrue(result.contains("  + follow: true"));
        assertTrue(result.contains("  + verbose: true"));

        assertTrue(result.startsWith("{"));
        assertTrue(result.endsWith("}"));
    }
    @Test
    public void testUnsupportedFormatThrowsException() {
        RuntimeException exception = assertThrows(IllegalArgumentException.class, () -> {
            Parser.getFormat("file.txt");
        });
        assertEquals("Unsupported file format: file.txt", exception.getMessage());
    }
}
