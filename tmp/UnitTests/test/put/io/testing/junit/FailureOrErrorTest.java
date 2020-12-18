package put.io.testing.junit;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FailureOrErrorTest {
    @Test
    void test1(){
        assertEquals(3, -3);
    }

    @Test
    void test2() throws Exception {
        throw new Exception("Wyjatek");
    }

    @Test
    void test3() throws Throwable {
        try{
            assertEquals(true, false);
        }
        catch(Throwable e){
            e.printStackTrace(); //AssertionFailedError
        }
    }
}