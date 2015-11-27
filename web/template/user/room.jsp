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
        <title>Room</title>
    </head>
    <body>
        <script>
            function sendMessage(){
                $.ajax({
                    url: "index.jsp?action=SendMessage",
                    type: "post",
                    dataType: "html",            
                    data:  $.param($("#sendMessage input, input[name='roomId'], input[name='profileId'], input[name='roomType'], input[name='profileType'] ")),
                    error: function(hr) {
                        jUtils.showing("error", hr);
                    },
                    success: function(html) {
                        if($.trim(html)){
                            var message_empty = $("#messages tbody #message_empty").length;
                            if(message_empty == 1){
                                $("#messages tbody tr:last").remove();
                            }
                            $("#messages tbody:last").append(html);
                        }
                    }
                });
            }
            
            function deletePrivateRoom(){
                var roomId = $("input[name='roomId']").val();

                $.ajax({
                    url: "index.jsp?action=DeletePrivateRoom",
                    type: "post",
                    dataType: "html",            
                    data:  {'roomId': roomId},
                    error: function(hr) {
                        jUtils.showing("error", hr);
                    },
                    success: function(html) {
                        window.close();
                    }
                });

                return false;
            }
            
            function leavePrivateRoom(){
                var userAccessId = $("input[name='userAccessId']").val();

                $.ajax({
                    url: "index.jsp?action=LeaveGroup",
                    type: "post",
                    dataType: "html",            
                    data:  {'userAccessId': userAccessId},
                    error: function(hr) {
                        jUtils.showing("error", hr);
                    },
                    success: function(html) {
                        window.close();
                    }
                });

                return false;
            }
        </script>
        
        <fmt:bundle basename="ar.edu.ubp.das.properties.etiquetas">
            <c:set value="${form.room}" var="room" ></c:set>
            <c:set value="${room.getId()}" var="roomId" ></c:set>
            <c:set value="${room.getName()}" var="roomName" ></c:set>
            <c:set value="${room.getType()}" var="roomType" ></c:set>
            <c:set value="${room.getOwner()}" var="roomOwner" ></c:set>
            <c:set value="${form.profile}" var="profile" ></c:set>           
            <c:set value="${profile.getId()}" var="profileId" ></c:set>        
            <c:set value="${profile.getType()}" var="profileType" ></c:set>        
            <c:set value="${form.userAccess}" var="userAccess" ></c:set>
            <c:set value="${userAccess.getId()}" var="userAccessId" ></c:set>
            <c:set value="${form.accessDenied}" var="accessDenied" ></c:set>
            
            <input type="hidden" value="${roomId}" name="roomId" />
            <input type="hidden" value="${roomName}" name="roomName" />
            <input type="hidden" value="${roomType}" name="roomType" />
            <input type="hidden" value="${profileId}" name="profileId" />
            <input type="hidden" value="${profileType}" name="profileType" />
            <input type="hidden" value="${userAccessId}" name="userAccessId" />   

            <c:choose>
                <c:when test="${accessDenied != null}">
                    <input type="hidden" value="${accessDenied}" name="accessDenied" />            
                    <a href="index.jsp?action=LoginProfile" >Close</a>
                    <p>Access denied</p>
                </c:when>
                <c:otherwise>
                    <c:choose>
                        <c:when test="${roomType.equals('private')}" >
                            <c:choose>
                                <c:when test="${roomOwner == profileId}" >
                                    <a id="deletePrivateRoom" href="#" onclick="deletePrivateRoom();">Close chat</a>
                                </c:when>
                                <c:otherwise>
                                    <a id="leavePrivateRoom" href="#" onclick="deletePrivateRoom();" >Leave chat</a>
                                </c:otherwise>
                            </c:choose>
                        </c:when>
                        <c:otherwise>
                            <a href="index.jsp?action=LoginProfile" >Close</a>
                            <a href="index.jsp?action=LeaveGroup&userAccessId=${userAccess.id}" >Leave group</a>
                        </c:otherwise>
                    </c:choose>
                    
                    <h2>Room name: -${roomName}-</h2>

                    <div id="messages"></div>

                    <form id="sendMessage" >
                        <input type="text" name="message" placeholder="Enter message" />
                        <input type="button" name="send" onclick="sendMessage()" value="Send" />
                    </form>

                    <div id="participants"></div>   <%-- Participants in this room --%>
                    <div id="allLoguedParticipants"></div> <%-- All logued participants --%>
                    <div id="participateRoom"></div> <%-- Rooms in which I participate --%>
                    
                    <c:choose>
                        <c:when test="${roomType.equals('public')}">
                            <div>
                                <h4>Start a private chat</h4>
                                <form id="createPrivateRoom">
                                    <label for="title">Title/Subject</label>
                                    <input type="text" id="title" name="titleRoom" placeholder="I want to talk about..."/>
                                    <br>
                                    <label for="inviteEmail">Invite email</label>
                                    <input type="text" id="inviteEmail" name="inviteEmailRoom" placeholder="somebody@somewhere.com"/>
                                    <br>
                                    <input type="button" id="submit" value='Create my chat' />
                                </form>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <div>
                                <h4>Invite participants</h4>
                                <form id="inviteParticipant">
                                    <label for="inviteEmail">Invite email</label>
                                    <input type="text" id="inviteEmail" name="inviteEmailRoom" placeholder="somebody@somewhere.com"/>
                                    <br>
                                    <input type="button" id="submit" value='Invite' />
                                </form>
                            </div>
                        </c:otherwise>
                    </c:choose>
                    
                    <div id="response"></div>
                    <div id="error"></div>
                </c:otherwise>
            </c:choose>
            
        </fmt:bundle>
        
        
        <script>
            $(document).ready(function(){
                var RELOAD_TIME = 15000;
                var privateRoom = $("input[name='roomType']").val() == "private" ? true : false;
                
                /*Estos metodos se ejecutan recursivamente. 
                 * Los ajax devuelven promesas (return $.ajax() el ajax retorna una promesa), y si el ajax terminó (promise.done()) 
                 * recien se ejeccuta el setTimeOut llamando recursivamente a la misma funcion.
                 * Necesariamente para cada refresh tengo que hacer un metodo aparte
                 * Ademas estos metodos tiene dependencia, necesitan q los primeros ajax se terminen para recien ejecutarse
                 * pj: $.ajax().success(refreshMessage)
                 * */
                function refreshMessage(){
                    var promise = reloadMessage();
                    promise.done(function(){
                        setTimeout(refreshMessage, RELOAD_TIME);
                    });
                }
                    
                function refreshParticipants(){
                    var promise = reloadParticipants();
                    promise.done(function(){
                        setTimeout(refreshParticipants, RELOAD_TIME);
                    });

                }
                
                function refreshAllLoguedParticipants(){
                    var promise = reloadAllLoguedParticipants();
                    promise.done(function(){
                        setTimeout(refreshAllLoguedParticipants, RELOAD_TIME);
                    });
                }
                 // null y undefined evaluan en falso en js
                // !! transforma en boolean
                if ( (!! $("input[name='accessDenied']").val()) == false ){
                     
                    $.ajax({
                        url: "index.jsp?action=GetMessageList",
                        type: "post",
                        dataType: "html",            
                        data:  $.param($("input[type='hidden']")),
                        error: function(hr) {
                            jUtils.showing("error", hr);
                        },
                        success: function(html) {
                            jUtils.showing("messages", html);
                        }
                    }).success(refreshMessage);
                    
                    $.ajax({
                        url: "index.jsp?action=GetPublicRoomByProfile",
                        type: "post",
                        dataType: "html",            
                        data:  $.param($("input[type='hidden']")),
                        error: function(hr) {
                            jUtils.showing("error", hr);
                        },
                        success: function(html) {
                            jUtils.showing("participateRoom", html);
                        }
                    });

                    $.ajax({
                        url: "index.jsp?action=GetParticipantList",
                        type: "post",
                        dataType: "html",            
                        data:  $.param($("input[type='hidden']")),
                        error: function(hr) {
                            jUtils.showing("error", hr);
                        },
                        success: function(html) {
                            jUtils.showing("participants", html);
                            var cant = $("#participants tr[data-name]").length;
                             
                            if(privateRoom){
                                if(cant > 1){
                                    $("input[name='message']").removeAttr('disabled');
                                    $("input[name='send']").removeAttr('disabled');
                                }else{
                                    $("input[name='message']").attr('disabled', 'disabled');
                                    $("input[name='send']").attr('disabled', 'disabled');
                                }
                            }
                        }
                    }).success(refreshParticipants);

                    if(privateRoom){
                        $.ajax({
                            url: "index.jsp?action=GetAllLoguedParticipantList",
                            type: "post",
                            dataType: "html",            
                            data:  $.param($("input[type='hidden']")),
                            error: function(hr) {
                                jUtils.showing("error", hr);
                            },
                            success: function(html) {
                                jUtils.showing("allLoguedParticipants", html);
                            }
                        }).success(refreshAllLoguedParticipants);
                    }
                    
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
                                //the call ajax response roomId
                                window.open("http://localhost:8080/chat/index.jsp?action=Room&profileId="+profileId+"&roomId="+roomId , 'PrivateChat, _blank');
                            }
                        });
                    });
                    
                    $("#inviteParticipant #submit").on('click', function(){
                        var roomId = $("input[name=roomId]").val();
                        var profileId = $("input[name='profileId']").val();
                        var participantList = $("input[name='inviteEmailRoom']").val();

                        $.ajax({
                            url: "index.jsp?action=InviteParticipant",
                            type: "post",
                            dataType: "html",            
                            data:  {'roomId': roomId, 'profileId': profileId, 'participantList': participantList},
                            error: function(hr) {
                                jUtils.showing("error", hr);
                            },
                            success: function(html) {
                                console.log("Success send invitations");
                            }
                        });
                    });

                    $("#messages").on('click','a[data-delete]', function(){
                        var parent = $(this).parents("tr");
                        var id = $(this).data("id");

                        $.ajax({
                            url: "index.jsp?action=DeleteMessage",
                            type: "post",
                            dataType: "html",            
                            data:  {'id': id},
                            error: function(hr) {
                                jUtils.showing("error", hr);
                            },
                            success: function(html) {
                                parent.remove();
                            }
                        });

                        return false;
                    });

                    $("#participants").on('click','a[data-login]', function(){
                        var login = $(this).data("login");
                        var inviteEmail = $("#createPrivateRoom #inviteEmail").val(login);
                        
                        return false;
                    });
                    
                    $("#allLoguedParticipants").on('click','a[data-login]', function(){
                        var login = $(this).data("login");
                        var participantList = $("#inviteParticipant #inviteEmail");
                        
                        if(participantList.val() == ""){
                            participantList.val(login); 
                        }else{
                            participantList.val(participantList.val() + ";" + login); 
                        }
                        
                        return false;
                    });

                    $("#participants").on('click','a[data-delete]', function(){
                        var parent = $(this).parents("tr");
                        var roomId = $(this).data("roomid");
                        var profileId = $(this).data("profileid");
                        var userAccessId = $(this).data("useraccessid");

                        $.ajax({
                            url: "index.jsp?action=EjectUser",
                            type: "post",
                            dataType: "html",            
                            data:  {'roomId': roomId, 'profileId': profileId, 'userAccessId': userAccessId},
                            error: function(hr) {
                                jUtils.showing("error", hr);
                            },
                            success: function(html) {
                                parent.remove();
                            }
                        });

                        return false;
                    });
                    
                    refreshAccessPolicy();
                    
                    if(privateRoom){
                        refreshIfExistPrivateRoom();
                        refreshRejectedInvitations();
                    }
                    
                }
                
                /*no dependences*/
                function refreshAccessPolicy(){
                    var promise = reloadAccessPolicy();
                    promise.done(function(){
                        setTimeout(refreshAccessPolicy, RELOAD_TIME);
                    });

                }
                /*no dependences*/
                function refreshIfExistPrivateRoom(){
                    var promise = reloadIfExistPrivateRoom();
                    promise.done(function(){
                        setTimeout(refreshIfExistPrivateRoom, RELOAD_TIME);
                    });
                }
                /*no dependences*/
                function refreshRejectedInvitations(){
                    var promise = reloadRejectedInvitations();
                    promise.done(function(){
                        setTimeout(refreshRejectedInvitations, RELOAD_TIME);
                    });
                }
             
                /**Check if the user has a new messages*/
                function reloadMessage(){
                    var roomId = $("input[name='roomId']").val();
                    var roomType = $("input[name='roomType']").val();
                    var profileId = $("input[name='profileId']").val();
                    var profileType = $("input[name='profileType']").val();
                    
                    return $.ajax({
                        url: "index.jsp?action=GetMessageList",
                        type: "post",
                        dataType: "html",            
                        data:  {"roomId":roomId, "roomType":roomType, "profileId":profileId, "profileType":profileType},                        
                        error: function(hr) {
                            jUtils.showing("error", hr);
                        },
                        success: function(html) {
                            jUtils.showing("messages", html);
                        }
                    });
                }
                
                /**Check if the user has been ejected*/
                function reloadAccessPolicy(){
                    var roomId = $("input[name='roomId']").val();
                    var profileId = $("input[name='profileId']").val();
                    
                    return $.ajax({
                        url: "index.jsp?action=UpdateCheckAccessPolicy",
                        type: "post",
                        dataType: "html",            
                        data:  {'roomId': roomId, 'profileId': profileId},
                        error: function(hr) {
                            jUtils.showing("error", hr);
                        },
                        success: function(html) {
                            if($.trim(html) == "ProfileEjected"){
                                alert("You has been ejected to this room");
                                parent.history.back();
                            }
                        }
                    });        
                }
                
                /**Check if the room has benn removed*/
                function reloadIfExistPrivateRoom(){
                    var roomId = $("input[name='roomId']").val();
                    
                    return $.ajax({
                        url: "index.jsp?action=UpdateCheckIfExistPrivateRoom",
                        type: "post",
                        dataType: "html",            
                        data:  {'roomId': roomId},
                        error: function(hr) {
                            jUtils.showing("error", hr);
                        },
                        success: function(html) {
                            if($.trim(html) == "not_found"){
                                alert("This room has been removed");
                                parent.history.back();
                            }
                        }
                    });        
                }
                
                /**Reload participant list*/
                function reloadParticipants(){
                    return $.ajax({
                        url: "index.jsp?action=GetParticipantList",
                        type: "post",
                        dataType: "html",            
                        data:  $.param($("input[type='hidden']")),
                        error: function(hr) {
                            jUtils.showing("error", hr);
                        },
                        success: function(html) {
                            jUtils.showing("participants", html);
                            var cant = $("#participants tr[data-name]").length;
                         
                            if(privateRoom){
                                if(cant > 1){
                                    $("input[name='message']").removeAttr('disabled');
                                    $("input[name='send']").removeAttr('disabled');
                                }else{
                                    $("input[name='message']").attr('disabled', 'disabled');
                                    $("input[name='send']").attr('disabled', 'disabled');
                                }
                            }
                        }
                    });
                }
                
                function reloadAllLoguedParticipants(){
                    return $.ajax({
                        url: "index.jsp?action=GetAllLoguedParticipantList",
                        type: "post",
                        dataType: "html",            
                        data:  $.param($("input[type='hidden']")),
                        error: function(hr) {
                            jUtils.showing("error", hr);
                        },
                        success: function(html) {
                            jUtils.showing("allLoguedParticipants", html);
                        }
                    });
                }
                
                function reloadRejectedInvitations(){
                    var roomId = $("input[name='roomId']").val();
                    
                    return $.ajax({
                        url: "index.jsp?action=UpdateRejectedInvitation",
                        type: "post",
                        dataType: "json",            
                        data:  {"roomId" : roomId},
                        error: function(hr) {
                            jUtils.showing("error", hr);
                        },
                        success: function(data) {
                            $.each(data, function(index, element) {
                                alert("El usuario: " + element.login + " rechazo la invitacion al chat");
                            });
                        }
                    });
                }
                
            });
        </script>
    </body>
</html>