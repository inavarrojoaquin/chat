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
                    data:  $.param($("#sendMessage input, input[name='roomId'], input[name='profileId'] ")),
                    error: function(hr) {
                        jUtils.showing("error", hr);
                    },
                    success: function(html) {
                        var message_empty = $("#messages tbody #message_empty").length;
                        if(message_empty == 1){
                            $("#messages tbody tr:last").remove();
                        }
                        $("#messages tbody:last").append(html);
                    }
                });
            }
        </script>
        
        <fmt:bundle basename="ar.edu.ubp.das.properties.etiquetas">
            <c:set value="${form.roomId}" var="roomId" ></c:set>
            <c:set value="${form.roomName}" var="roomName" ></c:set>
            <c:set value="${form.profileId}" var="profileId" ></c:set>
            <c:set value="${form.userAccess}" var="userAccess" ></c:set>
            
            <input type="hidden" value="${roomId}" name="roomId" />
            <input type="hidden" value="${roomName}" name="roomName" />
            <input type="hidden" value="${profileId}" name="profileId" />
            <input type="hidden" value="${userAccess.id}" name="userAccessId" />
            
            <a href="index.jsp?action=LoginProfile" >Close</a>
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
            
        </fmt:bundle>
        
        
        <script>
            $(document).ready(function(){
                
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
                
                
            });
        </script>
    </body>
</html>