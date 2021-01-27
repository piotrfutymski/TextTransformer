package pl.put.poznan.transformer.logic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;


/**
 *
 * Class extending TextDecorator, it performs action of deleting all repetitions of words in String.
 *
 */
public class DuplicateDelete extends TextDecorator {

    private static final Logger logger = LoggerFactory.getLogger(DuplicateDelete.class);

    public DuplicateDelete(TextTransform t) {
        super(t);
    }

    /**
     * Main function, which is deletes all repetitions in String, and returns it.
     * @param text is a String which is meant to be processed.
     * @return String without repetitions.
     */
    @Override
    protected String operation(String text)
    {
        logger.debug("Transformation DuplicateDelete started.");
        String[] splited = text.split(" +");
        StringBuilder result = new StringBuilder();
        String previous = "";
        for (String split : splited)
        {
            if (!previous.equals(split))
                result.append(" ").append(split);
            previous = split;
        }
        logger.debug("Transformation DuplicateDelete ended.");

        return result.toString();
    }
}
