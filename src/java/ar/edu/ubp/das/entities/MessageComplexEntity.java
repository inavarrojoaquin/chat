package ar.edu.ubp.das.entities;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Febo
 */
@XmlRootElement
public class MessageComplexEntity {
    private Integer id;
    private Integer room;
    private Integer owner;
    private Date datetimeOfCreation;
    private String body;
    private String state;
    private String ownerName;

    public MessageComplexEntity() {
        this.datetimeOfCreation = new Date();
    }

    public MessageComplexEntity(Integer id, Integer room, Integer owner, Date datetimeOfCreation, String body, String state, String ownerName) {
        this.id = id;
        this.room = room;
        this.owner = owner;
        this.datetimeOfCreation = datetimeOfCreation;
        this.body = body;
        this.state = state;
        this.ownerName = ownerName;
    }

    public Map toMap(){
        Map map = new HashMap();
        
        map.put("id", this.id);
        map.put("room", this.room);
        map.put("owner", this.owner);
        map.put("datetimeOfCreation", this.datetimeOfCreation);
        map.put("body", this.body);
        map.put("state", this.state);
        map.put("ownerName", this.ownerName);
        
        return map;
    }

    public void fromMap(Map map){
        this.id = (Integer) map.get("id");
        this.room = (Integer) map.get("room");
        this.owner = (Integer) map.get("owner");
        this.datetimeOfCreation = (Date) map.get("datetimeOfCreation");
        this.body = (String) map.get("body");
        this.state = (String) map.get("state");
        this.ownerName = (String) map.get("ownerName");
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRoom() {
        return room;
    }

    public void setRoom(Integer room) {
        this.room = room;
    }

    public Integer getOwner() {
        return owner;
    }

    public void setOwner(Integer owner) {
        this.owner = owner;
    }

    public Date getDatetimeOfCreation() {
        return datetimeOfCreation;
    }

    public void setDatetimeOfCreation(Date datetimeOfCreation) {
        this.datetimeOfCreation = datetimeOfCreation;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }
    
}
