<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <fmt:bundle basename="ar.edu.ubp.das.properties.etiquetas">
            <br/>
            <c:if test="${requestScope.form.profileList.size() > 0}">
                <table border="0" width="100%">
                    <colgroup>
                        <col with="20%"/>
                        <col with="40%"/>
                        <col with="40%"/>
                    </colgroup>
                    <tr>
                        <th class="al">Id</th>
                        <th class="al">Login</th>
                        <th class="al">Password</th>
                        <th class="al">Type</th>
                    </tr>
                    <c:forEach items="${requestScope.form.profileList}" var="profile">
                        <tr>
                            <td>${profile.getItem('id')}</td>
                            <td>${profile.getItem('login')}</td>
                            <td>${profile.getItem('password')}</td>
                            <td>${profile.getItem('type')}</td>
                        </tr>
                    </c:forEach>
                </table>
            </c:if>
            <c:if test="${requestScope.form.profileList.size() == 0}">
                <b><fmt:message key="result_empty"/></b>
            </c:if>
        </fmt:bundle>
    </body>
</html>
