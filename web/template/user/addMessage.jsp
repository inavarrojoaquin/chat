<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
    

<fmt:bundle basename="ar.edu.ubp.das.properties.etiquetas">
    <c:set value="${form.message}" var="message" ></c:set>
    <c:set value="${form.roomType}" var="roomType" ></c:set>
    <c:set value="${form.profileType}" var="profileType" ></c:set>
    <c:set value="${form.profileLogin}" var="profileLogin" ></c:set>
   
    <c:if test="${message != null}">
        <%-- Send message action --%>
        <li class="media" id="${message.id}">
            <div class="media-body">
                <div class="media">
                    <a class="pull-left" href="#"><img class="media-object img-circle" src="img/user.png" /></a>
                    <div class="media-body">
                        <p>${message.body}</p>
                        <small class="text-muted">${profileLogin} | <fmt:formatDate type="both" dateStyle="short" timeStyle="short" value="${message.datetimeOfCreation}" /></small>
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
    </c:if>        
</fmt:bundle>
