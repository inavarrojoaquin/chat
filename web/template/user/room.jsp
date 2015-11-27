<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script lang="javascript" type="text/javascript" src="./js/jquery.js"></script>
        <script lang="javascript" type="text/javascript" src="./js/utils.js"></script>
        <script lang="javascript" type="text/javascript" src="./js/jsRoom.js"></script>        
        <title>Room</title>
    </head>
    <body>
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
                </c:when>
                <c:otherwise>
                    <c:choose>
                        <c:when test="${roomType.equals('private')}" >
                            <c:choose>
                                <c:when test="${roomOwner == profileId}" >
                                    <a id="deletePrivateRoom" href="#" onclick="jsRoom.deletePrivateRoom(); return false;" >Close chat</a>
                                </c:when>
                                <c:otherwise>
                                    <a id="leavePrivateRoom" href="#" onclick="jsRoom.deletePrivateRoom(); return false;" >Leave chat</a>
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
                        <input type="button" name="send" onclick="jsRoom.sendMessage();" value="Send" />
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
                                    <input type="button" id="submit" onclick="jsRoom.newPrivateRoom();" value='Create my chat' />
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
                                    <input type="button" id="submit" onclick="jsRoom.inviteParticipant();" value='Invite' />
                                </form>
                            </div>
                        </c:otherwise>
                    </c:choose>
                    
                    <div id="response"></div>
                    <div id="error"></div>
                </c:otherwise>
            </c:choose>
        </fmt:bundle>
    </body>
</html>