<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
    
<div>
    <fmt:bundle basename="ar.edu.ubp.das.properties.etiquetas">
        <c:set value="${form.participantsList}" var="participants" ></c:set>
        <c:set value="${form.profileType}" var="profileType" ></c:set>
        <c:set value="${form.roomId}" var="roomId" ></c:set> 
        <c:set value="${form.userAccessId}" var="userAccessId" ></c:set>
        <c:set value="${form.flag}" var="inviteMore" ></c:set>
        
        <c:if test="${participants.size() > 0}"> 
            <c:choose>
                <c:when test="${inviteMore == null}" ><p><fmt:message key="title_participant"/></p></c:when>
                <c:otherwise><p><fmt:message key="title_participant_list"/></p></c:otherwise>
            </c:choose> 
            <table border="1">
                <tr>
                    <th><fmt:message key="label_name"/></th>
                </tr>
                <c:forEach items="${participants}" var="participant" >
                    <tr id="${participant.id}">
                        <c:choose>
                            <c:when test="${inviteMore == null}" >
                                <td><a href="#" onclick="jsRoom.addToInvite('${participant.login}'); return false;" >${participant.login}</a></td>
                            </c:when>
                            <c:otherwise>
                                <td><a href="#" onclick="jsRoom.addToInviteList('${participant.login}'); return false;" >${participant.login}</a></td>
                            </c:otherwise>
                        </c:choose>
                        <c:if test="${profileType.equals('ADMIN')}" >
                            <td><a href="#" onclick="jsRoom.ejectUser('${roomId}', '${participant.id}'); return false;" >Eject user</a></td>
                        </c:if> 
                    </tr>
                </c:forEach>
            </table>
        </c:if>
        <c:if test="${participants.size() == 0}">
            <p><fmt:message key="participant_empty"/></p>
        </c:if>
    </fmt:bundle>
</div>