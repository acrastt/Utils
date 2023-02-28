import org.example.acrastt.utils.SerializableObject;
import org.junit.jupiter.api.Test;

import java.io.Serializable;

class SerializableObjectTest {
    @Test
    void test() {
        assert new SerializableObject() instanceof Serializable;
    }
}
