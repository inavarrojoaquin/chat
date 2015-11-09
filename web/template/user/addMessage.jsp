<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
    

<fmt:bundle basename="ar.edu.ubp.das.properties.etiquetas">
    <c:set value="${form.message}" var="message" ></c:set>

    <c:if test="${message != null}">
        <tr>
            <td>--</td>
            <td>${message.owner}</td>
            <td>${message.datetimeOfCreation}</td>
            <td>${message.body}</td>
        </tr>
    </c:if>

</fmt:bundle>
