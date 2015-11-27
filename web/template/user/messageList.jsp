<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
    
<div>
    <fmt:bundle basename="ar.edu.ubp.das.properties.etiquetas">
        <p><fmt:message key="title_messages" /></p>
        <c:set value="${form.roomType}" var="roomType" ></c:set>
        <c:set value="${form.profileType}" var="profileType" ></c:set>
        <c:set value="${form.messageList}" var="messages" ></c:set>        
        <table border="1">
            <tbody>
                <tr>
                    <th><fmt:message key="label_owner"/></th>
                    <th><fmt:message key="label_created_date"/></th>
                    <th><fmt:message key="label_body"/></th>
                </tr>
        <c:choose>
            <c:when test="${messages != null && messages.size() > 0}">
                <c:forEach items="${messages}" var="message" varStatus="loop">
                    <tr id="${message.id}">
                        <td>${message.owner}</td>
                        <td><fmt:formatDate type="both" dateStyle="short" timeStyle="short" value="${message.datetimeOfCreation}" /></td>
                        <td>${message.body}</td>
                        <c:if test="${roomType.equals('public') && profileType.equals('ADMIN')}" >
                            <td><a href="#" onclick="jsRoom.deleteMessage('${message.id}'); return false;" ><fmt:message key="label_delete" /></a></td>
                        </c:if> 
                    </tr>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <tr id="message_empty">
                    <td colspan="4"><b><fmt:message key="message_empty"/></td>
                </tr>
            </c:otherwise>
        </c:choose>
            </tbody>
        </table>
    </fmt:bundle>
</div>