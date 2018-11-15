<%-- 
    Document   : index
    Created on : Nov 5, 2018, 12:29:58 PM
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
        <title>LONGDOMDOSIER - Perfume E-commerce | Home</title>

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
                        <li class="active"><a href="index.jsp">Home</a></li>
                        <li><a href="ProductListServlet?catagories=shop">Shop</a></li>
                        <li><a href="CheckoutPageServlet">Checkout</a></li>
                    </ul>
                </nav>
                <!-- Button Group -->
                <div class="amado-btn-group mt-30 mb-100">
                    <c:choose>
                        <c:when test="${sessionScope.account != null}">
                            <a href="MyAccountPageServlet" class="btn amado-btn mb-15">My Account</a>
                            <a href="MyOrder.jsp" class="btn amado-btn mb-15">My Order</a>
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
                    <a href="#"><i class="fa fa-pinterest" aria-hidden="true"></i></a>
                    <a href="#"><i class="fa fa-instagram" aria-hidden="true"></i></a>
                    <a href="#"><i class="fa fa-facebook" aria-hidden="true"></i></a>
                    <a href="#"><i class="fa fa-twitter" aria-hidden="true"></i></a>
                </div>
            </header>
            <!-- Header Area End -->

            <!-- Product Catagories Area Start -->
            <div class="products-catagories-area clearfix">
                <div class="amado-pro-catagory clearfix">

                    <!-- Single Catagory -->
                    <div class="single-products-catagory clearfix">
                        <a href="GetProductServlet?productcode=1179217">
                            <img src="PicProject/1179217.jpg" alt="">
                            <!-- Hover Content -->
                            <div class="hover-content">
                                <div class="line"></div>
                                <p>From $155</p>
                                <h4>OUD INTENSE</h4>
                            </div>
                        </a>
                    </div>

                    <!-- Single Catagory -->
                    <div class="single-products-catagory clearfix">
                        <a href="GetProductServlet?productcode=1149327">
                            <img src="PicProject/1149327.jpg" alt="">
                            <!-- Hover Content -->
                            <div class="hover-content">
                                <div class="line"></div>
                                <p>From $150</p>
                                <h4>ADDICT</h4>
                            </div>
                        </a>
                    </div>

                    <!-- Single Catagory -->
                    <div class="single-products-catagory clearfix">
                        <a href="GetProductServlet?productcode=1152719">
                            <img src="PicProject/1152719.jpg" alt="">
                            <!-- Hover Content -->
                            <div class="hover-content">
                                <div class="line"></div>
                                <p>From $100</p>
                                <h4>Sì</h4>
                            </div>
                        </a>
                    </div>

                    <!-- Single Catagory -->
                    <div class="single-products-catagory clearfix">
                        <a href="GetProductServlet?productcode=1034511">
                            <img src="PicProject/1034511.jpg" alt="">
                            <!-- Hover Content -->
                            <div class="hover-content">
                                <div class="line"></div>
                                <p>From $99</p>
                                <h4>ALLURE HOMME SPORT</h4>
                            </div>
                        </a>
                    </div>

                    <!-- Single Catagory -->
                    <div class="single-products-catagory clearfix">
                        <a href="GetProductServlet?productcode=1097286">
                            <img src="PicProject/1097286.jpg" alt="">
                            <!-- Hover Content -->
                            <div class="hover-content">
                                <div class="line"></div>
                                <p>From $80</p>
                                <h4>CHANCE EAU TENDRE</h4>
                            </div>
                        </a>
                    </div>

                    <!-- Single Catagory -->
                    <div class="single-products-catagory clearfix">
                        <a href="GetProductServlet?productcode=1159375">
                            <img src="PicProject/1159375.jpg" alt="">
                            <!-- Hover Content -->
                            <div class="hover-content">
                                <div class="line"></div>
                                <p>From $73</p>
                                <h4>DIAMONDS VIOLET</h4>
                            </div>
                        </a>
                    </div>

                    <!-- Single Catagory -->
                    <div class="single-products-catagory clearfix">
                        <a href="GetProductServlet?productcode=1158716">
                            <img src="PicProject/1158716.jpg" alt="">
                            <!-- Hover Content -->
                            <div class="hover-content">
                                <div class="line"></div>
                                <p>From $215</p>
                                <h4>GUCCI OUD</h4>
                            </div>
                        </a>
                    </div>

                    <!-- Single Catagory -->
                    <div class="single-products-catagory clearfix">
                        <a href="GetProductServlet?productcode=1166339">
                            <img src="PicProject/1166339.jpg" alt="">
                            <!-- Hover Content -->
                            <div class="hover-content">
                                <div class="line"></div>
                                <p>From $105</p>
                                <h4>J ADORE</h4>
                            </div>
                        </a>
                    </div>

                    <!-- Single Catagory -->
                    <div class="single-products-catagory clearfix">
                        <a href="GetProductServlet?productcode=1217391">
                            <img src="PicProject/1217391.jpg" alt="">
                            <!-- Hover Content -->
                            <div class="hover-content">
                                <div class="line"></div>
                                <p>From $90</p>
                                <h4>POISON GIRL UNEXPECTED</h4>
                            </div>
                        </a>
                    </div>
                </div>
            </div>
            <!-- Product Catagories Area End -->
        </div>
        <!-- ##### Main Content Wrapper End ##### -->

        <!-- ##### Newsletter Area End ##### -->

        <!-- ##### Footer Area Start ##### -->
        <footer class="footer_area clearfix">
            <div class="container">
                <div class="row align-items-center">
                    <!-- Single Widget Area -->
                    <div class="col-12 col-lg-4">
                        <div class="single_widget_area">
                            <!-- Logo -->
                            <div class="footer-logo mr-50">
                                <a href="index.html"><img src="PicProject/logo.png" style="height: 120px;" alt=""></a>
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
                                            <li class="nav-item active">
                                                <a class="nav-link" href="index.html">Home</a>
                                            </li>
                                            <li class="nav-item">
                                                <a class="nav-link" href="ProductListServlet?catagories=shop">Shop</a>
                                            </li>
                                            <li class="nav-item">
                                                <a class="nav-link" href="cart.html">Cart</a>
                                            </li>
                                            <li class="nav-item">
                                                <a class="nav-link" href="CheckoutServlet">Checkout</a>
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
