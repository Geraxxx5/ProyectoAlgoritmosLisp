package controller;

import java.util.ArrayList;
import java.util.List;
import modelo.FunctionModel;
import modelo.LispModel;
import modelo.VariableModel;

public class EvaluateExpression {
    /**
    *LispModel
    */
    LispModel lispModel = new LispModel();
    
    /**
     *
     * @param expr
     * @param variable
     * @param functions
     * @return Object
     * 
     * 
     * take the fisrt element of the expression to know if its a list, conditional, function, command
     * Create the expression list depends if first element is a list or string
     * Take the operator of the expression list
     * 
     * Commands:
     * 
     * Quote:
     * return in front of quote
     * example:
     * [quote, [+ [- 2 3] 5]]
     * return [+ [- 2 3] 5]]
     * 
     * eval:
        evaluate the expression in fron of eval
        example: [Eval, [Quote, [+ 5 2]]]               
        Evaluate what is in front of
        [Quote, [+ 5 2]]
        return of quote is [+ 5 2]
        evaluate what is return in fron of eval
        [Eval [+ 5 2]]
        return 7
     *
     * Setq:
        Difine the vars
        Example: [setq x 10]
        the name of the variable is 'x' and the value 10
        Using variableModel you can keep this data in a hasmap
        key: name of variable, Value: List of possible values 
        key: 'x', Value: List<Object> = [10]
    
     * atom:
         If the expression in front of atom is greater than 1
         it mean that you can divide in more list the expression
         * 
     *
     * Cond
     * Cond expression: [Cond, [[< 5 2] x], [t, 5]]
       statement: [< 5 2]
       return true statement: x
       return false statement: [t, 5]
       cond can take 5 different ways of take the command
            1: [Cond, [< 5 2]]
            2: [Cond, [[< 5 2] x]]
            3: [Cond, [[< 5 2] x], [t, 5]]
            4: [Cond, [[< 5 2] x], [t, 5]]
            5: [Cond, [< 5 2], [t, 5]]
                        
        Cond create 3 variable
        returnIfTrue: is the response if statement is true
        returnIfFalse: is the reponse if statement is false
        resultCondition: is the result of statement
     *
     * List:
            List create a list depends of what you give
            example: [List, [' a], [' b], [' [a,b]]]
            List return a list of object
            So if in front of list its a list: 'List, [' a]'
            Evaluate all the expression in front of list and add to a list
    
     *
     * Defun:
     *      Defun create a functions
            Example: [defun, [converToCelsius], [x], [* [- x 32] [/ 5 9]]]]
            The name of the variable would be the key in variableModel HashMap
            the value is a List<Object> where:
                in position 0: would be the params
                in position 1: would be the condition
     * 
     * Operators:
     *      if operator is +,-,/,sqrt,^
            Evaluate in base of the symbol
            Example [+,x,5]
            First take the first value of the list
            if is a String it means that it is a variable, so it would switch the variable with the value
            if its a integer or double, only takes the value
     *
     * Conditional:
        if operator is a conditional
        Take the first and the second value and if value is a String than switch the String with the value
        Return "T" if true
        Return "Nil" if false
     * 
     *
     * Functions:
            if operador is a function
            first create a new VariableModel so the function would take it's own variables
            This was made if there is a recursive function so other calls won't be mess with variables
            it would call the params and the condition of the variable
            Then using a for, the values would be asign to their params
            Return evaluate the function
     * 
     */
    public Object evaluate(List<Object> expr, VariableModel variable, FunctionModel functions){

            //take the fisrt element of the expression to know if its a list, conditional, function, command
            Object firstElement = expr.get(0);
            
            if (firstElement instanceof List || ((firstElement instanceof String)&& (expr instanceof List))) {
                //Create the expression list depends if first element is a list or string
                List<Object> expressionList;
                if(firstElement instanceof String){
                    expressionList = (List<Object>) expr;
                }else{
                    expressionList = (List<Object>) firstElement;
                }

                //Take the operator of the expression list
                String oper = (String) expressionList.get(0);

                //if operator is a command
                if(lispModel.getCommand().contains(oper)){
                    if(oper.equals("quote") || oper.equals("\'")){
                        //return in front of quote
                        //example:
                        //[quote, [+ [- 2 3] 5]]
                        //return [+ [- 2 3] 5]]
                        return expressionList.get(1);
                    }
                    else if(oper.equals("eval")){
                        //evaluate the expression in fron of eval
                        //example: [Eval, [Quote, [+ 5 2]]]
                        //Evaluate what is in front of
                        //[Quote, [+ 5 2]]
                        //return of quote is [+ 5 2]
                        //evaluate what is return in fron of eval
                        //[Eval [+ 5 2]]
                        //return 7
                        Object second = expressionList.get(1);
                        //if second its not a list then return second
                        //if its a list evaluate the list and the return
                        if(second instanceof List){
                            
                            List<Object> secondValue = (List<Object>) second;
                            Object response = evaluate(secondValue, variable, functions);
                            
                            //if response is a List it means that is something to evaluate
                            //if is a String only return the String
                            if(response instanceof List){
                                
                                List<Object> toEvaluate = (List<Object>) response;
                                return evaluate(toEvaluate,variable, functions);
                                
                            }else{
                                return response;
                            }
                        }else{
                            return second;
                        }
                    }else if(oper.equals("setq") || oper.equals("defvar")){
                        //Difine the vars
                        //Example: [setq x 10]
                        //the name of the variable is 'x' and the value 10
                        //Using variableModel you can keep this data in a hasmap
                        //key: name of variable, Value: List of possible values 
                        //key: 'x', Value: List<Object> = [10]
                        if(expressionList.get(1) instanceof String){
                            
                            String variableName = (String) expressionList.get(1);
                            
                            if(expressionList.get(2) instanceof Integer){
                                
                                List<Object> varibaleValue = new ArrayList<>();
                                varibaleValue.add(String.valueOf(expressionList.get(2)));
                                variable.createNewVariable(variableName, varibaleValue);
                                
                            }else{
                                throw new RuntimeException("Error in setq: Value incorrect");
                            }
                        }else{
                            throw new RuntimeException("Error in setq: variable name incorrect");
                        }
                    }else if(oper.equals("atom")){
                        //If the expression in front of atom is greater than 1
                        //it mean that you can divide in more list the expression
                        String atom = String.valueOf(expressionList.get(1));
                        if(atom.length()>1){
                            return "Nil";
                        }else{
                            return "T";
                        }
                    }else if(oper.equals("cond")){
                        //Cond expression: [Cond, [[< 5 2] x], [t, 5]]
                        //statement: [< 5 2]
                        //return true statement: x
                        //return false statement: [t, 5]
                        
                        //cond can take 5 different ways of take the command
                        // 1: [Cond, [< 5 2]]
                        // 2: [Cond, [[< 5 2] x]]
                        // 3: [Cond, [[< 5 2] x], [t, 5]]
                        // 4: [Cond, [[< 5 2] x], [t, 5]]
                        // 5: [Cond, [< 5 2], [t, 5]]
                        
                        //Cond create 3 variable
                        //returnIfTrue: is the response if statement is true
                        //returnIfFalse: is the reponse if statement is false
                        //resultCondition: is the result of statement
                        if(expressionList.size() == 2){
                            
                            String returnIfTrue = "";
                            List<Object> condition = (List<Object>) expressionList.get(1);
                            String resultCondition;
                            if(condition.size() == 2){
                                List<Object> evaluateCondition = (List<Object>) condition.get(0);
                                resultCondition = String.valueOf(evaluate(evaluateCondition,variable, functions));
                                Object returnIfTrueCondition = condition.get(1);
                                if(returnIfTrueCondition instanceof String){
                                    returnIfTrue = String.valueOf(symbolEvaluate(returnIfTrueCondition,variable));
                                }else{
                                    List<Object> express = (List<Object>) returnIfTrueCondition;
                                    returnIfTrue = String.valueOf(evaluate(express,variable, functions));
                                }
                                
                            }else{
                                resultCondition = String.valueOf(evaluate(condition,variable, functions));
                            }
                            
                            if(resultCondition == "T"){
                                if(returnIfTrue.equals("")){
                                    returnIfTrue = "T";
                                }
                                return returnIfTrue;
                            }else{
                                return "Nil";
                            }
                        }else if(expressionList.size() == 3){
                            String returnIfTrue = "";
                            String resultCondition = "";
                            List<Object> condition = (List<Object>) expressionList.get(1);

                            if(condition.size() == 2){
                                
                                List<Object> evaluateCondition = (List<Object>) condition.get(0);
                                resultCondition = String.valueOf(evaluate(evaluateCondition,variable, functions));
                                Object returnIfTrueCondition = condition.get(1);

                                if(returnIfTrueCondition instanceof String){
                                    
                                    returnIfTrue = String.valueOf(symbolEvaluate(returnIfTrueCondition,variable));
                                }else{
                                    
                                    List<Object> express = (List<Object>) returnIfTrueCondition;
                                    returnIfTrue = String.valueOf(evaluate(express,variable, functions));
                                    
                                }
                            }else{
                                resultCondition = String.valueOf(evaluate(condition,variable, functions));
                            }
                            List<Object> elseCondition = (List<Object>) expressionList.get(2);
                            Object conditionFalse = elseCondition.get(1);
                            String returnIfFalse = "";
                            
                            if(resultCondition.equals("T")){
                                return returnIfTrue;
                            }else{
                                if(conditionFalse instanceof String){
                                returnIfFalse = String.valueOf(symbolEvaluate(conditionFalse,variable));
                                }else{
                                    List<Object> toEvaluateForFalse = (List<Object>) conditionFalse;
                                    returnIfFalse = String.valueOf(evaluate(toEvaluateForFalse,variable, functions));
                                }
                                return returnIfFalse;
                            }
                        }
                    }else if(oper.equals("list")){
                        //List create a list depends of what you give
                        //example: [List, [' a], [' b], [' [a,b]]]
                        //List return a list of object
                        //So if in front of list its a list: 'List, [' a]'
                        //Evaluate all the expression in front of list and add to a list
                       
                        List<Object> list = new ArrayList<>();

                        for(int index = 1;index<expressionList.size();){
                            Object toDoList = expressionList.get(index);
                            Object forListadd;
                            if(toDoList instanceof List){
                                List<Object> forEvaluate = (List<Object>) toDoList;
                                forListadd = evaluate(forEvaluate,variable, functions);
                            }else{
                                forListadd = toDoList;
                            }
                            list.add(forListadd);
                            index++;
                        }
                        return list;
                    }else if(oper.equals("defun")){
                        //Defun create a functions
                        //Example: [defun, [converToCelsius], [x], [* [- x 32] [/ 5 9]]]]
                        //The name of the variable would be the key in variableModel HashMap
                        //the value is a List<Object> where:
                            //in position 0: would be the params
                            //in position 1: would be the condition
                        
                        String nameOfFunction = String.valueOf(expressionList.get(1));
                        if(!functions.funcionExist(nameOfFunction)){
                            List<Object> attributes = new ArrayList<>();
                            attributes.add(expressionList.get(2));
                            attributes.add(expressionList.get(3));
                            functions.createNewFunction(nameOfFunction, attributes);
                        }else{
                            throw new RuntimeException("Function alredy exist");
                        }
                    }
                }else if(lispModel.getOperators().contains(oper)){
                    //if operator is +,-,/,sqrt,^
                    //Evaluate in base of the symbol
                    //Example [+,x,5]
                    //First take the first value of the list
                    //if is a String it means that it is a variable, so it would switch the variable with the value
                    //if its a integer or double, only takes the value
                    
                    double res1 = 0.0;
                    double res2 = 0.0;
                    Object value1 = expressionList.get(1);
                    Object value2 = null;
                    if(!oper.equals("sqrt")){
                        value2 = expressionList.get(2);
                    } 
                    if(value1 instanceof List){
                        List<Object> v = (List<Object>) value1;
                        res1 = Double.parseDouble(String.valueOf(evaluate(v,variable, functions)));
                    }else{
                        if(value1 instanceof String){
                            res1 = Double.parseDouble(String.valueOf(symbolEvaluate(value1,variable)));
                        }else{
                            res1 = Double.parseDouble(String.valueOf(value1));
                        }
                        
                    }
                    if(!oper.equals("sqrt")){
                        if(value2 instanceof List){
                        List<Object> v = (List<Object>) value2;
                        //res2 = (double) evaluate(v);
                        res2 = Double.parseDouble(String.valueOf(evaluate(v,variable, functions)));
                        }else{
                            //res2 = (double) value2;
                            if(value2 instanceof String){
                                res2 = Double.parseDouble(String.valueOf(symbolEvaluate(value2,variable)));

                            }else{
                                res2 = Double.parseDouble(String.valueOf(value2));
                            }
                        }
                    }
                    
                    switch(oper){
                        case"+":
                            return res1+res2;
                        case"-":
                            return res1-res2;
                        case"/":
                            if(res2 == 0.0){
                                throw new RuntimeException("Trying to divide by 0");
                            }else{
                                return res1/res2;
                            }
                        case"*":
                            return res1*res2;
                        case"^":
                            return Math.pow(res1, res2);
                        case"sqrt":
                            return Math.sqrt(res1);
                        default:
                            throw new RuntimeException("incompatible character");    
                    }
    
                }else if(lispModel.getConditionals().contains(oper)){
                    //if operator is a conditional
                    //Take the first and the second value and if value is a String than switch the String with the value
                    //Return "T" if true
                    //Return "Nil" if false
                    
                    double value1 = Double.parseDouble(String.valueOf(symbolEvaluate(expressionList.get(1),variable))); 
                    double value2 = Double.parseDouble(String.valueOf(symbolEvaluate(expressionList.get(2),variable)));
                    switch(oper){
                        case"=":
                            if(value1 == value2){
                                return "T";
                            }else{
                                return "Nil";
                            }
                        case"<":
                            if(value1 < value2){
                                return "T";
                            }else{
                                return "Nil";
                            }
                        case">":
                            if(value1 > value2){
                                return "T";
                            }else{
                                return "Nil";
                            }
                        case"equal":
                            if(value1 == value2){
                                return "T";
                            }else{
                                return "Nil";
                            }
                        default:
                            throw new RuntimeException("Eror: unexpected"); 
                   }

                }else if(functions.funcionExist(oper)){
                    //if operador is a function
                    //first create a new VariableModel so the function would take it's own variables
                    //This was made if there is a recursive function so other calls won't be mess with variables
                    //it would call the params and the condition of the variable
                    //Then using a for, the values would be asign to their params
                    //Return evaluate the function
                    
                    VariableModel varsFunction = new VariableModel();
                    List<Object> params = functions.getParams(oper);
                    List<Object> condition = functions.getCondition(oper);

                    for(int index = 0;index<params.size();index++){
                        String var = (String) params.get(index);
                        List<Object> value = new ArrayList<>();
                        Object expressionToEvaluate = expressionList.get(1+index);
                        String toSave = "";
                        if(expressionToEvaluate instanceof List){
                            List<Object> toEvaluate = (List<Object>) expressionToEvaluate;
                            toSave = String.valueOf(evaluate(toEvaluate,variable, functions));
                        }else{
                            toSave = String.valueOf(expressionToEvaluate);
                        }
                        value.add(toSave);
                        varsFunction.createNewVariable(var,value);
                        
                    }
                    return evaluate(condition,varsFunction, functions);
                }
            }else{
                return symbolEvaluate(firstElement, variable);
            }
            //If expression in greater than 1, it means that its more so would be evaluate
            if(expr.size() > 1){
                List<Object> subList = expr.subList(1, expr.size());
                return evaluate(subList,variable, functions);
            }else{
                return symbolEvaluate(firstElement, variable);
            }
            //return expr;
        }
    
    /**
     *
     * @param symbol
     * @param variable
     * @return Object
     * 
     * Recive an object and variableModelo to determinate its real value
     * 
     */
    public Object symbolEvaluate(Object symbol,VariableModel variable){
            if(symbol instanceof String){
                String var = (String) symbol;
                if(variable.varibaleExist(var)){
                    return Double.parseDouble(String.valueOf(variable.lastValue(var))); 
                }else{
                    throw new RuntimeException("Variable dosn't exist: "+var); 
                }
            }else if(symbol instanceof Integer){
                return (int) symbol;
            }else if(symbol instanceof Double){
                return (double) symbol;
            }
            return symbol;
        }
}
