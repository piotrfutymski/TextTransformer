package pl.put.poznan.transformer.logic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    private static final Logger logger = LoggerFactory.getLogger(NumberToText.class);

    public Inverse(TextTransform t) {
        super(t);
    }

    /**
     * It creates String, which is inverted form of given String, and returns it.
     * @param text is a String which is meant to be inverted.
     * @return inverted String.
     */
    @Override
    protected String operation(String text)
    {
        logger.debug("Transformation inverse started.");
        ArrayList<Boolean> isUpper = new ArrayList<>();
        for (int i = 0; i < text.length(); i++){
            if (isUpperCase(text.charAt(i)))
                isUpper.add(true);
            else
                isUpper.add(false);
        }
        logger.debug("Sizes array completed.");
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
        logger.debug("String reversion completed.");
        logger.debug("Transformation inverse completed.");
        return sbr.toString();
    }
}
