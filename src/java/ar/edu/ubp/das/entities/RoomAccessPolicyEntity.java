package ar.edu.ubp.das.entities;

import java.util.HashMap;
import java.util.Map;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Febo
 */
@XmlRootElement
public class RoomAccessPolicyEntity {
    private Integer id;
    private Integer room;
    private Integer profile;
    private String policy;

    public RoomAccessPolicyEntity() {
    }

    public RoomAccessPolicyEntity(Integer id, Integer room, Integer profile, String policy) {
        this.id = id;
        this.room = room;
        this.profile = profile;
        this.policy = policy;
    }
    
    public Map toMap(){
        Map map = new HashMap();
        
        map.put("id", this.id);
        map.put("room", this.room);
        map.put("profile", this.profile);
        map.put("policy", this.policy);
        
        return map;
    }
    
    public void fromMap(Map map){
        this.id = (Integer) map.get("id");
        this.room = (Integer) map.get("room");
        this.profile = (Integer) map.get("profile");
        this.policy = (String) map.get("policy");
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

    public Integer getProfile() {
        return profile;
    }

    public void setProfile(Integer profile) {
        this.profile = profile;
    }

    public String getPolicy() {
        return policy;
    }

    public void setPolicy(String policy) {
        this.policy = policy;
    }
    
    
}
