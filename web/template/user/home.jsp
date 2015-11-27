<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script lang="javascript" type="text/javascript" src="./js/jquery.js"></script>
        <script lang="javascript" type="text/javascript" src="./js/utils.js"></script>
        <script lang="javascript" type="text/javascript" src="./js/chat.js"></script>        
        <title>Home</title>
    </head>
    <body>
        <fmt:bundle basename="ar.edu.ubp.das.properties.etiquetas">
            <a href="index.jsp?action=LogoutProfile" >Logout</a>
            <br>
                
            <c:set value="${form.profile}" var="profile" ></c:set>
            <input type="hidden" value="${profile.getId()}" name="profileId" />
            
            <div id="publicRooms"></div>
            <div id="invitations"></div>
            <div id="response"></div>
            <div id="error"></div>
        </fmt:bundle>
            
        <script>
            $(document).ready(function(){
                var RELOAD_TIME = 15000;
                
                function refreshPublicRoom(){
                    var promise = reloadPublicRoom();
                    promise.done(function(){
                        setTimeout(refreshPublicRoom, RELOAD_TIME);
                    });
                }
                
                function refreshInvitation(){
                    var promise = reloadInvitation();
                    promise.done(function(){
                        setTimeout(refreshInvitation, RELOAD_TIME);
                    });

                }
                
                $.ajax({
                    url: "index.jsp?action=GetPublicRoom",
                    type: "post",
                    dataType: "html",            
                    data:  $.param($("input[type='hidden']")),
                    error: function(hr) {
                        jUtils.showing("error", hr);
                    },
                    success: function(html) {
                        jUtils.showing("publicRooms", html);
                    }
                }).success(refreshPublicRoom);
                        
                $.ajax({
                    url: "index.jsp?action=GetInvitationList",
                    type: "post",
                    dataType: "html",            
                    data:  $.param($("input[type='hidden']")),
                    error: function(hr) {
                        jUtils.showing("error", hr);
                    },
                    success: function(html) {
                        jUtils.showing("invitations", html);
                    }
                }).success(refreshInvitation);
                
                $("#invitations").on('click','a[data-id]', function(){
                    var element = $(this);
                    var room = $(this).data("room");
                    var profileId = $(this).data("profileid");
                    var id = $(this).data("id");
                    var newState = $(this).data("state");
                    
                    $.ajax({
                        url: "index.jsp?action=UpdateStateInvitation",
                        type: "post",
                        dataType: "html",            
                        data:  {'id': id, 'newState': newState},
                        error: function(hr) {
                            jUtils.showing("error", hr);
                        },
                        success: function(html) {
                            if(newState == "accepted"){
                                element.parents("div#"+id).remove();
                                window.open("http://localhost:8080/chat/index.jsp?action=Room&profileId="+profileId+"&roomId="+room , '_blank');
                            }else {
                                element.parents("div#"+id).remove();
                            }                            
                        }
                    });
                    
                    return false;
                });
                
                /**Check if have a new invitation*/
                function reloadInvitation(){
                    var profileId = $("input[name='profileId']").val();

                    return $.ajax({
                         url: "index.jsp?action=GetInvitationList",
                         type: "post",
                         dataType: "html",            
                         data:  {'profileId': profileId},
                         error: function(hr) {
                             jUtils.showing("error", hr);
                         },
                         success: function(html) {
                            jUtils.showing("invitations", html);
                         }
                     }); 
                }
                
                /**Reload public room list*/
                function reloadPublicRoom(){
                    var profileId = $("input[name='profileId']").val();
                    
                    return $.ajax({
                        url: "index.jsp?action=GetPublicRoom",
                        type: "post",
                        dataType: "html",            
                        data:  {'profileId': profileId},
                        error: function(hr) {
                            jUtils.showing("error", hr);
                        },
                        success: function(html) {
                            jUtils.showing("publicRooms", html);
                        }
                    });
                }
                
            });
        </script>
    </body>
</html>
