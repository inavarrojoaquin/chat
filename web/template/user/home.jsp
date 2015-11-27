<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script lang="javascript" type="text/javascript" src="./js/jquery.js"></script>
        <script lang="javascript" type="text/javascript" src="./js/utils.js"></script>
        <script lang="javascript" type="text/javascript" src="./js/jsHome.js"></script>        
        <title>Home</title>
    </head>
    <body>
        <fmt:bundle basename="ar.edu.ubp.das.properties.etiquetas">
            <p><a href="index.jsp?action=LogoutProfile" ><fmt:message key="label_logout"/></a></p>
            
            <c:set value="${form.profile}" var="profile" ></c:set>
            <input type="hidden" value="${profile.getId()}" name="profileId" />
            
            <div id="publicRooms"></div>
            <div id="invitations"></div>
            <div id="response"></div>
            <div id="error"></div>
        </fmt:bundle>
    </body>
</html>
