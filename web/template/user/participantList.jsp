<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
    
<div>
    <fmt:bundle basename="ar.edu.ubp.das.properties.etiquetas">
        <p>Participants</p>
        <c:set value="${form.participantsList}" var="participants" ></c:set>
        
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
                        </tr>
                    </c:forEach>
                </table>
            </c:if>
            <c:if test="${participants.size() == 0}">
                <b><fmt:message key="result_empty"/></b>
            </c:if>
    </fmt:bundle>
</div>