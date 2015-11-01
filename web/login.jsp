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
        <title>Login</title>
    </head>
    <body>
        <c:choose>
            <c:when test="${sessionScope.sessionprofile != null}">
                <jsp:forward page="index.jsp?action=LoginProfile" >
                    <jsp:param value="${sessionScope.sessionprofile}" name="profile"></jsp:param>
                </jsp:forward>
            </c:when>
            <c:otherwise>
                <fmt:bundle basename="ar.edu.ubp.das.properties.etiquetas">
                    <div>
                        <fmt:message key="title_singup"/>
                        <form id="signup" method="post" action="index.jsp?action=NewProfile">
                            <table>
                                <tr>                
                                    <th class="ar"><fmt:message key="label_userName"/></th>
                                    <td colspan="5"><input type="text" name="userName" maxlength="100" size="90"/></td>
                                </tr>
                                <tr>
                                    <th class="ar"><fmt:message key="label_password"/></th>
                                    <td colspan="5"><input type="text" name="password" maxlength="100" size="90"/></td>
                                </tr>
                                <tr>
                                    <th class="ar" colspan="6">
                                        <input type="submit" value="Sing up"/>
                                    </th>
                                </tr>
                            </table>
                        </form>
                    </div>
                    <div>
                        <fmt:message key="title_login"/>
                        <form id="login" method="post" action="index.jsp?action=LoginProfile">
                            <table>
                                <tr>                
                                    <th class="ar"><fmt:message key="label_userName"/></th>
                                    <td colspan="5"><input type="text" name="userName" maxlength="100" size="90"/></td>
                                </tr>
                                <tr>
                                    <th class="ar"><fmt:message key="label_password"/></th>
                                    <td colspan="5"><input type="text" name="password" maxlength="100" size="90"/></td>
                                </tr>
                                <tr>
                                    <th class="ar" colspan="6">
                                        <input type="submit" value="Log in" />
                                    </th>
                                </tr>
                            </table>
                        </form>
                    </div>
                </fmt:bundle>
            </c:otherwise>
        </c:choose>
        <c:if test="${response != null}" >
            <c:out value="${response}" ></c:out>
        </c:if>
    </body>
</html>
