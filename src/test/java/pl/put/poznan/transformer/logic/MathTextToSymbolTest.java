package pl.put.poznan.transformer.logic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class MathTextToSymbolTest {
    private TextTransform textTransform;
    private MathTextToSymbol texttosymbol;

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
        texttosymbol = new MathTextToSymbol(textTransform);
    }

    @Test
    void testOperationLess() {
        assertEquals("<", texttosymbol.operation(" mniejsze od "));
    }

    @Test
    void testOperationMinus() {
        assertEquals("+", texttosymbol.operation(" plus "));
    }

    @Test
    void testOperationNoChange(){
        assertEquals("&^ brak zmian", texttosymbol.operation("&^ brak zmian"));
    }
}