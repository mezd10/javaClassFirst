import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class NumberTest {


    @Test
    public void testToString() throws Exception {
        List<Integer> numberUnion = Arrays.asList(1, 8, 9, 2, 1);
        assertEquals("189.21", new Number(3, 2, numberUnion).toString());
    }

    @Test
    public void plus() {
        List<Integer> numberUnion = new ArrayList<>();
        List<Integer> otherNumberUnion = new ArrayList<>();
        Collections.addAll(numberUnion, 9, 1, 1);
        Collections.addAll(otherNumberUnion, -1, -1);
        assertEquals("90.0", (new Number(2, 1, numberUnion).plus(new Number(1, 1, otherNumberUnion))).toString());
    }

    @Test
    public void minus() {
        List<Integer> numberUnion = new ArrayList<>();
        List<Integer> otherNumberUnion = new ArrayList<>();
        Collections.addAll(numberUnion, 1, 9, 9);
        Collections.addAll(otherNumberUnion, 1, 0, 3);
        assertEquals("96", (new Number(3, 0, numberUnion).minus(new Number(3, 0, otherNumberUnion))).toString());
    }

}