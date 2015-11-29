<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
    
<div>
    <fmt:bundle basename="ar.edu.ubp.das.properties.etiquetas">
        <c:set value="${form.invitations}" var="invitations" ></c:set>      
        <c:set value="${form.profileId}" var="profileId" ></c:set>   
        <c:set value="${form.userLoginStart}" var="userLoginStart" ></c:set>   

        <%-- Invitations --%>
        <c:choose>
            <c:when test="${!empty invitations}">
                <div class="panel panel-info">
                    <div class="panel-heading">
                        <fmt:message key="title_invitation"/>
                    </div>
                    <div class="panel-body modal-body">
                        <ul class="media-list">
                            <c:forEach items="${invitations}" var="invitation" >
                                <c:choose >
                                    <c:when test="${invitation.getItem('state').equals('pending')}" >
                                        <div id="${invitation.getItem("id")}">
                                            <li class="media">
                                                <div class="media-body">
                                                    <div class="media">
                                                        <a class="pull-left" href="#"><img class="media-object img-circle" src="img/invitation.png" /></a>

                                                        <div class="media-body">
                                                            <p>Invitation from ${invitation.getItem("senderName")} to room ${invitation.getItem("roomName")}</p>
                                                            <small class="text-muted"><p data-name="state"><fmt:message key="label_state"/> ${invitation.getItem("state")}</p></small>

                                                            <div class="btn-group btn-group-justified" role="group" aria-label="...">
                                                                <a href="#" role="button" class="btn btn-default" 
                                                                   onclick="jsHome.updateStateInvitation(${invitation.getItem('room')}, '${profileId}', ${invitation.getItem('id')}, 'accepted'); return false;" >
                                                                    <fmt:message key="lebel_accept"/>
                                                                </a>
                                                                <a href="#" role="button" class="btn btn-default" 
                                                                   onclick="jsHome.updateStateInvitation(${invitation.getItem('room')}, '${profileId}', ${invitation.getItem('id')}, 'rejected'); return false;" >
                                                                    <fmt:message key="label_reject"/>
                                                                </a>
                                                            </div>
                                                            <hr />
                                                        </div>
                                                    </div>
                                                </div>
                                            </li>
                                        </div>
                                    </c:when>
                                    <c:otherwise>

                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </ul>
                    </div>
                </div>
            </c:when>
            <c:otherwise>
                <h4 id="invitation_empty"><fmt:message key="invitation_empty"/></h4>
            </c:otherwise>
        </c:choose>
        <%-- /.Invitations --%>
    </fmt:bundle>
</div>

    
    