<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Presentation</title>
        
        <link href="/chat/css/bootstrap.min.css" rel="stylesheet">
        <link href="/chat/css/jquery-ui.min.css" rel="stylesheet">
        
    </head>
    <body>
        <fmt:bundle basename="ar.edu.ubp.das.properties.etiquetas">
            <c:set value="${form.profile}" var="profile" ></c:set>
            <c:set value="${profile.getLogin()}" var="profileLogin" ></c:set>
            
            <input type="hidden" value="${profile.getId()}" name="profileId" />
            
            <nav class="navbar navbar-default" role="navigation"> <%-- NavBar --%>
                <div class="container-fluid">
                    <div class="collapse navbar-collapse">
                        <ul class="nav nav-pills head-menu" id="tabs">
                            <li role="presentation" class="active pull-left" id="home"><a href="#iframe" data-toggle="tab" data-url="template/user/home.jsp" >Home</a></li>
                            <li role="presentation" class="pull-right" id="logout"><a class="noProccess" href="index.jsp?action=LogoutProfile"><fmt:message key="label_logout"/></a></li>
                            <li role="presentation" class="pull-right disabled"><a href="#" onclick="return false;" class="noProccess" >${profileLogin}</a></li>
                        </ul>
                    </div>
                </div>
            </nav> <%-- /.NavBar --%>
            
            <div class="container">
                <div class="tab-content">
                    <div class="tab-pane fade in active" id="iframe">
                        <iframe name="myIframe"
                            sandbox="allow-same-origin allow-scripts allow-modals allow-popups allow-forms allow-pointer-lock allow-top-navigation"
                            src="template/user/home.jsp" frameborder="0" framespacing="0"  border="0"
                            scrolling="auto" style="position:absolute; left:0px; top:50px; width:100%; height:100%;" >
                        </iframe>
                    </div>
                </div>
            </div>
        </fmt:bundle>
        
        <script lang="javascript" type="text/javascript" src="/chat/js/jquery.min.js"></script>    
        <script lang="javascript" type="text/javascript" src="/chat/js/jquery-ui.min.js"></script>
        <script lang="javascript" type="text/javascript" src="/chat/js-chat/jsPresentation.js"></script>
        <script src="/chat/js/bootstrap.min.js"></script>
        
    </body>
</html>
