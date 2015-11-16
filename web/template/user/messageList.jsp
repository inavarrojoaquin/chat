<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
    
<div>
    <fmt:bundle basename="ar.edu.ubp.das.properties.etiquetas">
        <p>Messages</p>
        <c:set value="${form.roomType}" var="roomType" ></c:set>
        <c:set value="${form.profileType}" var="profileType" ></c:set>
        <c:set value="${form.messageList}" var="messages" ></c:set>        
        <table border="1">
            <tbody>
                <tr>
                    <th>#</th>
                    <th>Owner</th>
                    <th>Created</th>
                    <th>Body</th>
                </tr>
        <c:choose>
            <c:when test="${messages != null && messages.size() > 0}">
                <c:forEach items="${messages}" var="message" varStatus="loop">
                    <tr>
                        <input type="hidden" value="${message.id}" name="messageId"/>
                        <td>${loop.index}</td>
                        <td>${message.owner}</td>
                        <td>${message.datetimeOfCreation}</td>
                        <td>${message.body}</td>
                        <c:if test="${roomType.equals('public') && profileType.equals('ADMIN')}" >
                            <td><a href="#" data-delete="" data-id="${message.id}" >Delete</a></td>
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