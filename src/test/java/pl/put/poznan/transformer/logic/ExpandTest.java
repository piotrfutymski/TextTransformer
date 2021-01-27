package pl.put.poznan.transformer.logic;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

public class ExpandTest {
    private TextTransform textTransform;
    private Expand expand;

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
        expand = new Expand(textTransform);
    }

    @Test
    void testOperationAllLower(){
        assertEquals("na przykład", expand.operation("np."));
    }

    @Test
    void testOperationFirstLetterCapital(){
        assertEquals("I tym podobne", expand.operation("Itp."));
    }

    @Test
    void testOperationAllUpper(){
        assertEquals("I TAK DALEJ", expand.transform("ITD."));
    }

    @Test
    void testOperationNoChange(){
        assertEquals("Tu nie ma nic do zmiany i t d.", expand.transform("Tu nie ma nic do zmiany i t d."));
    }

}