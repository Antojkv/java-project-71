package hexlet.code;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class DifferTest {
    @Test
    public void testDifferCreation() {
        Differ differ = new Differ();
        assertNotNull(differ, "Объект Differ должен создаваться");
    }
}
