package ar.edu.ubp.das.rest;

import ar.edu.ubp.das.entities.RoomComplexEntity;
import ar.edu.ubp.das.entities.RoomEntity;
import ar.edu.ubp.das.mvc.actions.DynaActionForm;
import ar.edu.ubp.das.mvc.daos.Dao;
import ar.edu.ubp.das.mvc.daos.DaoFactory;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author Febo
 */
@Path("rooms")
public class RoomResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of RoomResource
     */
    public RoomResource() {
    }

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response create(RoomEntity entity) {
        try {
            Dao dao = DaoFactory.getDao("Room");
            DynaActionForm form = new DynaActionForm();
            form.setItems(entity.toMap());
            
            dao.insert(form);
            entity.setId((Integer) form.getItem("id"));
            
            return Response.ok(entity).build();
        } catch (Exception ex) {
            Logger.getLogger(RoomResource.class.getName()).log(Level.SEVERE, null, ex);
            return Response.serverError().build();
        }        
    }
    
    @GET
    @Produces("application/json")
    public List<RoomEntity> findAll() {
        try {
            Dao dao = DaoFactory.getDao("Room");
            List<RoomEntity> entities = new LinkedList<>();
            List<DynaActionForm> resultSet;
            
            DynaActionForm form = new DynaActionForm();
            form.setItem("selector", "findAll");
            
            resultSet = dao.select(form);
            
            for(DynaActionForm temp : resultSet){
                RoomEntity room = new RoomEntity();
                room.fromMap(temp.getItems());
                entities.add(room);
            }
            
            return entities;
        } catch (Exception ex) {
            Logger.getLogger(RoomResource.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    @POST
    @Path("id")
    @Produces("application/json")
    public Response findById(@FormParam("id") Integer id) {
        try {
            Dao dao = DaoFactory.getDao("Room");
            List<DynaActionForm> resultSet;
            
            DynaActionForm form = new DynaActionForm();
            form.setItem("selector", "byId");
            form.setItem("id", id);
            
            resultSet = dao.select(form);
            if(resultSet.size() == 1){
                RoomEntity entity = new RoomEntity();
                entity.fromMap(resultSet.get(0).getItems());
                return Response.ok(entity).build();
            }else {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
        } catch (Exception ex) {
            Logger.getLogger(RoomResource.class.getName()).log(Level.SEVERE, null, ex);
            return Response.serverError().build();
        }
    }
    
    /*not used*/
    @POST
    @Path("owner/id")
    @Produces("application/json")
    public List<RoomEntity> findByOwner(@FormParam("id") Integer id) {
        try {
            Dao dao = DaoFactory.getDao("Room");
            List<RoomEntity> entities = new LinkedList<>();
            List<DynaActionForm> resultSet;
            
            DynaActionForm form = new DynaActionForm();
            form.setItem("selector", "byOwner");
            form.setItem("owner", id);
            
            resultSet = dao.select(form);
            
            for(DynaActionForm temp : resultSet){
                if(temp.getItem("type").equals("private")){
                    RoomEntity entity = new RoomEntity();
                    entity.fromMap(temp.getItems());
                    entities.add(entity);
                }
            }
            
            return entities;
        } catch (Exception ex) {
            Logger.getLogger(RoomResource.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    @POST
    @Path("public/participant/profile")
    @Produces("application/json")
    public List<RoomEntity> findByProfileInPublic(@FormParam("id") Integer id) {
        try {
            Dao dao = DaoFactory.getDao("Room");
            List<RoomEntity> entities = new LinkedList<>();
            List<DynaActionForm> resultSet;
            
            DynaActionForm form = new DynaActionForm();
            form.setItem("selector", "byProfile");
            form.setItem("profile", id);
            
            resultSet = dao.select(form);
            
            for(DynaActionForm temp : resultSet){
                RoomEntity entity = new RoomEntity();
                entity.fromMap(temp.getItems());
                entities.add(entity);
            }
            
            return entities;
        } catch (Exception ex) {
            Logger.getLogger(RoomResource.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    @GET
    @Path("type/public")
    @Produces("application/json")
    public List<RoomComplexEntity> findPublicsWithUserCant() {
        try {
            Dao dao = DaoFactory.getDao("RoomComplex");
            List<RoomComplexEntity> entities = new LinkedList<>();
            List<DynaActionForm> resultSet;
            
            DynaActionForm form = new DynaActionForm();
            form.setItem("selector", "byAllRoom");
            
            resultSet = dao.select(form);
            
            for(DynaActionForm temp : resultSet){
                RoomComplexEntity entity = new RoomComplexEntity();
                entity.fromMap(temp.getItems());
                entities.add(entity);
            }
            
            return entities;
        } catch (Exception ex) {
            Logger.getLogger(RoomResource.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        
    }
    
    @POST
    @Path("delete/room")
    @Produces("application/json")
    public Response deleteRoom(@FormParam("id") Integer id){
        try {
            Dao dao = DaoFactory.getDao("Room");
            DynaActionForm form = new DynaActionForm();
            
            form.setItem("id", id);
            dao.delete(form);
            
            return Response.ok().build();
        } catch (Exception ex) {
            Logger.getLogger(MessageResource.class.getName()).log(Level.SEVERE, null, ex);
            return Response.serverError().build();
        }
    }

}
