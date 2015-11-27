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
public class UserAccessEntity {
    private Integer id;
    private Integer room;
    private Integer profile;
    private Date datetimeOfAccessStart;    
    private Date datetimeOfAccessEnd;

    public UserAccessEntity() {
        this.datetimeOfAccessStart = new Date();
    }

    public UserAccessEntity(Integer id, Integer room, Integer profile, Date datetimeOfAccessStart, Date datetimeOfAccessEnd) {
        this.id = id;
        this.room = room;
        this.profile = profile;
        this.datetimeOfAccessStart = datetimeOfAccessStart;
        this.datetimeOfAccessEnd = datetimeOfAccessEnd;
    }

    public void fromMap(Map map){
        this.id = (Integer) map.get("id");
        this.room = (Integer) map.get("room");
        this.profile = (Integer) map.get("profile");
        this.datetimeOfAccessStart = (Date) map.get("datetimeOfAccessStart");
        this.datetimeOfAccessEnd = (Date) map.get("datetimeOfAccessEnd");
    }
    
    public Map toMap(){
        Map map = new HashMap<>();
        
        map.put("id", this.id);
        map.put("room", this.room);
        map.put("profile", this.profile);
        map.put("datetimeOfAccessStart", this.datetimeOfAccessStart);
        map.put("datetimeOfAccessEnd", this.datetimeOfAccessEnd);
        
        return map;
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

    public Date getDatetimeOfAccessStart() {
        return datetimeOfAccessStart;
    }

    public void setDatetimeOfAccessStart(Date datetimeOfAccessStart) {
        this.datetimeOfAccessStart = datetimeOfAccessStart;
    }

    public Date getDatetimeOfAccessEnd() {
        return datetimeOfAccessEnd;
    }

    public void setDatetimeOfAccessEnd(Date datetimeOfAccessEnd) {
        this.datetimeOfAccessEnd = datetimeOfAccessEnd;
    }

    
    
    
    
}
