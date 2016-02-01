<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
    
<div>
    <fmt:bundle basename="ar.edu.ubp.das.properties.etiquetas">
        <c:set value="${form.participantsList}" var="participants" ></c:set>
        <c:set value="${form.profileType}" var="profileType" ></c:set>
        <c:set value="${form.roomId}" var="roomId" ></c:set> 
        <c:set value="${form.userAccessId}" var="userAccessId" ></c:set>
        <c:set value="${form.profileId}" var="profileId" ></c:set>
        
        <c:if test="${participants.size() > 0}"> 
            <div class="panel panel-primary">
                <div class="panel-heading">
                    <fmt:message key="title_participant"/>
                </div>
            </div>
            <div class="panel-body">
                <ul class="media-list">
                <c:forEach items="${participants}" var="participant" >
                    <c:if test="${participant.id != profileId}" >
                        <li class="media" id="${participant.id}">
                            <div class="media-body">
                                <div class="media">
                                    <a class="pull-left" href="#">
                                        <img class="media-object img-circle" style="max-height:40px;" src="/chat/img/user.png" />
                                    </a>
                                    <div class="media-body" >
                                        <c:choose>
                                            <c:when test="${inviteMore == null}" >
                                                <a href="#" onclick="jsRoom.addToInvite('${participant.login}'); return false;" >${participant.login}</a>
                                            </c:when>
                                            <c:otherwise>
                                                <a href="#" onclick="jsRoom.addToInviteList('${participant.login}'); return false;" >${participant.login}</a>
                                            </c:otherwise>
                                        </c:choose>
                                        <c:if test="${profileType.equals('ADMIN')}" >
                                            <a href="#" onclick="jsRoom.ejectUser('${roomId}', '${participant.id}'); return false;" ><fmt:message key="label_eject_user"/></a>
                                        </c:if> 
                                    </div>
                                </div>
                            </div>
                        </li>
                    </c:if>
                </c:forEach>
                </ul>
            </div>
        </c:if>
        <c:if test="${participants.size() == 0}">
            <h3><fmt:message key="participant_empty"/></h3>
        </c:if>
    </fmt:bundle>
</div>
                            