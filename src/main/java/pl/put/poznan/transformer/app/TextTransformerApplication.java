package pl.put.poznan.transformer.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.put.poznan.transformer.logic.TextTransformer;
import pl.put.poznan.transformer.logic.UnknownTransform;


@SpringBootApplication(scanBasePackages = {"pl.put.poznan.transformer.rest"})
public class TextTransformerApplication {

    public static void main(String[] args) {
        String[] settings = new String[2];
        settings[0]="upper";
        settings[1]="upper";

        try {
            TextTransformer t = new TextTransformer(settings);
            System.out.println(t.transform("hej"));
        } catch (UnknownTransform exc) {
            System.out.println("Transform " + exc.getTransformName() + " is unknown");
        }

        //SpringApplication.run(TextTransformerApplication.class, args);
    }
}
