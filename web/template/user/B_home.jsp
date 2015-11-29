<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Room</title>
    <!-- Bootstrap -->
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <link href="css-chat/home.css" rel="stylesheet">

</head>
    <body>
        <%-- NavBar --%>
        <nav class="navbar navbar-inverse navbar-fixed-top">
            <div class="container">
                <div class="navbar-header">
                    <p class="navbar-text">Home</p>
                </div>
                <!-- Collect the nav links, forms, and other content for toggling -->
                <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                    <ul class="nav navbar-nav navbar-right">
                        <li class=""><a href="#">Log out </a></li>  
                    </ul>
                </div><!-- /.navbar-collapse -->
            </div><!-- /.container-fluid -->
        </nav> <%-- /.NavBar --%>
        <div class="container">
            <div class="row "> 
                <div class="col-md-6"> <%-- Public rooms--%>
                    <div class="panel panel-primary">
                        <div class="panel-heading">
                            Public Rooms
                        </div>
                        <div class="panel-body">
                            <ul class="media-list">
                                <li class="media">

                                    <div class="media-body">

                                        <div class="media">
                                            <a class="pull-left" href="#">
                                                <img class="media-object img-circle" style="max-height:40px;" src="img/user.png" />
                                            </a>
                                            <div class="media-body" >
                                                <h5>Alex Deo | User </h5>

                                                <small class="text-muted">Active From 3 hours</small>
                                            </div>
                                        </div>

                                    </div>
                                </li>
                                <li class="media">

                                    <div class="media-body">

                                        <div class="media">
                                            <a class="pull-left" href="#">
                                                <img class="media-object img-circle" style="max-height:40px;" src="img/user.png" />
                                            </a>
                                            <div class="media-body" >
                                                <h5>Jhon Rexa | User </h5>

                                                <small class="text-muted">Active From 3 hours</small>
                                            </div>
                                        </div>

                                    </div>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div> <%-- /.Public Rooms --%>
                    
                <div class="col-md-6"> <%-- Invitations --%>
                    <div class="panel panel-info">
                        <div class="panel-heading">
                            INVITACIONES
                        </div>

                        <div class="panel-body modal-body">
                            <ul class="media-list">

                                <li class="media">
                                    <div class="media-body">
                                        <div class="media">
                                            <a class="pull-left" href="#"><img class="media-object img-circle" src="img/user.png" /></a>

                                            <div class="media-body">
                                                Donec sit amet ligula enim. Duis vel condimentum massa. Donec sit amet ligula enim. Duis vel condimentum
                                                massa.Donec sit amet ligula enim. Duis vel condimentum massa. Donec sit amet ligula enim. Duis vel
                                                condimentum massa.<br />
                                                <small class="text-muted">Joaquin | 23rd June at 5:00pm</small> <br /> <br />


                                                <div class="btn-group btn-group-justified" role="group" aria-label="...">
                                                    <a role="button" class="btn btn-default">ACEPTAR</a>
                                                    <a role="button" class="btn btn-default">RECHAZAR</a>

                                                </div>

                                                <hr />
                                            </div>
                                        </div>
                                    </div>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div> <%-- /.Invitations --%>
            </div>
            
        </div>
        <script lang="javascript" type="text/javascript" src="./js-chat/jquery.js"></script>    
        <script src="js/bootstrap.min.js"></script>
    </body>
</html>
