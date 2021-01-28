package pl.put.poznan.transformer.logic;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ExpandTest {
    private TextTransform textTransform;
    private Expand expand;
    private JSONLoader loader;

    @BeforeEach
    void setUp(){
        textTransform = mock(TextTransform.class);
        when(textTransform.transform(anyString())).thenAnswer(
                new Answer() {
                    public Object answer(InvocationOnMock invocation) {
                        String text = (String)invocation.getArguments()[0];
                        return text;
                    }
                });
        loader = mock(JSONLoader.class);
        List<String[]> transforms = Arrays.asList(
                new String[]{"np.", "na przykład"},
                new String[]{"m.in.", "między innymi"},
                new String[]{"itd.","i tak dalej"},
                new String[]{"prof.","profesor"},
                new String[]{"dr", "doktor"},
                new String[]{"itp.", "i tym podobne"}
        );
        List<List<String>> transformsList = new ArrayList<>();
        for (String[] transform : transforms) {
            transformsList.add(Arrays.asList(transform));
        }
        when(loader.getJSONList()).thenReturn(transformsList);
        expand = new Expand(textTransform, loader);
    }

    @Test
    void testOperationWhenAllLower(){
        assertEquals("na przykład", expand.operation("np."));
    }

    @Test
    void testOperationWhenFirstLetterCapital(){
        assertEquals("I tym podobne", expand.operation("Itp."));
    }

    @Test
    void testOperationWhenAllUpper(){
        assertEquals("I TAK DALEJ", expand.transform("ITD."));
    }

    @Test
    void testOperationWhenNoChange(){
        assertEquals("Tu nie ma nic do zmiany i t d.", expand.transform("Tu nie ma nic do zmiany i t d."));
    }

    @Test
    void testOperationWhenEmptyString(){
        assertEquals("", expand.transform(""));
    }

    @Test
    void testOperationWhenNull(){
        assertThrows(NullPointerException.class, () -> { expand.operation(null); });
    }

}