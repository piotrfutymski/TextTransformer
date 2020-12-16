package pl.put.poznan.transformer.logic;

public class Upper extends TextDecorator {

    public Upper(TextTransform t) {
        super(t);
    }

    @Override
    protected String operation(String text) {
        return text.toUpperCase();
    }
}
