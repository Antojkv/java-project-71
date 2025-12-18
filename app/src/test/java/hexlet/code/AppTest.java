package hexlet.code;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AppTest {
    @Test
    public void testSimpleMath() {
        assertEquals(4, 2 + 2, "2+2 должно быть 4");
    }
    
    @Test
    public void testAppCreation() {
        App app = new App();
        assertNotNull(app, "Объект App должен создаваться");
    }
}
