package pl.put.poznan.transformer.logic;

/**
 * This is just an example to show that the logic should be outside the REST service.
 */
public class TextTransformer {
    private TextTransform transform;

    public TextTransformer(String[] transformNames) throws UnknownTransform {
        configure(transformNames);
    }

    public void configure(String[] transformNames) throws UnknownTransform {
        transform = new Identity();
        for (String name : transformNames) {
            switch (name) {
                case "upper":
                    transform = new Upper(transform);
                    break;
                case "inverse":
                    transform = new Inverse(transform);
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
                default:
                    throw new UnknownTransform(name);
            }
        }
    }
    public String transform(String text){
        return transform.transform(text);
    }

}
