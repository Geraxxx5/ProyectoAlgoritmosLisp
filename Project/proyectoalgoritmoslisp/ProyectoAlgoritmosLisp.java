package proyectoalgoritmoslisp;

import controller.EvaluateExpression;
import java.util.List;
import modelo.FunctionModel;
import modelo.VariableModel;
import parser.ParserExpression;

public class ProyectoAlgoritmosLisp {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        FunctionModel functionModel = new FunctionModel();
        VariableModel variableModel = new VariableModel();
        EvaluateExpression evaluate = new EvaluateExpression();
        List<Object> expression = ParserExpression.readFileExpression("datos.txt", true);
        System.out.println("---------------------------------");
        System.out.println("obtained expression: "+expression);
        System.out.println("---------------------------------");
        System.out.println("---------------------------------");
        try {
            System.out.println("result of the expression: "+evaluate.evaluate(expression, variableModel, functionModel));   
        } catch (Exception e) {
            System.err.println("Error: "+e);
        }
        System.out.println("---------------------------------");
    }
    
}
