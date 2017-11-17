package converter;

import converter.exceptions.MalformedNumberException;
import converter.exceptions.ValueOutOfBoundsException;
import jdk.nashorn.internal.runtime.regexp.joni.exception.ValueException;

import javax.swing.*;
import java.util.HashMap;
import java.util.stream.Collectors;

/**
 * This class implements a converter that takes a string that represents a number in either the
 * Elbonian or Arabic numeral form. This class has methods that will return a value in the chosen form.
 * s
 *
 * @version 3/18/17
 */
public class ElbonianArabicConverter {

    // A string that holds the number (Elbonian or Arabic) you would like to convert
    private final String number;

    private HashMap<Character, Integer> char2int = new HashMap<>();
    private String theWholeThing = "MMMDeCCCLmXXXVwIII";
    private int numberInt;


    /**
     * Constructor for the ElbonianArabic class that takes a string. The string should contain a valid
     * Elbonian or Arabic numeral. The String can have leading or trailing spaces. But there should be no
     * spaces within the actual number (ie. "9 9" is not ok, but " 99 " is ok). If the String is an Arabic
     * number it should be checked to make sure it is within the Elbonian number systems bounds. If the
     * number is Elbonian, it must be a valid Elbonian representation of a number.
     *
     * @param number A string that represents either a Elbonian or Arabic number.
     * @throws MalformedNumberException  Thrown if the value is an Elbonian number that does not conform
     *                                   to the rules of the Elbonian number system. Leading and trailing spaces should not throw an error.
     * @throws ValueOutOfBoundsException Thrown if the value is an Arabic number that cannot be represented
     *                                   in the Elbonian number system.
     */
    public ElbonianArabicConverter(String number) throws MalformedNumberException, ValueOutOfBoundsException {

        // TODO check to see if the number is valid, then set it equal to the string
        this.number = number;

        this.char2int.put('M', 1000);
        this.char2int.put('C', 100);
        this.char2int.put('X', 10);
        this.char2int.put('I', 1);
        this.char2int.put('D', 500);
        this.char2int.put('L', 50);
        this.char2int.put('V', 5);
        this.char2int.put('e', 400);
        this.char2int.put('m', 40);
        this.char2int.put('w', 4);
    }

    /**
     * Converts the number to an Arabic numeral or returns the current value as an int if it is already
     * in the Arabic form.
     *
     * @return An arabic value
     */
    public int toArabic() throws MalformedNumberException {
        try {
            // TODO: rule checker
            int tentativeArabic = this.number.chars()
                    .mapToObj(c -> (char) c)
                    .map(char2int::get)
                    .mapToInt(Integer::intValue).sum();
            String convertedBackElbonian = (new ElbonianArabicConverter(String.valueOf(tentativeArabic))).toElbonian();
            if (convertedBackElbonian.equals(this.number)) {
                return tentativeArabic;
            } else throw new MalformedNumberException(convertedBackElbonian);
        } catch (NullPointerException | ValueOutOfBoundsException e) {
            throw new MalformedNumberException(number);
        }
    }

    /**
     * Converts the number to an Elbonian numeral or returns the current value if it is already in the Elbonian form.
     *
     * @return An Elbonian value
     */
    public String toElbonian() throws ValueOutOfBoundsException {
        this.numberInt = Integer.parseInt(this.number);
        // TODO Fill in the method's body
        return toElbonianRecursion("", this.theWholeThing, 0);
    }

    private String toElbonianRecursion(String confirmedElbonian, String possibleElbonian, int confirmedArabic) throws ValueOutOfBoundsException {

        char currentElbonian = possibleElbonian.charAt(0);//todo check string size and make sure it is not zero
        int currentArabic = this.char2int.get(currentElbonian);
        int tentativeArabic = currentArabic + confirmedArabic;
        String tentativeElbonian = confirmedElbonian + String.valueOf(currentElbonian);
        try {
            if (tentativeArabic == this.numberInt) {
                return tentativeElbonian;
            } else if (tentativeArabic < this.numberInt) {
                return this.toElbonianRecursion(tentativeElbonian, possibleElbonian.substring(1), tentativeArabic);
                //todo check size of possibleElbonian
            } else {
                return this.toElbonianRecursion(confirmedElbonian, possibleElbonian.substring(1), confirmedArabic);
            }
        } catch (StringIndexOutOfBoundsException e) {
            throw new ValueOutOfBoundsException(this.number);
        }
    }
}
