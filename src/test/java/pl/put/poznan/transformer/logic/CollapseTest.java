package pl.put.poznan.transformer.logic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CollapseTest {
    private TextTransform textTransform;
    private Collapse collapse;
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
        collapse = new Collapse(textTransform, loader);
    }

    @Test
    void testOperationWhenAllLower(){
        assertEquals("np.", collapse.operation("na przykład"));
    }

    @Test
    void testOperationWhenFirstLetterCapital(){
        assertEquals("Itp.", collapse.operation("I tym podobne"));
    }

    @Test
    void testOperationWhenAllUpper(){
        assertEquals("ITD.", collapse.transform("I TAK DALEJ"));
    }

    @Test
    void testOperationWhenNoChange(){
        assertEquals("Tu nie ma nic do zmiany i t d.", collapse.transform("Tu nie ma nic do zmiany i t d."));
    }

    @Test
    void testOperationWhenEmptyString(){
        assertEquals("", collapse.transform(""));
    }

    @Test
    void testOperationWhenNull(){
        assertThrows(NullPointerException.class, () -> { collapse.operation(null); });
    }

}