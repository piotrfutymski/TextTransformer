package pl.put.poznan.transformer.logic;

/**
 * Provides implementation of Capitalize operation which transforms every word first letter to upper case.
 */
public class Capitalize extends TextDecorator {

    /**
     * Constructor of Capitalize
     * @param t transformation that will be performed after the operation
     */
    public Capitalize(TextTransform t) {
        super(t);
    }

    /**
     * transforms every word first letter to upper case
     * @param text text to be transformed
     */
    @Override
    protected String operation(String text) {
        StringBuilder tmp = new StringBuilder(text);
        boolean white = true;
        for( int i =0; i < tmp.length(); i++){
            if(white)
                tmp.setCharAt(i, Character.toUpperCase(tmp.charAt(i)));
            white = Character.isWhitespace(tmp.charAt(i));
        }
        return tmp.toString();
    }
}
