package pl.put.poznan.transformer.rest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.put.poznan.transformer.logic.TextTransformer;
import pl.put.poznan.transformer.logic.UnknownTransform;

import java.util.Arrays;


@RestController
@RequestMapping("/{text}")
public class TextTransformerController {

    private static final Logger logger = LoggerFactory.getLogger(TextTransformerController.class);

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public String get(@PathVariable String text,
                              @RequestParam(value="transforms") String[] transforms) {

        // log the parameters
        logger.debug(text);
        logger.debug(Arrays.toString(transforms));


        TextTransformer transformer = null;
        try {
            transformer = new TextTransformer(transforms);
        } catch (UnknownTransform exc) {
            System.out.println("Transform " + exc.getTransformName() + " is unknown");
        }
        return "{\"result\": \"" + transformer.transform(text) + "\"}";
    }

    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    public String post(@PathVariable String text,
                      @RequestBody String[] transforms) {

        // log the parameters
        logger.debug(text);
        logger.debug(Arrays.toString(transforms));

        TextTransformer transformer = null;
        try {
            transformer = new TextTransformer(transforms);
        } catch (UnknownTransform exc) {
            System.out.println("Transform " + exc.getTransformName() + " is unknown");
        }
        logger.info("Transforms completed.");
        return "{\"result\": \"" + transformer.transform(text) + "\"}";
    }



}


