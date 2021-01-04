package pl.put.poznan.transformer.logic;

/**
 * Interface that represents text transformation
 */
public interface TextTransform {
    /**
     * Function that perform the transformation
     * @param text input string that will be transformed
     * @return the result of transformation
     */
    public abstract String transform(String text);
}
