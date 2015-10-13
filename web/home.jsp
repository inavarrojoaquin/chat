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
        <title>JSP Page</title>
    </head>
    <body>
        <fmt:bundle basename="ar.edu.ubp.das.properties.etiquetas">
            <form id="newdata" method="post" action="javascript:void(null)">
                <table border="0">
                    <tr>                
                        <th class="ar"><fmt:message key="label_login"/></th>
                        <td colspan="5"><input type="text" name="login" maxlength="100" size="90"/></td>
                    </tr>
                    <tr>
                        <th class="ar"><fmt:message key="label_password"/></th>
                        <td colspan="5"><input type="text" name="password" maxlength="100" size="90"/></td>
                    </tr>
                    <tr>
                        <th class="ar"><fmt:message key="label_type"/></th>
                        <td colspan="5"><input type="text" name="type" maxlength="100" size="90"/></td>
                    </tr>
                    <tr>
                        <th class="ar" colspan="6">
                            <input type="button" value="Nuevo" onclick="jChat.newProfile()"/>
                            <input type="button" value="Profiles" onclick="jChat.getProfileList()"/>
                            <input type="button" value="Test" onclick="jChat.testRest()"/>
                        </th>
                    </tr>
                </table>
            </form>
        </fmt:bundle>
        <div id="response"></div>
    </body>
</html>
