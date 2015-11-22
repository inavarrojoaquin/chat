/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.ubp.das.rest;

import ar.edu.ubp.das.entities.MessageEntity;
import ar.edu.ubp.das.mvc.actions.DynaActionForm;
import ar.edu.ubp.das.mvc.daos.Dao;
import ar.edu.ubp.das.mvc.daos.DaoFactory;
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
@Path("messages")
public class MessageResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of MessageResource
     */
    public MessageResource() {
    }

    @POST
    @Produces("application/json")
    @Consumes("application/json")
    public Response create(MessageEntity entity){
        try {
            Dao dao = DaoFactory.getDao("Message");
            DynaActionForm form = new DynaActionForm();
            
            form.setItems(entity.toMap());
            dao.insert(form);
            entity.setId((Integer) form.getItem("id"));
            
            return Response.ok(entity).build();
        } catch (Exception ex) {
            Logger.getLogger(MessageResource.class.getName()).log(Level.SEVERE, null, ex);
            return Response.serverError().build();
        }
    }
    
    @PUT
    @Produces("application/json")
    @Consumes("application/json")
    public Response updateMessage(MessageEntity entity){
        try {
            Dao dao = DaoFactory.getDao("Message");
            DynaActionForm form = new DynaActionForm();
            
            form.setItems(entity.toMap());
            dao.update(form);
            
            return Response.ok(entity).build();
        } catch (Exception ex) {
            Logger.getLogger(MessageResource.class.getName()).log(Level.SEVERE, null, ex);
            return Response.serverError().build();
        }
    }
    
    @DELETE
    @Path("delete/{id}")
    @Produces("application/json")    
    public Response deleteMessage(@PathParam("id") Integer id){
        try {
            Dao dao = DaoFactory.getDao("Message");
            DynaActionForm form = new DynaActionForm();
            
            form.setItem("id", id);
            dao.delete(form);
            
            return Response.ok().build();
        } catch (Exception ex) {
            Logger.getLogger(MessageResource.class.getName()).log(Level.SEVERE, null, ex);
            return Response.serverError().build();
        }
    }
    
    @GET
    @Produces("application/json")
    public List<MessageEntity> findAll() {
        try {
            Dao dao = DaoFactory.getDao("Message");
            DynaActionForm form = new DynaActionForm();
            List<MessageEntity> entities = new LinkedList<>();
            
            form.setItem("selector", "findAll");
            List<DynaActionForm> select = dao.select(form);
            
            for(DynaActionForm temp : select){
                MessageEntity m = new MessageEntity();
                m.fromMap(temp.getItems());
                entities.add(m);
            }
            
            return entities;
        } catch (Exception ex) {
            Logger.getLogger(MessageResource.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    /*No usado*/
    @POST
    @Path("find/id")
    @Produces("application/json")
    public Response findMessageById(@FormParam("id") Integer id) {
        try {
            Dao dao = DaoFactory.getDao("Message");
            DynaActionForm form = new DynaActionForm();
            
            form.setItem("selector", "byId");
            form.setItem("id", id);
            List<DynaActionForm> select = dao.select(form);
            
            if(select.size() == 1){
                MessageEntity entity = new MessageEntity();
                entity.fromMap(select.get(0).getItems());
                return Response.ok(entity).build();
            } else{
                return Response.status(Response.Status.NOT_FOUND).build();
            }
        } catch (Exception ex) {
            Logger.getLogger(MessageResource.class.getName()).log(Level.SEVERE, null, ex);
            return Response.serverError().build();
        }
    }
    
    @POST
    @Path("room/id")
    @Produces("application/json")
    public List<MessageEntity> findMessageByRoom(@FormParam("id") Integer id) {
        try {
            Dao dao = DaoFactory.getDao("Message");
            DynaActionForm form = new DynaActionForm();
            List<MessageEntity> entities = new LinkedList<>();
            
            form.setItem("selector", "byRoom");
            form.setItem("room",id);
            List<DynaActionForm> select = dao.select(form);
            
            for(DynaActionForm temp : select){
               MessageEntity m = new MessageEntity();
                m.fromMap(temp.getItems());
                entities.add(m);
            }
            
            return entities;
        } catch (Exception ex) {
            Logger.getLogger(MessageResource.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    @POST
    @Path("room/id/message/id/profileId/id")
    @Produces("application/json")
    public List<MessageEntity> findLastMessagesByRoom(@FormParam("room") Integer room, @FormParam("message") Integer message, @FormParam("profileId") Integer profileId) {
        try {
            Dao dao = DaoFactory.getDao("Message");
            DynaActionForm form = new DynaActionForm();
            List<MessageEntity> entities = new LinkedList<>();
            
            form.setItem("selector", "byLastMessage");
            form.setItem("room",room);
            form.setItem("id",message);
            form.setItem("profileId", profileId);
            List<DynaActionForm> select = dao.select(form);
            
            for(DynaActionForm temp : select){
               MessageEntity m = new MessageEntity();
                m.fromMap(temp.getItems());
                entities.add(m);
            }
            
            return entities;
        } catch (Exception ex) {
            Logger.getLogger(MessageResource.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    /**No usado*/
    @POST
    @Path("owner/id")
    @Produces("application/json")
    public List<MessageEntity> findMessageByOwner(@FormParam("id") Integer id) {
        try {
            Dao dao = DaoFactory.getDao("Message");
            DynaActionForm form = new DynaActionForm();
            List<MessageEntity> entities = new LinkedList<>();
            
            form.setItem("selector", "byOwner");
            form.setItem("owner",id);
            List<DynaActionForm> select = dao.select(form);
            
            for(DynaActionForm temp : select){
                MessageEntity m = new MessageEntity();
                m.fromMap(temp.getItems());
                entities.add(m);
            }
            
            return entities;
        } catch (Exception ex) {
            Logger.getLogger(MessageResource.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
