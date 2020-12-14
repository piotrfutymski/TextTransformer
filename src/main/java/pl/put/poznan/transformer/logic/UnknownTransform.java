package pl.put.poznan.transformer.logic;

public class UnknownTransform extends Exception {
    private String transformName;

    public UnknownTransform(String name) {
        transformName=name;
    }

    public String getTransformName() {
        return transformName;
    }

}
