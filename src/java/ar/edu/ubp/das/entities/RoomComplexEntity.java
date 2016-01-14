package ar.edu.ubp.das.entities;

import java.util.HashMap;
import java.util.Map;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Febo
 */
@XmlRootElement
public class RoomComplexEntity {
    private Integer id;
    private String name;
    private String type;
    private Integer owner;
    private Integer cant_user;

    public RoomComplexEntity() {
    }

    public RoomComplexEntity(Integer id, String name, String type, Integer owner, Integer cant_user) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.owner = owner;
        this.cant_user = cant_user;
    }

    public void fromMap(Map map){
        this.id = (Integer) map.get("id");
        this.name = (String) map.get("name");
        this.type = (String) map.get("type");
        this.owner = (Integer) map.get("owner");
        this.cant_user = (Integer) map.get("cant_user");
    }

    public Map toMap(){
        Map map = new HashMap();
        map.put("id", this.id);
        map.put("name", this.name);
        map.put("type", this.type);
        map.put("owner", this.owner);
        map.put("cant_user", this.cant_user);
        
        return map;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getOwner() {
        return owner;
    }

    public void setOwner(Integer owner) {
        this.owner = owner;
    }

    public Integer getCant_user() {
        return cant_user;
    }

    public void setCant_user(Integer cant_user) {
        this.cant_user = cant_user;
    }
    
}
