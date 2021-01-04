package pl.put.poznan.transformer.logic;

/**
 * Identity is an implementation of TextTransform Interface. It represents transformation that does nothing
 */
public class Identity implements TextTransform{
    /**
     * Returns unchanged text
     * @param text is input string
     * @return unchanged input string
     */
    @Override
    public String transform(String text) {
        return text;
    }
}
