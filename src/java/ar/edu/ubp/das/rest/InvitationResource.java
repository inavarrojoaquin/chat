package ar.edu.ubp.das.rest;

import ar.edu.ubp.das.entities.InvitationEntity;
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
@Path("invitations")
public class InvitationResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of InvitationsResource
     */
    public InvitationResource() {
    }

    @POST
    @Produces("application/json")
    @Consumes("application/json")
    public Response create(InvitationEntity entity){
        try {
            Dao dao = DaoFactory.getDao("Invitation");
            DynaActionForm form = new DynaActionForm();
            
            form.setItems(entity.toMap());
            dao.insert(form);
            entity.setId((Integer) form.getItem("id"));
            
            return Response.ok(entity).build();
        } catch (Exception ex) {
            Logger.getLogger(UserLoginResource.class.getName()).log(Level.SEVERE, null, ex);
            return Response.serverError().build();
        }
    }
    
    @PUT
    @Produces("application/json")
    @Consumes("application/json")
    public Response updateInvitation(InvitationEntity entity){
        try {
            Dao dao = DaoFactory.getDao("Invitation");
            DynaActionForm form = new DynaActionForm();
            
            form.setItems(entity.toMap());
            dao.update(form);
            
            return Response.ok(entity).build();
        } catch (Exception ex) {
            Logger.getLogger(InvitationResource.class.getName()).log(Level.SEVERE, null, ex);
            return Response.serverError().build();
        }
    }
    
    @GET
    @Produces("application/json")
    public List<InvitationEntity> findAll() {
        try {
            Dao dao = DaoFactory.getDao("Invitation");
            DynaActionForm form = new DynaActionForm();
            List<InvitationEntity> entities = new LinkedList<>();
            
            form.setItem("selector", "findAll");
            
            for(DynaActionForm temp : dao.select(form) ){
                InvitationEntity i = new InvitationEntity();
                i.fromMap(temp.getItems());
                entities.add(i);
            }
            
            if(!entities.isEmpty()){
                return entities;
            }else {
                return null;
            }
        } catch (Exception ex) {
            Logger.getLogger(InvitationResource.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    @GET
    @Path("{id}")
    @Produces("application/json")
    public Response findById(@PathParam("id") Integer id) {
        try {
            Dao dao;
            DynaActionForm form;
            List<DynaActionForm> resultSet;
            
            dao = DaoFactory.getDao("Invitation");
            form = new DynaActionForm();
            form.setItem("selector", "byId");
            form.setItem("id", id);
            resultSet = dao.select(form);
            
            if(resultSet.size() == 1){
                InvitationEntity entity = new InvitationEntity(); 
                entity.fromMap(resultSet.get(0).getItems());
                return Response.ok(entity).build();
            }
            else {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
        } catch (Exception ex) {
            Logger.getLogger(InvitationResource.class.getName()).log(Level.SEVERE, null, ex);
            return Response.serverError().build();
        }
    }
    
    @GET
    @Path("sender/{sender}")
    @Produces("application/json")
    public List<InvitationEntity> findInvitationBySender(@PathParam("sender") Integer sender) {
        try {
            Dao dao;
            DynaActionForm form;
            List<DynaActionForm> resultSet;
            List<InvitationEntity> entities = new LinkedList<>();
            
            dao = DaoFactory.getDao("Invitation");
            form = new DynaActionForm();
            form.setItem("selector", "bySender");
            form.setItem("sender", sender);
            resultSet = dao.select(form);
            
            for(DynaActionForm temp : resultSet ){
                InvitationEntity i = new InvitationEntity();
                i.fromMap(temp.getItems());
                entities.add(i);
            }
            
            if(!entities.isEmpty()){
                return entities;
            }else {
                return null;
            }
        } catch (Exception ex) {
            Logger.getLogger(InvitationResource.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    @GET
    @Path("receiver/{receiver}")
    @Produces("application/json")
    public List<InvitationEntity> findInvitationByReceiver(@PathParam("receiver") Integer receiver) {
        try {
            Dao dao;
            DynaActionForm form;
            List<DynaActionForm> resultSet;
            List<InvitationEntity> entities = new LinkedList<>();
            
            dao = DaoFactory.getDao("Invitation");
            form = new DynaActionForm();
            form.setItem("selector", "byReceiver");
            form.setItem("receiver", receiver);
            resultSet = dao.select(form);
            
            for(DynaActionForm temp : resultSet ){
                InvitationEntity i = new InvitationEntity();
                i.fromMap(temp.getItems());
                entities.add(i);
            }
            
            if(!entities.isEmpty()){
                return entities;
            }else {
                return null;
            }
        } catch (Exception ex) {
            Logger.getLogger(InvitationResource.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
