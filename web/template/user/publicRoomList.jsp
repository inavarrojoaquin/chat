<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
    
<div>
    <fmt:bundle basename="ar.edu.ubp.das.properties.etiquetas">
        <c:set value="${form.publicRooms}" var="publicRooms" ></c:set>
        <c:set value="${form.profileId}" var="profileId" ></c:set>
        <c:set value="${form.publicParticipateRooms}" var="publicParticipateRooms" ></c:set>
        
        <c:choose>
            <c:when test="${publicRooms != null && !empty publicRooms}" >
                <p><fmt:message key="title_public_room"/></p>
                <table border="1">
                    <tr>
                        <th><fmt:message key="label_name"/></th>
                        <th><fmt:message key="label_user_cant"/></th>
                    </tr>
                    <c:forEach items="${publicRooms}" var="room" varStatus="loop">
                        <tr>
                            <td><a href="index.jsp?action=Room&roomId=${room.getItem("id")}&profileId=${profileId}&roomName=${room.getItem("name")}" >${room.getItem("name")}</a></td>
                            <td>${room.getItem("cant_user")}</td>
                        </tr>
                    </c:forEach>
                </table>
            </c:when>
            <c:when test="${publicParticipateRooms != null && !empty publicParticipateRooms}">
                <p><fmt:message key="title_participate_room"/></p>
                <table border="1">
                    <tr>
                        <th><fmt:message key="label_name"/></th>
                    </tr>
                    <c:forEach items="${publicParticipateRooms}" var="room" varStatus="loop">
                        <tr>
                            <td><a href="index.jsp?action=Room&roomId=${room.id}&profileId=${profileId}&roomName=${room.name} ">${room.name}</a></td>
                        </tr>
                    </c:forEach>
                </table>
            </c:when>
            <c:otherwise>
                <b><fmt:message key="public_room_empty"/></b>
            </c:otherwise>
        </c:choose>
        
    </fmt:bundle>
</div>