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
            <input type="hidden" value="${profile.getType()}" name="profileType" />
            
            <div id="publicRooms"></div>
            <div id="privateRooms"></div>
            <div>
                <h4>Start a private chat</h4>
                <form id="createPrivateRoom">
                    <label for="title">Title/Subject</label>
                    <input type="text" id="title" name="titleRoom" placeholder="I want to talk about..."/>
                    <br>
                    <label for="inviteEmail">Invite email</label>
                    <input type="text" id="inviteEmail" name="inviteEmailRoom" placeholder="somebody@somewhere.com"/>
                    <br>
                    <input type="button" id="submit" value="Create my chat"/>
                </form>
            </div>
        </fmt:bundle>
        <div id="invitations"></div>
        <div id="response"></div>
        <div id="error"></div>
                
        <script>
            $(document).ready(function(){
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
                });
                
                $.ajax({
                    url: "index.jsp?action=GetPrivateRoom",
                    type: "post",
                    dataType: "html",            
                    data:  $.param($("input[type='hidden']")),
                    error: function(hr) {
                        jUtils.showing("error", hr);
                    },
                    success: function(html) {
                        jUtils.showing("privateRooms", html);
                    }
                });
                
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
                });
                
                $("#createPrivateRoom #submit").on('click', function(){
                    var profileId = $("input[name='profileId']").val();
                    var title = $("input[name='titleRoom']").val();
                    var inviteEmail = $("input[name='inviteEmailRoom']").val();
                    
                    $.ajax({
                        url: "index.jsp?action=NewPrivateRoom",
                        type: "post",
                        dataType: "html",            
                        data:  {'profileId': profileId, 'title': title, 'inviteEmail': inviteEmail},
                        error: function(hr) {
                            jUtils.showing("error", hr);
                        },
                        success: function(roomId) {
                            //the call response roomId
                            window.location.href = "http://localhost:8080/chat/index.jsp?action=Room&profileId="+profileId+"&roomId="+roomId;
                        }
                    });
                });
                    
                $("#invitations").on('click','a[data-id]', function(){
                    var element = $(this);
                    var parent = $(this).parent();
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
                                parent.empty();
                                parent.siblings("p[data-name='state']").text("State: "+newState);
                                parent.append("<a href=\"index.jsp?action=Room&profileId="+profileId+"&roomId="+room+" \">Enter</a>");
                            }else {
                                element.parents("div#"+id).remove();
                            }                            
                        }
                    });
                    
                    return false;
                });
                
                $("#privateRooms").on('click','a[data-roomid]', function(){
                    var parent = $(this).parents("tr");
                    var roomId = $(this).data("roomid");
                    
                    $.ajax({
                        url: "index.jsp?action=DeletePrivateRoom",
                        type: "post",
                        dataType: "html",            
                        data:  {'roomId': roomId},
                        error: function(hr) {
                            jUtils.showing("error", hr);
                        },
                        success: function(html) {
                            parent.remove();                            
                        }
                    });
                    
                    return false;
                });
                
                setInterval(reloadInvitation, 10000);  
                setInterval(reloadPublicRoom, 10000);
                
                /**Check if have a new invitation*/
                function reloadInvitation(){
                    var profileId = $("input[name='profileId']").val();
                    var lastInvitationId = $("#invitations div:last").attr("id");

                    $.ajax({
                         url: "index.jsp?action=UpdateInvitation",
                         type: "post",
                         dataType: "html",            
                         data:  {'profileId': profileId, 'invitationId': lastInvitationId},
                         error: function(hr) {
                             jUtils.showing("error", hr);
                         },
                         success: function(html) {
                             if($.trim(html) != "empty"){
                                 var invitation_empty = $("#invitations p#invitation_empty").length;
                                 if(invitation_empty == 1){
                                    $("#invitations p#invitation_empty").remove();
                                 }
                                 $("#invitations").append(html);
                             }
                         }
                     }); 
                }
                
                /**Reload public room list*/
                function reloadPublicRoom(){
                    var profileId = $("input[name='profileId']").val();
                    var profileType = $("input[name='profileType']").val();
                    
                    $.ajax({
                        url: "index.jsp?action=GetPublicRoom",
                        type: "post",
                        dataType: "html",            
                        data:  {'profileId': profileId, 'profileType': profileType},
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
