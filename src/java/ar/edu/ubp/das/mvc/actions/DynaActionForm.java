/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.ubp.das.mvc.actions;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 *
 * @author Mariela Pastarini
 */
public class DynaActionForm {

    private String name;
    private Map    items;
    
    public DynaActionForm() {
        this.name  = this.getClass().getName();
        this.items = new HashMap<String, Object>();
    }
    
    public void setName(String name) {
        this.name = name;        
    }
    
    public void setItem(String name, Object value) {
        this.items.put(name, value);
    }

    public void setItems(Map items) {
        this.items = items;
    }
    
    public void removeItem(String name) {
        this.items.remove(name);
    }
    
    public int size() {
        return this.items.size();
    }
    
    public String getName() {
        return this.name;
    }
    
    public Object getItem(String name) {
        if(this.items.containsKey(name)) {
            return this.items.get(name);
        }
        return null;
    }
    
    public Map getItems() {
        return this.items;
    }
    
    @Override
    public String toString() {
        String key;
        StringBuilder form = new StringBuilder();        
        Iterator keys = this.items.keySet().iterator();
        while(keys.hasNext()) {
            key = keys.next().toString();
            form.append("Key: ");
            form.append(key);
            form.append(" - Value: ");
            form.append(this.items.get(key));
            form.append("\n");
        }
        return form.toString();
    }
    
}
