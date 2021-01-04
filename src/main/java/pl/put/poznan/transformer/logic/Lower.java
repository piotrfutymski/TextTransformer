package pl.put.poznan.transformer.logic;

/**
 * Provides implementation of Lower operation which transforms whole text to lower case.
 */
public class Lower extends TextDecorator {

    /**
     * Constructor of Lower
     * @param t transformation that will be performed after the operation
     */
    public Lower(TextTransform t) {
        super(t);
    }

    /**
     * Transforms text to lower case
     * @param text text to be transformed to lower case
     */
    @Override
    protected String operation(String text) {
        return text.toLowerCase();
    }
}
