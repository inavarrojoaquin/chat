<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
    
<div>
    <fmt:bundle basename="ar.edu.ubp.das.properties.etiquetas">
        <p>Invitations</p>
        <c:set value="${form.invitations}" var="invitations" ></c:set>      
        <c:set value="${form.profileId}" var="profileId" ></c:set>      
        <input type="hidden" value="${profileId}" name="profileId" />
        
        <c:choose>
            <c:when test="${!empty invitations}">
                <c:forEach items="${invitations}" var="invitation" varStatus="loop">
                    <c:if test="${!invitation.getItem('state').equals('rejected')}" >
                        <div id="${invitation.getItem("id")}">
                            <p>Invitation from ${invitation.getItem("senderName")} to room ${invitation.getItem("roomName")}</p>
                            <p data-name="state">State: ${invitation.getItem("state")}</p>
                            <p>
                                <c:choose>
                                    <c:when test="${invitation.getItem('state').equals('pending')}">
                                        <a href="#" data-id="${invitation.getItem('id')}" data-state="accepted" data-profileid="${profileId}" data-room="${invitation.getItem('room')}" >Accept</a>
                                        <a href="#" data-id="${invitation.getItem('id')}" data-state="rejected" data-profileid="${profileId}" data-room="${invitation.getItem('room')}" >Reject</a>
                                    </c:when>
                                    <c:when test="${invitation.getItem('state').equals('accepted')}">
                                        <a href="index.jsp?action=Room&profileId=${profileId}&roomId=${invitation.getItem('room')}">Enter room</a>
                                    </c:when>
                                    <c:otherwise>

                                    </c:otherwise>
                                </c:choose>
                            </p>
                        </div>
                    </c:if>
                </c:forEach>
            </c:when>
            <c:otherwise>
                
                <p>No invitations</p>
                
            </c:otherwise>
        </c:choose>
    </fmt:bundle>
</div>
