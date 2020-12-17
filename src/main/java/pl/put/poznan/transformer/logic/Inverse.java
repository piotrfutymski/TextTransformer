package pl.put.poznan.transformer.logic;

import java.util.ArrayList;
import static java.lang.Character.isUpperCase;
import static java.lang.Character.toLowerCase;
import static java.lang.Character.toUpperCase;
/**
 *
 * Class extending TextDecorator, it performs action of inverting text.
 *
 */
public class Inverse extends TextDecorator
{
    public Inverse(TextTransform t) {
        super(t);
    }

    /**
     * It creates String, which is inverted form of given String, and returns it.
     * @param text is a String which is meant to be inverted.
     * @return inverted String.
     */
    @Override
    protected String operation(String text) {

        ArrayList<Boolean> isUpper = new ArrayList<Boolean>();
        for (int i = 0; i < text.length(); i++){
            if (isUpperCase(text.charAt(i)))
                isUpper.add(true);
            else
                isUpper.add(false);
        }
        StringBuilder sbr = new StringBuilder();
        // Reversing String
        for (int i = 0; i < text.length(); i++)
        {
            final char ch = text.charAt(text.length() - i - 1);
            if (isUpper.get(i))
                sbr.append(toUpperCase(ch));
            else
                sbr.append(toLowerCase(ch));
        }
        return sbr.toString();
    }
}
