/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.ubp.das.rest;

import ar.edu.ubp.das.entities.ProfileEntity;
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
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author Febo
 */
@Path("profiles")
public class ProfileResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of ProfilesResource
     */
    public ProfileResource() {
    }

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response create(ProfileEntity entity) {
        try {
            DynaActionForm form = new DynaActionForm();
            form.setItems(entity.toMap());
            
            Dao profile = DaoFactory.getDao("Profile");
            profile.insert(form);
            entity.setId((Integer) form.getItem("id"));
            
            return Response.ok(entity).build();
        } catch (Exception ex) {
            Logger.getLogger(ProfileResource.class.getName()).log(Level.INFO, null, ex);
            return Response.status(Response.Status.CONFLICT).build();
        }
    }
    
    @GET
    @Produces("application/json")
    public List<ProfileEntity> findAll(){
        try {
            Dao dao;
            DynaActionForm form;
            List<DynaActionForm> resultSet;
            List<ProfileEntity> entities = new LinkedList<>();
            
            dao = DaoFactory.getDao("Profile");
            form = new DynaActionForm();
            form.setItem("selector", "findAll");
            resultSet = dao.select(form);
            
            for(DynaActionForm result : resultSet){
                ProfileEntity entity = new ProfileEntity();
                entity.fromMap(result.getItems());
                entities.add(entity);
            }
            
            return entities;
        } catch (Exception ex) {
            Logger.getLogger(ProfileResource.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
   /*Nos used*/ 
   @POST
   @Path("find/id")
   @Produces("application/json")
   public Response findById(@FormParam("id") Integer id ){
        try {
            Dao dao;
            List<DynaActionForm> resultSet;
            DynaActionForm form;
            
            dao = DaoFactory.getDao("Profile");
            form = new DynaActionForm();
            form.setItem("selector", "byId");
            form.setItem("id", id);
            resultSet = dao.select(form);
            
            if(resultSet.isEmpty()){
                return Response.status(Response.Status.NOT_FOUND).build();
            }else {
                ProfileEntity profile = new ProfileEntity();
                profile.fromMap(resultSet.get(0).getItems());
                return Response.ok(profile).build();
            }
            
        } catch (Exception ex) {
            Logger.getLogger(ProfileResource.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(Response.Status.PRECONDITION_FAILED).build();
        }
   }
   
   @POST
   @Path("find/login")
   @Produces("application/json")
   public Response findByLogin(@FormParam("login") String login ){
        try {
            Dao dao;
            List<DynaActionForm> resultSet;
            DynaActionForm form;
            
            dao = DaoFactory.getDao("Profile");
            form = new DynaActionForm();
            form.setItem("selector", "byLogin");
            form.setItem("login", login);
            resultSet = dao.select(form);
            
            if(resultSet.isEmpty()){
                return Response.status(Response.Status.NOT_FOUND).build();
            }else {
                ProfileEntity profile = new ProfileEntity();
                profile.fromMap(resultSet.get(0).getItems());
                return Response.ok(profile).build();
            }
            
        } catch (Exception ex) {
            Logger.getLogger(ProfileResource.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(Response.Status.PRECONDITION_FAILED).build();
        }
    }
   
   @POST
   @Path("find/login/password")
   @Produces("application/json")
   public Response findByLoginAndPassword(@FormParam("login") String login, @FormParam("password") String password ){
        try {
            Dao dao;
            List<DynaActionForm> resultSet;
            DynaActionForm form;
            
            dao = DaoFactory.getDao("Profile");
            form = new DynaActionForm();
            form.setItem("selector", "byLoginAndPassword");
            form.setItem("login", login);
            form.setItem("password", password);
            resultSet = dao.select(form);
            
            if(resultSet.isEmpty()){
                return Response.status(Response.Status.NOT_FOUND).build();
            }else {
                ProfileEntity profile = new ProfileEntity();
                profile.fromMap(resultSet.get(0).getItems());
                return Response.ok(profile).build();
            }
            
        } catch (Exception ex) {
            Logger.getLogger(ProfileResource.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(Response.Status.PRECONDITION_FAILED).build();
        }
    }
   
    @POST
    @Path("room/id/actives")
    @Produces("application/json")
    public List<ProfileEntity> findActivesByRoom(@FormParam("id") Integer id) {
        try {
            Dao dao;
            DynaActionForm form;
            List<DynaActionForm> resultSet;
            List<ProfileEntity> profiles = new LinkedList<>();

            dao = DaoFactory.getDao("Profile");
            form = new DynaActionForm();
            form.setItem("selector", "byRoom");
            form.setItem("room", id);
            resultSet = dao.select(form);
            
            for(DynaActionForm temp : resultSet){
                ProfileEntity profile = new ProfileEntity();
                profile.fromMap(temp.getItems());
                profiles.add(profile);
            }
            
            return profiles;
        } catch (Exception ex) {
            Logger.getLogger(ProfileResource.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    @POST
    @Path("rejected/invitation/by/room")
    @Produces("application/json")
    public List<ProfileEntity> findRejectedInvitationsByRoom(@FormParam("id") Integer id) {
        try {
            Dao dao;
            DynaActionForm form;
            List<DynaActionForm> resultSet;
            List<ProfileEntity> profiles = new LinkedList<>();

            dao = DaoFactory.getDao("Profile");
            form = new DynaActionForm();
            form.setItem("selector", "rejectedInvitationsByRoom");
            form.setItem("room", id);
            resultSet = dao.select(form);
            
            for(DynaActionForm temp : resultSet){
                ProfileEntity profile = new ProfileEntity();
                profile.fromMap(temp.getItems());
                profiles.add(profile);
            }
            
            return profiles;
        } catch (Exception ex) {
            Logger.getLogger(ProfileResource.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    @GET
    @Path("usersLogin/actives")
    @Produces("application/json")
    public List<ProfileEntity> findActivesUsersLogin() {
        try {
            Dao dao;
            DynaActionForm form;
            List<DynaActionForm> resultSet;
            List<ProfileEntity> profiles = new LinkedList<>();

            dao = DaoFactory.getDao("Profile");
            form = new DynaActionForm();
            form.setItem("selector", "byUsersActives");
            resultSet = dao.select(form);
            
            for(DynaActionForm temp : resultSet){
                ProfileEntity profile = new ProfileEntity();
                profile.fromMap(temp.getItems());
                profiles.add(profile);
            }
            
            return profiles;
        } catch (Exception ex) {
            Logger.getLogger(ProfileResource.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    /*Not used*/
    @POST
    @Path("usersLogin/actives/search")
    @Produces("application/json")
    public List<ProfileEntity> searchActivesUsersLogin(@FormParam("string_search") String string_search) {
        try {
            Dao dao;
            DynaActionForm form;
            List<DynaActionForm> resultSet;
            List<ProfileEntity> profiles = new LinkedList<>();

            dao = DaoFactory.getDao("Profile");
            form = new DynaActionForm();
            form.setItem("selector", "bySearchUsersActives");
            form.setItem("string_search", string_search);
            resultSet = dao.select(form);
            
            for(DynaActionForm temp : resultSet){
                ProfileEntity profile = new ProfileEntity();
                profile.fromMap(temp.getItems());
                profiles.add(profile);
            }
            
            return profiles;
        } catch (Exception ex) {
            Logger.getLogger(ProfileResource.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
