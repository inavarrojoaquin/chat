<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Home</title>
        <!-- Bootstrap -->
        <link href="css/bootstrap.min.css" rel="stylesheet">
        <link href="css-chat/home.css" rel="stylesheet">

    </head>
    <body>
        <fmt:bundle basename="ar.edu.ubp.das.properties.etiquetas">
            <%-- NavBar --%>
            <nav class="navbar navbar-inverse navbar-fixed-top">
                <div class="container">
                    <div class="navbar-header">
                        <p class="navbar-text">Home</p>
                    </div>
                    <!-- Collect the nav links, forms, and other content for toggling -->
                    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                        <ul class="nav navbar-nav navbar-right">
                            <li class=""><a href="index.jsp?action=LogoutProfile"><fmt:message key="label_logout"/></a></li>  
                        </ul>
                    </div><!-- /.navbar-collapse -->
                </div><!-- /.container-fluid -->
            </nav> <%-- /.NavBar --%>
            
            <div class="container">
                <div class="row">
                    <c:set value="${form.profile}" var="profile" ></c:set>
                    <input type="hidden" value="${profile.getId()}" name="profileId" />

                    <div class="col-md-6" id="publicRooms"></div>
                    <div class="col-md-6" id="invitations"></div>
                    <span class="label label-info" id="response"></span>
                </div>
            </div>
        </fmt:bundle>
        
        <script lang="javascript" type="text/javascript" src="./js-chat/jquery.js"></script>    
        <script lang="javascript" type="text/javascript" src="./js-chat/utils.js"></script>
        <script lang="javascript" type="text/javascript" src="./js-chat/jsHome.js"></script>  
        <script src="js/bootstrap.min.js"></script>
    </body>
</html>
