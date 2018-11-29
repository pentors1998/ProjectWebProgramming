<%-- 
    Document   : Thanks
    Created on : Nov 24, 2018, 5:38:19 PM
    Author     : Admin
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="description" content="">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <!-- The above 4 meta tags *must* come first in the head; any other head content must come *after* these tags -->

        <!-- Title  -->
        <title>LONGDOMDOSIER - Furniture E-commerce | Checkout</title>

        <!-- Favicon  -->
        <link rel="icon" href="img/core-img/favicon.ico">

        <!-- Core Style CSS -->
        <link rel="stylesheet" href="css/core-style.css">
        <link rel="stylesheet" href="style.css">

    </head>

    <body>
        <!-- Search Wrapper Area Start -->
        <div class="search-wrapper section-padding-100">
            <div class="search-close">
                <i class="fa fa-close" aria-hidden="true"></i>
            </div>
            <div class="container">
                <div class="row">
                    <div class="col-12">
                        <div class="search-content">
                            <form action="FindItemServlet" method="POST">
                                <input type="search" name="search" id="search" placeholder="Type your keyword...">
                                <button type="submit"><img src="img/core-img/search.png" alt=""></button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- Search Wrapper Area End -->

        <!-- ##### Main Content Wrapper Start ##### -->
        <div class="main-content-wrapper d-flex clearfix">

            <!-- Mobile Nav (max width 767px)-->
            <div class="mobile-nav">
                <!-- Navbar Brand -->
                <div class="amado-navbar-brand">
                    <a href="index.jsp"><img src="PicProject/logo.png" alt=""></a>
                </div>
                <!-- Navbar Toggler -->
                <div class="amado-navbar-toggler">
                    <span></span><span></span><span></span>
                </div>
            </div>

            <!-- Header Area Start -->
            <header class="header-area clearfix">
                <!-- Close Icon -->
                <div class="nav-close">
                    <i class="fa fa-close" aria-hidden="true"></i>
                </div>
                <!-- Logo -->
                <div class="logo">
                    <a href="index.jsp"><img src="PicProject/logo.png" alt=""></a>
                </div>
                <!-- Amado Nav -->

                <c:choose>
                    <c:when test="${sessionScope.account != null}">
                        <h6 style="color: #b2b2b2">Hello , ${account.firstname}</h6>
                    </c:when>
                </c:choose>
                <nav class="amado-nav">
                    <ul>
                        <li><a href="index.jsp">Home</a></li>
                        <li><a href="ProductListServlet?catagories=shop">Shop</a></li>
                        <li class="active"><a href="CheckoutPageServlet">Checkout</a></li>
                    </ul>
                </nav>
                <!-- Button Group -->
                <div class="amado-btn-group mt-30 mb-100">
                    <c:choose>
                        <c:when test="${sessionScope.account != null}">
                            <a href="MyAccountPageServlet" class="btn amado-btn mb-15">My Account</a>
                            <a href="OrderServlet" class="btn amado-btn mb-15">My Order</a>
                            <a href="LogoutServlet" class="btn amado-btn active">Logout</a>
                        </c:when>
                        <c:otherwise>
                            <a href="login.jsp" class="btn amado-btn mb-15">Login</a>
                            <a href="register.jsp" class="btn amado-btn active">Register</a>
                        </c:otherwise>
                    </c:choose>
                    <span style="color: #fbb710">${message}</span>
                </div>
                <!-- Cart Menu -->
                <div class="cart-fav-search mb-100">
                    <a href="CartServlet" class="cart-nav"><img src="img/core-img/cart.png" alt=""> Cart <span>(${cart.totalQuantity})</span></a>
                    <a href="#" class="search-nav"><img src="img/core-img/search.png" alt=""> Search</a>
                </div>
                <!-- Social Button -->
                <div class="social-info d-flex justify-content-between">
                    <a href="https://www.sit.kmutt.ac.th/"><i class="fa fa-pinterest" aria-hidden="true"></i></a>
                    <a href="https://www.instagram.com/vnrwt/?fbclid=IwAR0_8yZE7GUD2sknUlwfUNb76iZjeaNEkHogd2xyt2vD9iethVjFX3ycI3I"><i class="fa fa-instagram" aria-hidden="true"></i></a>
                    <a href="https://www.facebook.com/groups/1813788722072822/"><i class="fa fa-facebook" aria-hidden="true"></i></a>
                    <a href="https://twitter.com/login?lang=en"><i class="fa fa-twitter" aria-hidden="true"></i></a>
                </div>
            </header>
            <!-- Header Area End -->

            <div class="cart-table-area section-padding-100">
                <div class="container-fluid">
                    <div class="row">
                        <div class="jumbotron text-xs-center">
                            <h1 class="display-3">Thank You!</h1>
                            <p class="lead"><strong>Please check your order</strong> for view detail that correct about your order.</p>
                            <hr>
                            <p>
                                Having trouble? <a href="">Contact us</a>
                            </p>
                            <p class="lead">
                                <a class="btn btn-primary btn-sm" href="index.jsp" role="button">Continue to homepage</a>
                            </p>
                        </div>
                        
                    </div>
                </div>
            </div>
        </div>
        <!-- ##### Main Content Wrapper End ##### -->


        <!-- ##### Footer Area Start ##### -->
        <footer class="footer_area clearfix">
            <div class="container">
                <div class="row align-items-center">
                    <!-- Single Widget Area -->
                    <div class="col-12 col-lg-4">
                        <div class="single_widget_area">
                            <!-- Logo -->
                            <div class="footer-logo mr-50">
                                <a href="index.jsp"><img src="PicProject/logo.png" style="height: 120px;" alt=""></a>
                            </div>
                            <!-- Copywrite Text -->
                            <p class="copywrite"><!-- Link back to Colorlib can't be removed. Template is licensed under CC BY 3.0. -->
                                Copyright &copy;<script>document.write(new Date().getFullYear());</script> All rights reserved | This template is made with <i class="fa fa-heart-o" aria-hidden="true"></i> by <a href="https://colorlib.com" target="_blank">Colorlib</a>
                                <!-- Link back to Colorlib can't be removed. Template is licensed under CC BY 3.0. --></p>
                        </div>
                    </div>
                    <!-- Single Widget Area -->
                    <div class="col-12 col-lg-8">
                        <div class="single_widget_area">
                            <!-- Footer Menu -->
                            <div class="footer_menu">
                                <nav class="navbar navbar-expand-lg justify-content-end">
                                    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#footerNavContent" aria-controls="footerNavContent" aria-expanded="false" aria-label="Toggle navigation"><i class="fa fa-bars"></i></button>
                                    <div class="collapse navbar-collapse" id="footerNavContent">
                                        <ul class="navbar-nav ml-auto">
                                            <li class="nav-item">
                                                <a class="nav-link" href="index.jsp">Home</a>
                                            </li>
                                            <li class="nav-item">
                                                <a class="nav-link" href="ProductListServlet?catagories=shop">Shop</a>
                                            </li>
                                            <li class="nav-item">
                                                <a class="nav-link" href="CartServlet">Cart</a>
                                            </li>
                                            <li class="nav-item">
                                                <a class="nav-link active" href="CheckoutServlet">Checkout</a>
                                            </li>
                                        </ul>
                                    </div>
                                </nav>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </footer>
        <!-- ##### Footer Area End ##### -->

        <!-- ##### jQuery (Necessary for All JavaScript Plugins) ##### -->
        <script src="js/jquery/jquery-2.2.4.min.js"></script>
        <!-- Popper js -->
        <script src="js/popper.min.js"></script>
        <!-- Bootstrap js -->
        <script src="js/bootstrap.min.js"></script>
        <!-- Plugins js -->
        <script src="js/plugins.js"></script>
        <!-- Active js -->
        <script src="js/active.js"></script>

    </body>

</html>
