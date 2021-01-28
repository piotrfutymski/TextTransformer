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

class MathTextToSymbolTest {
    private TextTransform textTransform;
    private MathTextToSymbol texttosymbol;
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
                new String[]{" nie równe ","!="},
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
        texttosymbol = new MathTextToSymbol(textTransform, loader);
    }

    @Test
    void testOperationWhenLess() {
        assertEquals("<", texttosymbol.operation(" mniejsze "));
    }

    @Test
    void testOperationWhenMinus() {
        assertEquals("+", texttosymbol.operation(" plus "));
    }

    @Test
    void testOperationWhenDivision() {
        assertEquals("/", texttosymbol.operation(" podzielić przez "));
    }

    @Test
    void testOperationWhenNoChange(){
        assertEquals("&^ brak zmian", texttosymbol.operation("&^ brak zmian"));
    }
}