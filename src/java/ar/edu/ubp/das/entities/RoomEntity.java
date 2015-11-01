package ar.edu.ubp.das.entities;

import java.util.HashMap;
import java.util.Map;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Febo
 */
@XmlRootElement
public class RoomEntity {
    private Integer id;
    private String name;
    private String type;
    private Integer owner;

    public RoomEntity() {
    }

    public RoomEntity(Integer id, String name, String type, Integer owner) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.owner = owner;
    }

    public void fromMap(Map map){
        this.id = (Integer) map.get("id");
        this.name = (String) map.get("name");
        this.type = (String) map.get("type");
        this.owner = (Integer) map.get("owner");
    }

    public Map toMap(){
        Map map = new HashMap();
        map.put("id", this.id);
        map.put("name", this.name);
        map.put("type", this.type);
        map.put("owner", this.owner);
        
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
    
    
}
