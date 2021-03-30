package guru.springframework;

import org.junit.jupiter.api.Test;

import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
public class InlineMockTest {


    @Test
    void testInlinMock(){
        Map mapMock = mock(Map.class);

        assertEquals(mapMock.size(), 0);
    }
}
