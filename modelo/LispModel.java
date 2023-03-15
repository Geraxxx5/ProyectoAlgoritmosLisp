package modelo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LispModel {

    /**
     *
     */
    List<Object> list = new ArrayList<>();
    List<String> command = Arrays.asList("quote","atom","eval","setq","defvar","cond","\'","list","defun");
    List<String> operators = Arrays.asList("+","-","/","*","^","sqrt");
    List<String> conditionals = Arrays.asList("=","<",">","equal");
    
    /**
     *
     * @return
     */
    public List<String> getCommand(){
        return command;
    }
    
    /**
     *
     * @return
     */
    public List<String> getConditionals(){
        return conditionals;
    }
    
    /**
     *
     * @return
     */
    public List<String> getOperators(){
        return operators;
    }
    
    /**
     *
     * @param add
     */
    public void addNewList(List<String> add){
        list.add(add);
        System.out.println(list);
    }
}
