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

class SymbolToMathTextTest {
    private TextTransform textTransform;
    private SymbolToMathText symboltotext;
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
                new String[]{" większe ", ">"},
                new String[]{" mniejsze ", "<"},
                new String[]{" równe ","="},
                new String[]{" nie","!"},
                new String[]{" dodać ", "+"},
                new String[]{" plus ", "+"},
                new String[]{" minus ", "-"},
                new String[]{" odjąć ", "-"},
                new String[]{" razy ", "*"},
                new String[]{" podzielić przez ", "/"}
        );
        List<List<String>> transformsList = new ArrayList<>();
        for (String[] transform : transforms) {
            transformsList.add(Arrays.asList(transform));
        }
        when(loader.getJSONList()).thenReturn(transformsList);
        symboltotext = new SymbolToMathText(textTransform, loader);
    }

    @Test
    void testOperationWhenMoreEqual() {
        assertEquals("a większe  równe 6", symboltotext.operation("a>=6"));
    }

    @Test
    void testOperationWhenNotEqual() {
        assertEquals("1 nie równe 2", symboltotext.operation("1!=2"));
    }

    @Test
    void testOperationWhenMinus() {
        assertEquals("3 dodać 5", symboltotext.operation("3+5"));
    }

    @Test
    void testOperationWhenNoChange(){
        assertEquals("&^ brak zmian", symboltotext.operation("&^ brak zmian"));
    }
}
