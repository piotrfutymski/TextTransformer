package pl.put.poznan.transformer.logic;

/**
 * Provides implementation of upper operation which transforms whole text to upper case.
 */
public class Upper extends TextDecorator {

    public Upper(TextTransform t) {
        super(t);
    }

    /**
     * Transforms text to upper case.
     * @param text text to be transformed to upper case
     */
    @Override
    protected String operation(String text) {
        return text.toUpperCase();
    }
}
