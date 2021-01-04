package pl.put.poznan.transformer.logic;

/**
 * Exception thrown when unknown transformation name is encountered
 */
public class UnknownTransform extends Exception {
    private String transformName;

    /**
     * Constructor
     * @param name unknown transformation name
     */
    public UnknownTransform(String name) {
        transformName=name;
    }

    /**
     * Get the encountered unknown transformation name
     * @return unknown transformation name
     */
    public String getTransformName() {
        return transformName;
    }

}
