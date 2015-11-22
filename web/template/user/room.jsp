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
        </script>
        
        <fmt:bundle basename="ar.edu.ubp.das.properties.etiquetas">
            <c:set value="${form.roomId}" var="roomId" ></c:set>
            <c:set value="${form.roomName}" var="roomName" ></c:set>
            <c:set value="${form.roomType}" var="roomType" ></c:set>
            <c:set value="${form.profileId}" var="profileId" ></c:set>
            <c:set value="${form.profileType}" var="profileType" ></c:set>
            <c:set value="${form.userAccess}" var="userAccess" ></c:set>
            <c:set value="${form.accessDenied}" var="accessDenied" ></c:set>
            
            <input type="hidden" value="${roomId}" name="roomId" />
            <input type="hidden" value="${roomName}" name="roomName" />
            <input type="hidden" value="${roomType}" name="roomType" />
            <input type="hidden" value="${profileId}" name="profileId" />
            <input type="hidden" value="${profileType}" name="profileType" />
            <input type="hidden" value="${userAccess.id}" name="userAccessId" />            
            
            <a href="index.jsp?action=LoginProfile" >Close</a>
            
            <c:choose>
                <c:when test="${accessDenied != null}">
                    <input type="hidden" value="${accessDenied}" name="accessDenied" />            
                    <p>Access denied</p>
                </c:when>
                <c:otherwise>
                    <a href="index.jsp?action=LeaveGroup&userAccessId=${userAccess.id}" >Leave group</a>
                                
                    <h2>Room name: -${roomName}-</h2>

                    <div id="messages"></div>

                    <form id="sendMessage">
                        <input type="text" name="message" placeholder="Enter message" />
                        <input type="button" name="send" onclick="sendMessage()" value="Send"/>
                    </form>

                    <div id="participants"></div>
                    <div id="privateRooms"></div>
                    <div id="response"></div>
                    <div id="error"></div>
                </c:otherwise>
            </c:choose>
            
        </fmt:bundle>
        
        
        <script>
            $(document).ready(function(){
            if($("input[name='accessDenied']").val() == null){
                    
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
                        
                    }
                });
                
                if($("input[name='roomType']").val() == 'public'){
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
                }
                
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
               
                setInterval(reloadMessage, 10000);
                setInterval(reloadAccessPolicy, 10000);
                setInterval(reloadIfExistPrivateRoom, 10000);
                setInterval(reloadParticipants, 10000);
                
             }
             
             /**Check if the user has a new messages*/
                function reloadMessage(){
                    var messageId = $("#messages tr:last input[name='messageId']").val();
                    var roomId = $("input[name='roomId']").val();
                    var roomType = $("input[name='roomType']").val();
                    var profileType = $("input[name='profileType']").val();
                    var profileId = $("input[name='profileId']").val();
                    
                    $.ajax({
                        url: "index.jsp?action=UpdateMessage",
                        type: "post",
                        dataType: "html",            
                        data:  {'roomId': roomId, 'messageId': messageId, 'roomType': roomType, 'profileType': profileType, 'profileId': profileId},
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
                
                /**Check if the user has been ejected*/
                function reloadAccessPolicy(){
                    var roomId = $("input[name='roomId']").val();
                    var profileId = $("input[name='profileId']").val();
                    
                    $.ajax({
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
                    
                    $.ajax({
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
                        }
                    });
                }
             
            });
        </script>
    </body>
</html>