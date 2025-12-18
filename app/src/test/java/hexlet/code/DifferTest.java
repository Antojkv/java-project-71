package hexlet.code;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DifferTest {
    @Test
    public void testDifferCreation() {
        Differ differ = new Differ();
        assertNotNull(differ, "Объект Differ должен создаваться");
    }
}
