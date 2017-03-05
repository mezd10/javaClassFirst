import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class NumberTest {


    @Test
    public void testToString() throws Exception {
        List<Integer> numberUnion = Arrays.asList(0, -1, 8, 9, 2, 1);
        assertEquals("-189.21", new Number(4, 2, numberUnion).toString());
    }

    @Test
    public void plus() {
        List<Integer> numberUnion = new ArrayList<>();
        List<Integer> otherNumberUnion = new ArrayList<>();
        Collections.addAll(numberUnion, -1, 3, 9);
        Collections.addAll(otherNumberUnion, 1, 3, 9);
        assertEquals("00.0", (new Number(2, 1, numberUnion).plus(new Number(2, 1, otherNumberUnion))).toString());
        assertEquals("110.50", (new Number("55.45").plus(new Number("55.05"))).toString());
        assertEquals("1118888.9999",(new Number("1000000.101").plus(new Number("118888.8989"))).toString());
        assertEquals("-30.9", (new Number("-15.6").plus(new Number("-15.3"))).toString());
    }

    @Test
    public void minus() {
        List<Integer> numberUnion = new ArrayList<>();
        List<Integer> otherNumberUnion = new ArrayList<>();
        Collections.addAll(numberUnion, -1, 3);
        Collections.addAll(otherNumberUnion, -1, 5);
        assertEquals("0.2", (new Number(1, 1, numberUnion).minus(new Number(1, 1, otherNumberUnion))).toString());
        assertEquals("-3.0", (new Number("5.9").minus(new Number("8.9"))).toString());
    }

}