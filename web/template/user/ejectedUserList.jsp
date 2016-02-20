<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
    
<div>
    <fmt:bundle basename="ar.edu.ubp.das.properties.etiquetas">
        <c:set value="${form.ejectedUserList}" var="ejectedUsers" ></c:set>
        
        <c:if test="${ejectedUsers.size() > 0}"> 
            <div class="panel panel-primary">
                <div class="panel-heading">
                    Ejected Users
                </div>
            </div>
            <div class="panel-body">
                <ul class="media-list">
                <c:forEach items="${ejectedUsers}" var="participant" >
                        <li class="media" id="${participant.id}">
                            <div class="media-body">
                                <div class="media">
                                    <a class="pull-left" href="#">
                                        <img class="media-object img-circle" style="max-height:40px;" src="/chat/img/user.png" />
                                    </a>
                                    <div class="media-body" >
                                        ${participant.userName} | ${participant.roomName} 
                                        <a href="#" onclick="jsRoom.enabledUserAgain('${participant.id}'); return false;" >Enabled egain</a>
                                    </div>
                                </div>
                            </div>
                        </li>
                </c:forEach>
                </ul>
            </div>
        </c:if>
        <c:if test="${participants.size() == 0}">
            <h3><fmt:message key="participant_empty"/></h3>
        </c:if>
    </fmt:bundle>
</div>
                            