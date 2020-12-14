package pl.put.poznan.transformer.logic;

public abstract class TextDecorator implements TextTransform{
    protected TextTransform transform;

    public TextDecorator(TextTransform t)
    {
        transform = t;
    }

    protected abstract String operation(String text);

    @Override
    final public String transform(String text) {
        return transform.transform(operation(text));
    }
}
