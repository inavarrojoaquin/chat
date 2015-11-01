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
public class MessageEntity {
    private Integer id;
    private Integer room;
    private Integer owner;
    private Date datetimeOfCreation;
    private String body;
    private String state;

    public MessageEntity() {
        this.datetimeOfCreation = new Date();
    }

    public MessageEntity(Integer id, Integer room, Integer owner, Date datetimeOfCreation, String body, String state) {
        this.id = id;
        this.room = room;
        this.owner = owner;
        this.datetimeOfCreation = datetimeOfCreation;
        this.body = body;
        this.state = state;
    }
    
    public Map toMap(){
        Map map = new HashMap();
        
        map.put("id", this.id);
        map.put("room", this.room);
        map.put("owner", this.owner);
        map.put("datetimeOfCreation", this.datetimeOfCreation);
        map.put("body", this.body);
        map.put("state", this.state);
        
        return map;
    }

    public void fromMap(Map map){
        this.id = (Integer) map.get("id");
        this.room = (Integer) map.get("room");
        this.owner = (Integer) map.get("owner");
        this.datetimeOfCreation = (Date) map.get("datetimeOfCreation");
        this.body = (String) map.get("body");
        this.state = (String) map.get("state");
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
}
