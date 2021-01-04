package pl.put.poznan.transformer.logic;

/**
 * Represent complex dynamic text transformation which consists of it's operation and other text transformation.
 * Provides abstract method for performing operation on the text and ensures that the transform consists of
 * executing defined operation and then calling the external text transformation provided in the constructor
 */
public abstract class TextDecorator implements TextTransform{
    protected TextTransform transform;

    /**
     * Constructor of TextDecorator
     * @param t transformation that will be performed after the operation
     */
    public TextDecorator(TextTransform t)
    {
        transform = t;
    }

    /**
     * Function that defines the operation that the class performs on the text
     * @param text input string
     * @return text changed respectively to the defined operation
     */
    protected abstract String operation(String text);

    /**
     * Ensures that after operation the provided transformation will be executed
     * @param text input string that will be transformed
     * @return the result of transformation
     */
    @Override
    final public String transform(String text) {
        return transform.transform(operation(text));
    }
}
