import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class AppTest {

    @org.junit.jupiter.api.Test
    public void testOperateSum() {
        assertEquals(8, App.operateWithNumbers(3, 5, "+"));
    }

    @org.junit.jupiter.api.Test
    public void testOperateMultiplication() {
        assertEquals(81, App.operateWithNumbers(9, 9, "*"));
    }

    @org.junit.jupiter.api.Test
    public void testOperateDevision() {
        assertEquals(0, App.operateWithNumbers(3, 10, "/"));
    }

    @org.junit.jupiter.api.Test
    public void testOperateSubstraction() {
        assertEquals(-2, App.operateWithNumbers(8, 10, "-"));
    }

    @org.junit.jupiter.api.Test
    public void testOneNumberMoreThenTen() {
        // ==== wrap a string in a class ByteArrayInputStream
        ByteArrayInputStream is = new ByteArrayInputStream("11+1".getBytes());
        // ==== replacing in
        System.setIn(is);
        assertThrows(IOException.class, () -> App.calculator());
    }

    @org.junit.jupiter.api.Test
    public void testNumbersFromDifferentNotation() {
        ByteArrayInputStream is = new ByteArrayInputStream("6 - II".getBytes());
        System.setIn(is);
        assertThrows(IOException.class, () -> App.calculator());
    }

    @org.junit.jupiter.api.Test
    public void testWrongOperator() {
        ByteArrayInputStream is = new ByteArrayInputStream("5~2".getBytes());
        System.setIn(is);
        assertThrows(IOException.class, () -> App.calculator());
    }

    @org.junit.jupiter.api.Test
    public void testRomaNegativeNumber() {
        ByteArrayInputStream is = new ByteArrayInputStream("IV-IX".getBytes());
        System.setIn(is);
        assertThrows(Exception.class, () -> App.calculator());
    }

    @org.junit.jupiter.api.Test
    public void testRomaZeroAnswer() {
        ByteArrayInputStream is = new ByteArrayInputStream("V-V".getBytes());
        System.setIn(is);
        assertThrows(Exception.class, () -> App.calculator());
    }

    @org.junit.jupiter.api.Test
    public void testConvertRomaToArabNumber() {
        assertEquals(8, App.romaToArab("VIII"));
    }

    @org.junit.jupiter.api.Test
    public void testConvertArabToRomaNumber() {
        assertEquals("XXXVI", App.arabToRoma(36));
    }

}
