<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
    

<fmt:bundle basename="ar.edu.ubp.das.properties.etiquetas">
    <c:set value="${form.message}" var="message" ></c:set>
    <c:set value="${form.roomType}" var="roomType" ></c:set>
    <c:set value="${form.profileType}" var="profileType" ></c:set>
   
    <c:if test="${message != null}">
        <%-- Send message action --%>
        <tr>
            <input type="hidden" value="${message.id}" name="messageId"/>
            <td>${message.owner}</td>
            <td><fmt:formatDate type="both" dateStyle="short" timeStyle="short" value="${message.datetimeOfCreation}" /></td>
            <td>${message.body}</td>
            <c:if test="${roomType.equals('public') && profileType.equals('ADMIN')}" >
                <td><a href="#" onclick="jsRoom.deleteMessage('${message.id}') return false;" ><fmt:message key="label_delete"/></a></td>
            </c:if>
        </tr>
    </c:if>        

</fmt:bundle>
