package ar.edu.ubp.das.entities;

import java.util.HashMap;
import java.util.Map;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Febo
 */
@XmlRootElement
public class InvitationEntity {
    private Integer id;
    private Integer room;
    private Integer sender;
    private Integer receiver;
    private String state;

    public InvitationEntity() {
    }

    public InvitationEntity(Integer id, Integer room, Integer sender, Integer receiver, String state) {
        this.id = id;
        this.room = room;
        this.sender = sender;
        this.receiver = receiver;
        this.state = state;
    }
    
    public Map toMap(){
        Map map = new HashMap();
        
        map.put("id", this.id);
        map.put("room", this.room);
        map.put("sender", this.sender);
        map.put("receiver", this.receiver);
        map.put("state", this.state);
        
        return map;
    }

    public void fromMap(Map map){
        this.id = (Integer) map.get("id");
        this.room = (Integer) map.get("room");
        this.sender = (Integer) map.get("sender");
        this.receiver = (Integer) map.get("receiver");
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

    public Integer getSender() {
        return sender;
    }

    public void setSender(Integer sender) {
        this.sender = sender;
    }

    public Integer getReceiver() {
        return receiver;
    }

    public void setReceiver(Integer receiver) {
        this.receiver = receiver;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
    
    
}
