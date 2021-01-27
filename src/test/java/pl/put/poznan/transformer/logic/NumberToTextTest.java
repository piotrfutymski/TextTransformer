package pl.put.poznan.transformer.logic;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class NumberToTextTest {
    private TextTransform textTransform;
    private NumberToText numberToText;

    @BeforeEach
    void setUp(){
        textTransform = mock(TextTransform.class);
        when(textTransform.transform(anyString())).thenAnswer(
                (Answer) invocation -> (String) invocation.getArguments()[0]);
        numberToText = new NumberToText(textTransform);
    }

    @Test
    void testOperationSimpleOne(){
        assertEquals("jeden", numberToText.operation("1"));
    }

    @Test
    void testOperationTens(){
        assertEquals("jedenaście", numberToText.operation("11"));
    }

    @Test
    void testOperationZero(){
        assertEquals("zero", numberToText.transform("0"));
    }

    @Test
    void testOperationBigNumber(){
        assertEquals("siedemset tysięcy trzysta trzydzieści", numberToText.transform("700330"));
    }
    @Test
    void testOperationTwoNumbers(){
        assertEquals("siedemset trzysta trzydzieści", numberToText.transform("700 330"));
    }
}