package pl.put.poznan.transformer.logic;

/**
 * TextTransformer allows easy configuration and use of text transformations
 */
public class TextTransformer {
    private TextTransform transform;

    /**
     * Constructs and configures TextTransformer
     * @param transformNames list of transformation names used for configuration
     * @throws UnknownTransform at least one of provided transformation names could not be recognized
     */
    public TextTransformer(String[] transformNames) throws UnknownTransform {
        configure(transformNames);
    }

    /**
     * Changes currently performed transformation to new one
     * @param transformNames list of transformation names used for building new text transformation.
     *                       Listed transformations will be performed in reversed order in the resulting transformation.
     * @throws UnknownTransform at least one of provided transformation names could not be recognized
     */
    public void configure(String[] transformNames) throws UnknownTransform {
        transform = new Identity();
        for (String name : transformNames) {
            switch (name) {
                case "upper":
                    transform = new Upper(transform);
                    break;
                case "lower":
                    transform = new Lower(transform);
                    break;
                case "inverse":
                    transform = new Inverse(transform);
                    break;
                case "capitalize":
                    transform = new Capitalize(transform);
                    break;
                case "numbertotext":
                    transform = new NumberToText(transform);
                    break;
                case "expand":
                    transform = new Expand(transform);
                    break;
                case "collapse":
                    transform = new Collapse(transform);
                    break;
                case "mathtexttosymbol":
                    transform = new MathTextToSymbol(transform);
                    break;
                case "symboltomathtext":
                    transform = new SymbolToMathText(transform);
                    break;
                case "latexescape":
                    transform = new LatexEscape(transform);
                    break;
                case "duplicatedelete":
                    transform = new DuplicateDelete(transform);
                    break;
                default:
                    throw new UnknownTransform(name);
            }
        }
    }

    /**
     * Performs transformation
     * @param text input string that will be transformed
     * @return the result of transformation
     */
    public String transform(String text){
        return transform.transform(text);
    }

}
