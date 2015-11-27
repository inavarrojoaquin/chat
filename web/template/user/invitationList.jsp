<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
    
<div>
    <fmt:bundle basename="ar.edu.ubp.das.properties.etiquetas">        
        <c:set value="${form.invitations}" var="invitations" ></c:set>      
        <c:set value="${form.profileId}" var="profileId" ></c:set>   
        <c:set value="${form.userLoginStart}" var="userLoginStart" ></c:set>   
        
        <c:choose>
            <c:when test="${!empty invitations}">
                <p><fmt:message key="title_invitation"/></p>
                <c:forEach items="${invitations}" var="invitation" >
                    <c:choose >
                        <c:when test="${invitation.getItem('state').equals('pending')}" >
                            <div id="${invitation.getItem("id")}">
                                <p>Invitation from ${invitation.getItem("senderName")} to room ${invitation.getItem("roomName")}</p>
                                <p data-name="state"><fmt:message key="label_state"/> ${invitation.getItem("state")}</p>
                                <div>
                                    <a href="#" onclick="jsHome.updateStateInvitation(${invitation.getItem('room')}, '${profileId}', ${invitation.getItem('id')}, 'accepted'); return false;" ><fmt:message key="lebel_accept"/></a>
                                    <a href="#" onclick="jsHome.updateStateInvitation(${invitation.getItem('room')}, '${profileId}', ${invitation.getItem('id')}, 'rejected'); return false;" ><fmt:message key="label_reject"/></a>
                                </div>
                            </div>
                        </c:when>
                        <c:otherwise>
                            
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <p id="invitation_empty"><fmt:message key="invitation_empty"/></p>
            </c:otherwise>
        </c:choose>
    </fmt:bundle>
</div>
