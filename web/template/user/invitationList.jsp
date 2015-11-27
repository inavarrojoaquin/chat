<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
    
<div>
    <fmt:bundle basename="ar.edu.ubp.das.properties.etiquetas">
        <c:set value="${form.invitations}" var="invitations" ></c:set>      
        <c:set value="${form.profileId}" var="profileId" ></c:set>   
        <c:set value="${form.userLoginStart}" var="userLoginStart" ></c:set>   
        
        <input type="hidden" value="${profileId}" name="profileId" />
        
        <c:choose>
            <c:when test="${!empty invitations}">
                <c:forEach items="${invitations}" var="invitation" varStatus="loop">
                    <c:choose >
                        <c:when test="${invitation.getItem('state').equals('pending')}" >
                            <div id="${invitation.getItem("id")}">
                                <p>Invitation from ${invitation.getItem("senderName")} to room ${invitation.getItem("roomName")}</p>
                                <p data-name="state">State: ${invitation.getItem("state")}</p>
                                <div>
                                    <a href="#" data-id="${invitation.getItem('id')}" data-state="accepted" data-profileid="${profileId}" data-room="${invitation.getItem('room')}" >Accept</a>
                                    <a href="#" data-id="${invitation.getItem('id')}" data-state="rejected" data-profileid="${profileId}" data-room="${invitation.getItem('room')}" >Reject</a>
                                </div>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <div id="${invitation.getItem("id")}" style="display: none"></div>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </c:when>
            <c:otherwise>
                
                <p id="invitation_empty">No invitations</p>
                
            </c:otherwise>
        </c:choose>
    </fmt:bundle>
</div>
