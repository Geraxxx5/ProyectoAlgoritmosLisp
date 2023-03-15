/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Gerax
 */
public class FunctionModel {
    HashMap<String, List<Object>> functions = new HashMap<>();
    
    /**
     *
     * @param name
     * @param Value
     */
    public void createNewFunction(String name, List<Object> Value){
        functions.put(name, Value);
    }
    
    /**
     *
     * @param key
     * @return boolean
     */
    public boolean funcionExist(String key){
        return functions.containsKey(key);
    }
    
    /**
     *
     * @return map Function
     */
    public HashMap<String, List<Object>> getFunctions(){
        return functions;
    }
    
    /**
     *
     * @param key
     * @return params
     * 
     * Get the params of the function
     */
    public List<Object> getParams(String key){
        List<Object> toReturn = (List<Object>) functions.get(key).get(0);
        return toReturn;
    }
    
    /**
     *
     * @param key
     * @return condition
     * 
     * Get te condition of the function
     */
    public List<Object> getCondition(String key){
        List<Object> toReturn = (List<Object>) functions.get(key).get(1);
        return toReturn;
    }
}
