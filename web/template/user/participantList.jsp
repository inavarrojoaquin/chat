<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
    
<div>
    <fmt:bundle basename="ar.edu.ubp.das.properties.etiquetas">
        <p>Participants</p>
        <c:set value="${form.participantsList}" var="participants" ></c:set>
        <c:set value="${form.profileType}" var="profileType" ></c:set>
        <c:set value="${form.roomId}" var="roomId" ></c:set> 
        <c:set value="${form.userAccessId}" var="userAccessId" ></c:set>
        
        <c:if test="${participants.size() > 0}">
                <table border="1">
                    <tr>
                        <th>#</th>
                        <th>Name</th>
                    </tr>
                    <c:forEach items="${participants}" var="participant" varStatus="loop">
                        <tr>
                            <td>${loop.index + 1}</td>
                            <td>${participant.login}</td>
                            <c:if test="${profileType.equals('ADMIN')}" >
                                <td><a href="#" data-delete="" data-profileid="${participant.id}" data-roomid="${roomId}" data-useraccessid="${userAccessId}"  >Eject user</a></td>
                            </c:if> 
                        </tr>
                    </c:forEach>
                </table>
            </c:if>
            <c:if test="${participants.size() == 0}">
                <b><fmt:message key="result_empty"/></b>
            </c:if>
    </fmt:bundle>
</div>