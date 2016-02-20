package ar.edu.ubp.das.entities;

import java.util.HashMap;
import java.util.Map;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Febo
 */
@XmlRootElement
public class RoomAccessPolicyComplexEntity {
    private Integer id;
    private Integer room;
    private Integer profile;
    private String policy;
    private String userName;
    private String roomName;

    public RoomAccessPolicyComplexEntity() {
    }

    public RoomAccessPolicyComplexEntity(Integer id, Integer room, Integer profile, String policy, String userName, String roomName) {
        this.id = id;
        this.room = room;
        this.profile = profile;
        this.policy = policy;
        this.userName = userName;
        this.roomName = roomName;
    }
    
    public Map toMap(){
        Map map = new HashMap();
        
        map.put("id", this.id);
        map.put("room", this.room);
        map.put("profile", this.profile);
        map.put("policy", this.policy);
        map.put("userName", this.userName);
        map.put("roomName", this.roomName);
        
        return map;
    }
    
    public void fromMap(Map map){
        this.id = (Integer) map.get("id");
        this.room = (Integer) map.get("room");
        this.profile = (Integer) map.get("profile");
        this.policy = (String) map.get("policy");
        this.userName = (String) map.get("userName");
        this.roomName = (String) map.get("roomName");
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }
}
