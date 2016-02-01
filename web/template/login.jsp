<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Login</title>
    <!-- Bootstrap -->
    <link href="/chat/css/bootstrap.min.css" rel="stylesheet">
    <link href="/chat/css-chat/signin.css" rel="stylesheet">
    
  </head>
  <body>
        <c:choose>
          <c:when test="${sessionScope.profile != null}">
              <jsp:forward page="/index.jsp?action=LoginProfile" >
                  <jsp:param value="${sessionScope.profile}" name="profile"></jsp:param>
              </jsp:forward>
          </c:when>
          <c:otherwise>
              <fmt:bundle basename="ar.edu.ubp.das.properties.etiquetas">
                  <div class="container">
                      <%-- sign in  --%>
                      <div class="row"> 
                          <div class="col-md-6 col-md-offset-3">
                              <form class="form-signin" id="login" method="post" action="index.jsp?action=LoginProfile">
                                  <h3 class="form-signin-heading"><fmt:message key="title_login"/></h3>
                                  <input type="text" class="form-control" name="userName" placeholder="Email address" required="" autofocus="">
                                  <input type="password" class="form-control" name="password" placeholder="Password" required="">
                                  <input class="form-control btn btn-primary btn-block" type="submit" value="Sign in" />
                              </form>
                          </div>
                      </div> 
                      <%-- /sign in  --%>
                      <%-- get response  --%>
                      <c:if test="${response != null}" >
                            <div class="col-md-6 col-md-offset-3">
                                <div class="alert alert-success" role="alert"><c:out value="${response}" ></c:out></div>
                            </div>
                      </c:if>
                      <%-- /get response  --%>
                      <%-- get response  --%>
                      <c:if test="${error != null}" >
                            <div class="col-md-6 col-md-offset-3">
                                <div class="alert alert-danger" role="alert"><c:out value="${error}" ></c:out></div>
                            </div>
                      </c:if>
                      <%-- /get response  --%>
                      <%-- sign up  --%>
                      <div class="row" style="padding: 70px;">
                          <div class="col-md-6 col-md-offset-3">
                              <h4 style="border-bottom: 1px solid #c5c5c5;">
                                  <i class="glyphicon glyphicon-user"></i>
                                  <fmt:message key="title_singup"/>
                              </h4>
                              <div style="padding: 20px;" id="form-signup">
                                  <form role="form" id="signup" method="post" action="index.jsp?action=NewProfile">
                                      <fieldset>
                                          <div class="form-group input-group">
                                              <span class="input-group-addon">@</span>
                                              <input class="form-control" placeholder="someone@gmail.com" name="userName" type="email" required="" autofocus="">
                                          </div>
                                          <div class="form-group input-group">
                                              <span class="input-group-addon">
                                                  <i class="glyphicon glyphicon-lock"></i>
                                              </span>
                                              <input class="form-control" placeholder="Password" name="password" type="password" value="" required="">
                                          </div>
                                          <div class="form-group input-group">
                                              <span class="input-group-addon">
                                                  <i class="glyphicon glyphicon-lock"></i>
                                              </span>
                                              <input class="form-control" placeholder="Confirm password" name="confirmPassword" type="password" value="" required="">
                                          </div>
                                          <div class="form-group">
                                              <input class="form-control btn btn-primary btn-block" type="submit" value="Sign up"/>
                                          </div>
                                      </fieldset>
                                  </form>
                              </div>
                          </div>
                      </div>
                      <%-- /sign up  --%>
                  </div>
              </fmt:bundle>
          </c:otherwise>
        </c:choose>
    
    <script lang="javascript" type="text/javascript" src="/chat/js/jquery.min.js"></script>    
    <script src="/chat/js/bootstrap.min.js"></script>
    
  </body>
</html>