<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
    
<div>
    <fmt:bundle basename="ar.edu.ubp.das.properties.etiquetas">
        <p>Public Rooms</p>
        <c:set value="${form.publicRooms}" var="publicRooms" ></c:set>
        <c:set value="${form.profileId}" var="profileId" ></c:set>
        <c:set value="${form.profileType}" var="profileType" ></c:set>
        
        <c:if test="${publicRooms.size() > 0}">
                <table border="1">
                    <tr>
                        <th>#</th>
                        <th>Name</th>
                        <th>Type</th>
                        <th>UserCant</th>
                    </tr>
                    <c:forEach items="${publicRooms}" var="room" varStatus="loop">
                        <tr>
                            <td>${loop.index + 1}</td>
                            <td><a href="index.jsp?action=Room&roomId=${room.key.id}&profileId=${profileId}&roomName=${room.key.name}&profileType=${profileType} "  id="${room.key.id}" >${room.key.name}</a></td>
                            <td>${room.key.type}</td>
                            <td>${room.value}</td>
                        </tr>
                    </c:forEach>
                </table>
            </c:if>
            <c:if test="${publicRooms.size() == 0}">
                <b><fmt:message key="public_room_empty"/></b>
            </c:if>
    </fmt:bundle>
</div>