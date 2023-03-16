package parser;

import java.util.Arrays;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class ParserExpressionTest {
    
    /**
     * Test of readFileExpression method, of class ParserExpression.
     */
    @Test
    public void testReadFileExpression() {
        System.out.println("readFileExpression");
        String expression = "(eval (quote (+ 5 2)))";
        boolean isFile = false;
        List<Object> expResult = Arrays.asList(Arrays.asList("eval",Arrays.asList("quote",Arrays.asList("+",5,2))));
        List<Object> result = ParserExpression.readFileExpression(expression, isFile);
        assertEquals(expResult, result);
    }

    /**
     * Test of parse method, of class ParserExpression.
     */
    @Test
    public void testParse() {
        System.out.println("parse");
        String input = "(setq x 20) (setq y 5) (+ y x)";
        List<Object> expResult = Arrays.asList(Arrays.asList("setq", "x", 20),Arrays.asList("setq", "y", 5),
                                    Arrays.asList("+", "y", "x"));
        List<Object> result = ParserExpression.parse(input);
        assertEquals(expResult, result);
    }
    
}
