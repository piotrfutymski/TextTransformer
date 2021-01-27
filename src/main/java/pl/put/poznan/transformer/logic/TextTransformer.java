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
            if(name.equals("upper"))
                 transform = new Upper(transform);
            else if(name.equals("lower"))
                transform = new Lower(transform);
            else if(name.equals("inverse"))
                transform = new Inverse(transform);
            else if(name.equals("capitalize"))
                transform = new Capitalize(transform);
            else if(name.equals("numbertotext"))
                transform = new NumberToText(transform);
            else if(name.equals("expand"))
                transform = new Expand(transform);
            else if(name.equals("collapse"))
                transform = new Collapse(transform);
            else if(name.equals("mathtexttosymbol"))
                transform = new MathTextToSymbol(transform);
            else if(name.equals("symboltomathtext"))
                transform = new SymbolToMathText(transform);
            else if(name.equals("latexescape"))
                transform = new LatexEscape(transform);
            else
                throw new UnknownTransform(name);
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
