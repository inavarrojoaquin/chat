<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <title>Bootstrap 101 Template</title>

    <link href="css/bootstrap.min.css" rel="stylesheet">
    

  </head>
  <body>
      
    <nav class="navbar navbar-default" role="navigation">
        <div class="container-fluid">
            <div class="collapse navbar-collapse" id="">
                <ul class="nav nav-pills head-menu" id="tabs">
                    <li class="active"><a href="#iframe" data-toggle="tab" id="inicio" data-url="template/login.jsp">Inicio</a></li>
                    <li><a href="#iframe" data-toggle="tab" id="perfil" data-url="template/user/home.jsp">Perfil</a></li>
                    <li><a href="#iframe" data-toggle="tab" id="configuraciones" data-url="http://mi.ubp.edu.ar">Configuraciones</a></li>
                    <li><a href="#iframe" data-toggle="tab" id="micuenta">Mi cuenta</a></li>
                    <li class="pull-right"><a href="#" data-toggle="tab" id="logout">Logout</a></li>
                </ul>
            </div>
        </div>
    </nav>
      
    <div class="container">
        <div class="tab-content">
            <div class="tab-pane fade in active" id="iframe">
                <iframe src="template/login.jsp" width="100%" height="254"></iframe>
            </div>
<!--            <div class="tab-pane fade" id="perfil"><h4>Texto perfil</h4></div>
            <div class="tab-pane fade" id="configuraciones"><h4>Texto configuraciones</h4></div>
            <div class="tab-pane fade" id="micuenta">
                <iframe src="template/login.jsp"></iframe>
                <%--<jsp:include page="login.jsp" ></jsp:include>--%>
            </div>-->
        </div>
    </div>

    <script src="js/jquery.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
    
    <script>
        var time = 1;
        
        function reload(){
            time += 1;
            var actualId = $("#tabs li.active > a").attr("href");
            var text = $(actualId).children().text("Reload: " + time);
        }
        
        var id = $("#tabs li.active > a").attr("id");
        $("#tabs li").on("click", function(){
            id = $(this).find("li.active > a").attr("id");
            var url = $(this).find("a").data("url");
            $("#iframe iframe").attr("src", url);
            
        });
        

        //setInterval(reload, 3000);
        //setInterval(function(){alert("Hola time");} , 5000);
        
        
    </script>
    
  </body>
</html>
