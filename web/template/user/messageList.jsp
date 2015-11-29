<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
    
<div>
    <fmt:bundle basename="ar.edu.ubp.das.properties.etiquetas">
        <c:set value="${form.roomType}" var="roomType" ></c:set>
        <c:set value="${form.profileType}" var="profileType" ></c:set>
        <c:set value="${form.messageList}" var="messages" ></c:set>        

        <div class="panel panel-info">
            <div class="panel-heading">
                <fmt:message key="title_messages" />
            </div>
            <div class="panel-body modal-body">
                <ul class="media-list">
                    <c:choose>
                        <c:when test="${messages != null && messages.size() > 0}">
                            <c:forEach items="${messages}" var="message" varStatus="loop">
                                <li class="media" id="${message.id}">
                                    <div class="media-body">
                                        <div class="media">
                                            <a class="pull-left" href="#"><img class="media-object img-circle" src="img/user.png" /></a>
                                            <div class="media-body">
                                                <p>${message.body}</p>
                                                <small class="text-muted">${message.owner} | <fmt:formatDate type="both" dateStyle="short" timeStyle="short" value="${message.datetimeOfCreation}" /></small>
                                                <c:if test="${roomType.equals('public') && profileType.equals('ADMIN')}" >
                                                    <div class="btn-group btn-group-justified" role="group" aria-label="...">
                                                        <a href="#" role="button" class="pull-right" onclick="jsRoom.deleteMessage('${message.id}'); return false;" ><fmt:message key="label_delete" /></a>
                                                    </div>
                                                </c:if> 
                                                <hr />
                                            </div>
                                        </div>
                                    </div>
                                </li>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <li class="media" id="message_empty" >
                                <div class="media-body">
                                    <div class="media">
                                        <div class="media-body">
                                            <p><fmt:message key="message_empty"/></p>
                                        </div>
                                    </div>
                                </div>
                            </li>
                        </c:otherwise>
                    </c:choose>
                </ul>
            </div>
        </div>
    </fmt:bundle>
</div>