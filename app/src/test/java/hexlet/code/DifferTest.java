package hexlet.code;

import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;



public class DifferTest {

    private static Path getFixturePath(String fileName) {
        return Paths.get("src", "test", "resources", "fixtures", fileName)
                .toAbsolutePath()
                .normalize();
    }

    private static String readExpectedFile(String fileName) throws Exception {
        Path path = Paths.get("src", "test", "resources", "fixtures", "expected", fileName);
        return Files.readString(path);
    }

    private static String normalize(String text) {
        String result = text.replace("[ ", "[").replace(" ]", "]");
        result = result.replace(" : ", ": ");
        return result.trim();
    }


    @Test
    public void testJsonStylish() throws Exception {
        String path1 = getFixturePath("diff1.json").toString();
        String path2 = getFixturePath("diff2.json").toString();
        String expected = readExpectedFile("stylish.txt");

        String actual = Differ.generate(path1, path2, "stylish");

        assertEquals(normalize(expected), normalize(actual));
    }

    @Test
    public void testJsonPlain() throws Exception {
        String path1 = getFixturePath("diff1.json").toString();
        String path2 = getFixturePath("diff2.json").toString();
        String expected = readExpectedFile("plain.txt");

        String actual = Differ.generate(path1, path2, "plain");

        assertEquals(normalize(expected), normalize(actual));
    }

    @Test
    public void testJsonJson() throws Exception {
        String path1 = getFixturePath("diff1.json").toString();
        String path2 = getFixturePath("diff2.json").toString();
        String expected = readExpectedFile("json.txt");

        String actual = Differ.generate(path1, path2, "json");

        assertEquals(normalize(expected), normalize(actual));
    }

    @Test
    public void testJsonDefault() throws Exception {
        String path1 = getFixturePath("diff1.json").toString();
        String path2 = getFixturePath("diff2.json").toString();
        String expected = readExpectedFile("default.txt");

        String actual = Differ.generate(path1, path2);

        assertEquals(normalize(expected), normalize(actual));
    }

    @Test
    public void testYamlStylish() throws Exception {
        String path1 = getFixturePath("diff1.yaml").toString();
        String path2 = getFixturePath("diff2.yaml").toString();
        String expected = readExpectedFile("stylish.txt");

        String actual = Differ.generate(path1, path2, "stylish");

        assertEquals(normalize(expected), normalize(actual));
    }

    @Test
    public void testYamlPlain() throws Exception {
        String path1 = getFixturePath("diff1.yaml").toString();
        String path2 = getFixturePath("diff2.yaml").toString();
        String expected = readExpectedFile("plain.txt");

        String actual = Differ.generate(path1, path2, "plain");

        assertEquals(normalize(expected), normalize(actual));
    }

    @Test
    public void testYamlJson() throws Exception {
        String path1 = getFixturePath("diff1.yaml").toString();
        String path2 = getFixturePath("diff2.yaml").toString();
        String expected = readExpectedFile("json.txt");

        String actual = Differ.generate(path1, path2, "json");

        assertEquals(normalize(expected), normalize(actual));
    }

    @Test
    public void testYamlDefault() throws Exception {
        String path1 = getFixturePath("diff1.yaml").toString();
        String path2 = getFixturePath("diff2.yaml").toString();
        String expected = readExpectedFile("default.txt");

        String actual = Differ.generate(path1, path2);

        assertEquals(normalize(expected), normalize(actual));
    }

    @Test
    public void testUnsupportedFormatThrowsException() {
        String path1 = getFixturePath("diff1.json").toString();
        String path2 = getFixturePath("diff2.json").toString();

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> Differ.generate(path1, path2, "invalid_format"));
    }
}
