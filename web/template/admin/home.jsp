<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script lang="javascript" type="text/javascript" src="./js/jquery.js"></script>
        <script lang="javascript" type="text/javascript" src="./js/utils.js"></script>
        <script lang="javascript" type="text/javascript" src="./js/chat.js"></script>        
        <title>Home-Admin</title>
    </head>
    <body>
        <fmt:bundle basename="ar.edu.ubp.das.properties.etiquetas">
            <a href="index.jsp?action=LogoutProfile" >Logout</a>
            <c:if test="${!empty requestScope.form.profile}" >
                <c:set value="${form.profile}" var="profile" ></c:set>
                <c:out value="Datos-Admin:"></c:out>
                <c:out value="${profile.getId()}"></c:out>
                <c:out value="${profile.getLogin()}"></c:out>
            </c:if>
        </fmt:bundle>
    </body>
</html>
