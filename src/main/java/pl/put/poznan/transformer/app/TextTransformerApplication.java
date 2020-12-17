package pl.put.poznan.transformer.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.put.poznan.transformer.logic.TextTransformer;
import pl.put.poznan.transformer.logic.UnknownTransform;

import java.util.ArrayList;


@SpringBootApplication(scanBasePackages = {"pl.put.poznan.transformer.rest"})
public class TextTransformerApplication {

    public static void main(String[] args) {
        //String[] settings = new String[2];
        ArrayList<String> settings = new ArrayList<String>();
        settings.add("numbertotext");

        try {
            TextTransformer t = new TextTransformer((String[]) settings.toArray(new String[0]));
            System.out.println(t.transform("Wsiadaj mala 745 1000,80 oproznijmy floata"));
        } catch (UnknownTransform exc) {
            System.out.println("Transform " + exc.getTransformName() + " is unknown");
        }

        //SpringApplication.run(TextTransformerApplication.class, args);
    }
}
