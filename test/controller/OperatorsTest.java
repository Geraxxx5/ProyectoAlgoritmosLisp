/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.Arrays;
import java.util.List;
import modelo.FunctionModel;
import modelo.VariableModel;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Gerax
 */
public class OperatorsTest {
    
    /**
     * Test of evaluate method, of class EvaluateExpression for Addition operator.
     */
    @Test
    public void testAdditionOperator() {
        System.out.println("Addition Operator");
        List<Object> expr = Arrays.asList("+",5,2);
        VariableModel variable = null;
        FunctionModel functions = null;
        EvaluateExpression instance = new EvaluateExpression();
        Object expResult = 7.0;
        Object result = instance.evaluate(expr, variable, functions);
        assertEquals(expResult, result);
    }
    
    /**
    * Test of evaluate method, of class EvaluateExpression for Subtraction operator.
    */
    @Test
    public void testSubtractionOperator(){
        System.out.println("Subtraction Operator");
        List<Object> expr = Arrays.asList("-",5,2);
        VariableModel variable = null;
        FunctionModel functions = null;
        EvaluateExpression instance = new EvaluateExpression();
        Object expResult = 3.0;
        Object result = instance.evaluate(expr, variable, functions);
        assertEquals(expResult, result);
    }
    
    /**
    * Test of evaluate method, of class EvaluateExpression for Multiplication operator.
    */
    @Test
    public void testMultiplicationOperator(){
        System.out.println("Multiplication Operator");
        List<Object> expr = Arrays.asList("*",5,2);
        VariableModel variable = null;
        FunctionModel functions = null;
        EvaluateExpression instance = new EvaluateExpression();
        Object expResult = 10.0;
        Object result = instance.evaluate(expr, variable, functions);
        assertEquals(expResult, result);
    }
    
    /**
    * Test of evaluate method, of class EvaluateExpression for Division operator.
    */
    @Test
    public void testDivisionOperator(){
        System.out.println("Division Operator");
        List<Object> expr = Arrays.asList("/",4,2);
        VariableModel variable = null;
        FunctionModel functions = null;
        EvaluateExpression instance = new EvaluateExpression();
        Object expResult = 2.0;
        Object result = instance.evaluate(expr, variable, functions);
        assertEquals(expResult, result);
    }
    
    /**
    * Test of evaluate method, of class EvaluateExpression for Power operator.
    */
    @Test
    public void testPowerOperator(){
        System.out.println("Power Operator");
        List<Object> expr = Arrays.asList("^",2,2);
        VariableModel variable = null;
        FunctionModel functions = null;
        EvaluateExpression instance = new EvaluateExpression();
        Object expResult = 4.0;
        Object result = instance.evaluate(expr, variable, functions);
        assertEquals(expResult, result);
    }
    
    /**
    * Test of evaluate method, of class EvaluateExpression for Square Root operator.
    */
    @Test
    public void testSquareRootOperator(){
        System.out.println("Square Root Operator");
        List<Object> expr = Arrays.asList("sqrt",4);
        VariableModel variable = null;
        FunctionModel functions = null;
        EvaluateExpression instance = new EvaluateExpression();
        Object expResult = 2.0;
        Object result = instance.evaluate(expr, variable, functions);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of symbolEvaluate method, of class EvaluateExpression.
     * 
     */
    @Test
    public void testSymbolEvaluate() {
        System.out.println("symbolEvaluate");
        Object symbol = "x";
        VariableModel variable = new VariableModel();
        variable.createNewVariable("x", Arrays.asList(20.0));
        EvaluateExpression instance = new EvaluateExpression();
        Object expResult = 20.0;
        Object result = instance.symbolEvaluate(symbol, variable);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }
    
    
}
