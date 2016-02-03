<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Home</title>
        <!-- Bootstrap -->
        <link href="/chat/css/bootstrap.min.css" rel="stylesheet">
        <link href="/chat/css/jquery-ui.min.css" rel="stylesheet">
        <link href="/chat/css-chat/home.css" rel="stylesheet">
        
    </head>
    <body>
        <fmt:bundle basename="ar.edu.ubp.das.properties.etiquetas">
            <div class="container">
                <div class="row">
                    <div class="col-md-4 col-md-offset-4"><span class="label label-primary" id="response"></span></div>
                    
                    <div class="col-md-6" id="publicRooms"></div>
                    <div class="col-md-6" id="invitations"></div>
                </div>
            </div>
        </fmt:bundle>
        
        <script lang="javascript" type="text/javascript" src="/chat/js/jquery.min.js"></script>    
        <script lang="javascript" type="text/javascript" src="/chat/js/jquery-ui.min.js"></script>    
        <script lang="javascript" type="text/javascript" src="/chat/js-chat/utils.js"></script>
        <script lang="javascript" type="text/javascript" src="/chat/js-chat/jsPresentation.js"></script>
        <script lang="javascript" type="text/javascript" src="/chat/js-chat/jsHome.js"></script>  
        <script src="/chat/js/bootstrap.min.js"></script>
        
    </body>
</html>
