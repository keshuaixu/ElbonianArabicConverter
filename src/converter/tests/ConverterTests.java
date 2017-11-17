package converter.tests;

import converter.ElbonianArabicConverter;
import converter.exceptions.MalformedNumberException;
import converter.exceptions.ValueOutOfBoundsException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test cases for the ElbonianArabicConverter class.
 */
public class ConverterTests {

    @Test
    public void ElbonianToArabicSampleTest() throws MalformedNumberException, ValueOutOfBoundsException {
        ElbonianArabicConverter converter = new ElbonianArabicConverter("1");
        assertEquals(converter.toElbonian(), "I");
    }

    @Test
    public void ElbonianToArabicMMM() throws MalformedNumberException, ValueOutOfBoundsException {
        ElbonianArabicConverter converter = new ElbonianArabicConverter("3000");
        assertEquals(converter.toElbonian(), "MMM");
    }

    @Test
    public void ElbonianToArabicXX() throws MalformedNumberException, ValueOutOfBoundsException {
        ElbonianArabicConverter converter = new ElbonianArabicConverter("20");
        assertEquals(converter.toElbonian(), "XX");
    }

    @Test
    public void ElbonianToArabicMCXIThreeTimes() throws MalformedNumberException, ValueOutOfBoundsException {
        ElbonianArabicConverter converter = new ElbonianArabicConverter("4000");
        assertEquals(converter.toElbonian(), "MMMDeC");
    }

    @Test
    public void ElbonianToArabicMultiple45() throws MalformedNumberException, ValueOutOfBoundsException {
        ElbonianArabicConverter converter = new ElbonianArabicConverter("945");
        assertEquals(converter.toElbonian(), "DemV");
    }

    @Test
    public void ArabicToElbonianSampleTest() throws MalformedNumberException, ValueOutOfBoundsException {
        ElbonianArabicConverter converter = new ElbonianArabicConverter("I");
        assertEquals(converter.toArabic(), 1);
    }

    @Test
    public void ArabicToElbonianAllLetters() throws MalformedNumberException, ValueOutOfBoundsException {
        ElbonianArabicConverter converter = new ElbonianArabicConverter("MDeCLmXVwI");
        assertEquals(converter.toArabic(), 2110);
    }
    @Test
    public void ArabicToElbonianAllLetters2() throws MalformedNumberException, ValueOutOfBoundsException {
        ElbonianArabicConverter converter = new ElbonianArabicConverter("MMCX");
        assertEquals(converter.toArabic(), 2110);
    }
    @Test(expected = MalformedNumberException.class)
    public void malformedNumberTest() throws MalformedNumberException, ValueOutOfBoundsException {
        (new ElbonianArabicConverter("ABC")).toArabic();
    }

    @Test(expected = MalformedNumberException.class)
    public void malformedNumberTest2() throws MalformedNumberException, ValueOutOfBoundsException {
        (new ElbonianArabicConverter("MMMM")).toArabic();
    }

    @Test(expected = ValueOutOfBoundsException.class)
    public void valueOutOfBoundsTest() throws MalformedNumberException, ValueOutOfBoundsException {
        (new ElbonianArabicConverter("0")).toElbonian();
    }

}
