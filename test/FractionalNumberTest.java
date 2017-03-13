import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class FractionalNumberTest {


    @Test
    public void testToString() throws Exception {
        List<Integer> numberUnion = Arrays.asList(0, -1, 8, 9, 2, 1);
        assertEquals("-189.21", new FractionalNumber("-189.21").toString());
    }

    @Test
    public void plus() {
        assertEquals("-0.6",(new FractionalNumber("-0.9").plus(new FractionalNumber("0.3"))).toString());
        assertEquals("-1.2",(new FractionalNumber("-0.9").plus(new FractionalNumber("-0.3"))).toString());
        assertEquals("-1.0", (new FractionalNumber("-0.6").plus(new FractionalNumber("-0.4"))).toString());
        assertEquals("110.50", (new FractionalNumber("55.45").plus(new FractionalNumber("55.05"))).toString());
        assertEquals("1118888.9999",(new FractionalNumber("1000000.101").plus(new FractionalNumber("118888.8989"))).toString());
        assertEquals("-30.9", (new FractionalNumber("-15.6").plus(new FractionalNumber("-15.3"))).toString());
        assertEquals("30.9", (new FractionalNumber("15.6").plus(new FractionalNumber("15.3"))).toString());
        assertEquals("0.9", (new FractionalNumber("0.6").plus(new FractionalNumber("0.3"))).toString());
        assertEquals("1.0", (new FractionalNumber("0.6").plus(new FractionalNumber("0.4"))).toString());
        assertEquals("-1.0", (new FractionalNumber("-0.6").plus(new FractionalNumber("-0.4"))).toString());
        assertEquals("1.2",(new FractionalNumber("0.9").plus(new FractionalNumber("0.3"))).toString());
        assertEquals("0.6",(new FractionalNumber("0.9").plus(new FractionalNumber("-0.3"))).toString());
    }

    @Test
    public void minus() {
        assertEquals("-1.0", (new FractionalNumber("-0.6").minus(new FractionalNumber("0.4"))).toString());
        assertEquals("-3.0", (new FractionalNumber("5.9").minus(new FractionalNumber("8.9"))).toString());
        assertEquals("0.0", (new FractionalNumber("8.9").minus(new FractionalNumber("8.9"))).toString());
        assertEquals("-14.8", (new FractionalNumber("-5.9").minus(new FractionalNumber("8.9"))).toString());
        assertEquals("-3.0", (new FractionalNumber("5.9").minus(new FractionalNumber("8.9"))).toString());
        assertEquals("-0.3",(new FractionalNumber("-0.9").minus(new FractionalNumber("-0.6"))).toString());
        assertEquals("1.5",(new FractionalNumber("0.9").minus(new FractionalNumber("-0.6"))).toString());
        assertEquals("-0.3",(new FractionalNumber("-0.9").minus(new FractionalNumber("-0.6"))).toString());
    }

    @Test
    public void multiplication(){
        assertEquals("00.09",(new FractionalNumber("-0.3").multiplication(new FractionalNumber("-0.3"))).toString());
        assertEquals("09.00",(new FractionalNumber("-3.0").multiplication(new FractionalNumber("-3.0"))).toString());
        assertEquals("0121.00",(new FractionalNumber("11.0").multiplication(new FractionalNumber("11.0"))).toString());
        assertEquals("1089.00",(new FractionalNumber("33.0").multiplication(new FractionalNumber("33.0"))).toString());
        assertEquals("36.00",(new FractionalNumber("6.0").multiplication(new FractionalNumber("6.0"))).toString());
        assertEquals("1089.00",(new FractionalNumber("33.0").multiplication(new FractionalNumber("33.0"))).toString());
        assertEquals("099.00",(new FractionalNumber("33.0").multiplication(new FractionalNumber("3.0"))).toString());
        assertEquals("-1089.00",(new FractionalNumber("33.0").multiplication(new FractionalNumber("-33.0"))).toString());
        assertEquals("000.00",(new FractionalNumber("33.0").multiplication(new FractionalNumber("0.0"))).toString());
        assertEquals("00.27",(new FractionalNumber("0.9").multiplication(new FractionalNumber("0.3"))).toString());
        assertEquals("02.47",(new FractionalNumber("-1.9").multiplication(new FractionalNumber("-1.3"))).toString());
    }

    @Test
    public void rounding(){
        assertEquals("1.40", (new FractionalNumber("1.399").rounding(2).toString()));
        assertEquals("1.39", (new FractionalNumber("1.386").rounding(2).toString()));
        assertEquals("10.0", (new FractionalNumber("9.99").rounding(1).toString()));
        assertEquals("10", (new FractionalNumber("10").rounding(2).toString()));
        assertEquals("1.39", (new FractionalNumber("1.386").rounding(2).toString()));
        assertEquals("10", (new FractionalNumber("9.9").rounding(0).toString()));
        assertEquals("10", (new FractionalNumber("10").rounding(2).toString()));
        assertEquals("10.0", (new FractionalNumber("9.99").rounding(1).toString()));
        assertEquals("-10.0", (new FractionalNumber("-9.99").rounding(1).toString()));
    }

    @Test
    public void toDouble() throws Exception{
        assertEquals(9.99, new FractionalNumber("9.99").toDouble(), 1e-5);
        assertEquals(0.123456789, new FractionalNumber("0.123456789").toDouble(), 1e-5);
        assertEquals(0.99, new FractionalNumber("0.99").toDouble(), 1e-5);
        assertEquals(3.0, new FractionalNumber("3").toDouble(), 1e-5);
    }

}