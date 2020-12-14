package pl.put.poznan.transformer.logic;

public class Identity implements TextTransform{

    @Override
    public String transform(String text) {
        return text;
    }
}
