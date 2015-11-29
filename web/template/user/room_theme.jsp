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
    <link href="css-chat/room.css" rel="stylesheet">

</head>
    <body>
        <%-- NavBar --%>
        <nav class="navbar navbar-inverse navbar-fixed-top">
            <div class="container">
                <!-- Collect the nav links, forms, and other content for toggling -->
                <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                    <ul class="nav navbar-nav">
                        <li class=""><a href="#">Close </a></li>                        
                        <li class=""><a href="#">Leave </a></li>                        
                    </ul>
                </div><!-- /.navbar-collapse -->
            </div><!-- /.container-fluid -->
        </nav> <%-- /.NavBar --%>
        <div class="container">
                <div class="row "> 
                    <div class="col-md-8"> <%-- Messages--%>
                        <div class="panel panel-info">
                            <div class="panel-heading">
                                HISTORIAL DE CHAT
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
                                                    <small class="text-muted">Joaquin | 23rd June at 5:00pm</small>
                                                    <hr />
                                                </div>
                                            </div>
                                        </div>
                                    </li>

                                    <li class="media">
                                        <div class="media-body">
                                            <div class="media">
                                                <a class="pull-left" href="#"><img class="media-object img-circle" src="img/user.png" /></a>

                                                <div class="media-body">
                                                    Donec sit amet ligula enim. Duis vel condimentum massa. Donec sit amet ligula enim. Duis vel condimentum
                                                    massa.Donec sit amet ligula enim. Duis vel condimentum massa. Donec sit amet ligula enim. Duis vel
                                                    condimentum massa.<br />
                                                    <small class="text-muted">Sofi | 23rd June at 5:00pm</small>
                                                    <hr />
                                                </div>
                                            </div>
                                        </div>
                                    </li>


                                </ul>
                            </div>

                            <div class="panel-footer">
                                <div class="input-group">
                                    <input type="text" class="form-control" placeholder="Ingrese el Mensaje" />
                                    <span class="input-group-btn">
                                        <button class="btn btn-info" type="button">ENVIAR</button>
                                    </span>
                                </div>
                            </div>
                        </div>

                    </div> <%-- /.Messages--%>
                    <div class="col-md-4"> 
                        <div class="row">
                            <div class="col-md-12"> <%-- Participants--%>
                                <div class="panel panel-primary">
                                    <div class="panel-heading">
                                        Participants
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
                            </div> <%-- /.Participants --%>
                            <div class="col-md-12"> <%-- All logued participants --%>
                                <div class="panel panel-primary">
                                    <div class="panel-heading">
                                        All logued users
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
                            </div> <%-- All logued participants --%>
                            <div class="col-md-12"> <%-- New private chat --%>
                                <div class="panel panel-primary">
                                    <div class="panel-heading">
                                        Create private chat
                                    </div>
                                    <div class="input-group">
                                        <input type="text" class="form-control" name="" placeholder="Title" required="" autofocus="">
                                        <input type="text" class="form-control" name="" placeholder="invite@email.com" required="">
                                        <input class="form-control btn btn-primary btn-block" type="submit" value="Send" />
                                    </div>
                                </div>
                            </div> <%-- /.New private chat --%>

                        </div>
                    </div>
                </div>

            </div>
            <%-- /Messages and participants --%>
        <script lang="javascript" type="text/javascript" src="./js-chat/jquery.js"></script>    
        <script src="js/bootstrap.min.js"></script>
    </body>
</html>
