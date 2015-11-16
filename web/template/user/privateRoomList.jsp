<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
    
<div>
    <fmt:bundle basename="ar.edu.ubp.das.properties.etiquetas">
        <p>Private Rooms</p>
        <c:set value="${form.privateRooms}" var="privateRooms" ></c:set>
        <c:set value="${form.profileId}" var="profileId" ></c:set>
        
        <c:if test="${privateRooms.size() > 0}">
                <table border="1">
                    <tr>
                        <th>#</th>
                        <th>Name</th>
                        <th>Type</th>
                    </tr>
                    <c:forEach items="${privateRooms}" var="room" varStatus="loop">
                        <tr>
                            <td>${loop.index + 1}</td>
                            <td><a href="index.jsp?action=Room&roomId=${room.id}&profileId=${profileId}&roomName=${room.name} "  id="${room.id}" >${room.name}</a></td>
                            <td>${room.type}</td>
                            <td><a href="#" data-roomid="${room.id}" >Delete</a></td>
                        </tr>
                    </c:forEach>
                </table>
            </c:if>
            <c:if test="${privateRooms.size() == 0}">
                <b><fmt:message key="private_room_empty"/></b>
            </c:if>
    </fmt:bundle>
</div>