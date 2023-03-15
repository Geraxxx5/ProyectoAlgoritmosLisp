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
        System.out.println(evaluate.evaluate(expression, variableModel, functionModel));
    }
    
}
