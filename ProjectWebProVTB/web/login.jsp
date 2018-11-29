<%-- 
    Document   : login
    Created on : Nov 2, 2018, 10:01:02 AM
    Author     : INT303
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
    <head>
        <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
        <link rel="stylesheet" href="loginstyle.css" type="text/css"/>

        <script src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
        <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    </head>
    <section class="login-block">
        <div class="container">
            <div class="row">
                <div class="col-md-4 login-sec">
                    <h2 class="text-center">Login Now</h2>
                    <form action="LoginServlet" class="login-form" method="POST">
                        <div class="form-group">
                            <label for="exampleInputEmail1" class="text-uppercase">Email</label>
                            <input type="email" name="email" class="form-control" placeholder="Your email.." required>
                        </div>
                        <div class="form-group">
                            <label for="exampleInputPassword1" class="text-uppercase">Password</label>
                            <input type="password" name="password" class="form-control" placeholder="Your password.." required>
                        </div>  
                        <span style="color: red">${message}</span>

                        <div class="form-check">

                            <button type="submit" class="btn btn-login float-right">Login</button>
                        </div>

                    </form>
                    <form action="index.jsp" method="POST">
                        <button type="submit" class="btn btn-login float-left" style="background-color: black">Back</button>
                    </form>
                </div>
                <div class="col-md-8 banner-sec">
                    <div id="carouselExampleIndicators" class="carousel slide" data-ride="carousel">
                        <ol class="carousel-indicators">
                            <li data-target="#carouselExampleIndicators" data-slide-to="0" class="active"></li>
                            <li data-target="#carouselExampleIndicators" data-slide-to="1"></li>
                            <li data-target="#carouselExampleIndicators" data-slide-to="2"></li>
                        </ol>
                        <div class="carousel-inner" role="listbox">
                            <div class="carousel-item active">
                                <img src="images/1.jpg" alt="First slide" width="120%">
                                <div class="carousel-caption d-none d-md-block">
                                    <div class="banner-text">
                                        <h2>CHANEL</h2>
                                        <p>New Collection</p>
                                    </div>	
                                </div>
                            </div>
                            <div class="carousel-item">
                                <img class="d-block img-fluid" src="https://images.pexels.com/photos/7097/people-coffee-tea-meeting.jpg" alt="First slide">
                                <div class="carousel-caption d-none d-md-block">
                                    <div class="banner-text">
                                        <h2>This is Heaven</h2>
                                        <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation</p>
                                    </div>	
                                </div>
                            </div>
                            <div class="carousel-item">
                                <img class="d-block img-fluid" src="https://images.pexels.com/photos/872957/pexels-photo-872957.jpeg" alt="First slide">
                                <div class="carousel-caption d-none d-md-block">
                                    <div class="banner-text">
                                        <h2>This is Heaven</h2>
                                        <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation</p>
                                    </div>	
                                </div>
                            </div>
                        </div>	   

                    </div>
                </div>
            </div>
    </section>
</html>
