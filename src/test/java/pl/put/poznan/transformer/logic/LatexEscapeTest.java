package pl.put.poznan.transformer.logic;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

class LatexEscapeTest {
    private LatexEscape t;

    @BeforeEach
    void setUp() {
        TextTransform identityMock = mock(TextTransform.class);
        when(identityMock.transform(anyString())).thenAnswer(
                new Answer() {
                    public Object answer(InvocationOnMock invocation) {
                        String text = (String)invocation.getArguments()[0];
                        return text;
                    }
                });
        t = new LatexEscape((identityMock));
    }

    @Test
    void testOperationWhenEmptyString() {
        assertEquals("", t.operation(""));
    }
    @Test
    void testOperationWhenDollar() {
        assertEquals("\\$", t.operation("$"));
    }
    @Test
    void testOperationWhenAnd() {
        assertEquals("\\&", t.operation("&"));
    }
    @Test
    void testOperationWhenNull() {
        assertThrows(NullPointerException.class, () -> { t.operation(null); });
    }
}