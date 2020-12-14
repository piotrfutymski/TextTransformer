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
            if(name == "upper")
                 transform = new Upper(transform);
            else
                throw new UnknownTransform(name);
        }
    }
    public String transform(String text){
        return transform.transform(text);
    }

}
