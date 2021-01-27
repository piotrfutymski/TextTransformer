package pl.put.poznan.transformer.logic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SymbolToMathTextTest {
    private TextTransform textTransform;
    private SymbolToMathText symboltotext;



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
        symboltotext = new SymbolToMathText(textTransform);
    }

    @Test
    void testOperationMoreEqual() {
        assertEquals(" większe  równe ", symboltotext.operation(">="));
    }

    @Test
    void testOperationMinus() {
        assertEquals(" minus ", symboltotext.operation("-"));
    }

    @Test
    void testOperationNoChange(){
        assertEquals("&^ brak zmian", symboltotext.operation("&^ brak zmian"));
    }
}
