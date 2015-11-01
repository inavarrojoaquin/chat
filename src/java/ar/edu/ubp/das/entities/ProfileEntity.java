/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.ubp.das.entities;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Map;
import java.util.HashMap;

@XmlRootElement
public class ProfileEntity {
    
    private Integer id;
    private String login;
    private String password;
    private String type;
    
    public ProfileEntity(){}
    
    public ProfileEntity(Integer id, String login, String password, String type){
        this.id = id;
        this.login = login;
        this.password = password;
        this.type = type;
    }
    
    public void fromMap(Map map){
        this.id = (Integer) map.get("id");
        this.login = (String) map.get("login");
        this.password = (String) map.get("password");
        this.type = (String) map.get("type");
    }
    
    public Map toMap(){
        Map map = new HashMap<>();
        
        map.put("id", this.id);
        map.put("login", this.login);
        map.put("password", this.password);
        map.put("type", this.type);
        
        return map;
    }
    
    public Integer getId(){
        return this.id;
    }
    
    public String getLogin(){
        return this.login;
    }
    
    public String getPassword(){
        return this.password;
    }
    
    public String getType(){
        return this.type;
    }
    
    public void setId(Integer id){
        this.id = id;
    }
    
    public void setLogin(String login){
        this.login = login;
    }
    
    public void setPassword(String password){
        this.password = password;
    }
    
    public void setType(String type){
        this.type = type;
    }
}
