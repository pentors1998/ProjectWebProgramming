<%-- 
    Document   : shop
    Created on : Nov 5, 2018, 11:46:21 AM
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
        <title>LONGDOMDOSIER - Perfume E-commerce | Shop</title>

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
                    <a href="index.jsp"><img src="img/core-img/logo.png" alt=""></a>
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
                    <a href="index.jsp"><img src="img/core-img/logo.png" alt=""></a>
                </div>
                <!-- Amado Nav -->
                <nav class="amado-nav">
                    <ul>
                        <li><a href="index.jsp">Home</a></li>
                        <li class="active"><a href="ProductListServlet?catagories=shop">Shop</a></li>
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
                    <a href="cart.jsp" class="cart-nav"><img src="img/core-img/cart.png" alt=""> Cart <span>(${cart.totalQuantity})</span></a>
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

            <div class="shop_sidebar_area">

                <!-- ##### Single Widget ##### -->
                <div class="widget catagory mb-50">
                    <!-- Widget Title -->
                    <h6 class="widget-title mb-30">Catagories</h6>

                    <!--  Catagories  -->
                    <div class="catagories-menu">
                        <ul>
                            <li><a href="ProductListServlet?catagories=shop">All</a></li>
                            <li><a href="ProductListServlet?catagories=Eau de Toilette">Eau de Toilette</a></li>
                            <li><a href="ProductListServlet?catagories=Eau de Cologne">Eau de Cologne</a></li>
                            <li><a href="ProductListServlet?catagories=Eau de Parfum">Eau de Parfum</a></li>
                            <li><a href="ProductListServlet?catagories=Parfum">Parfum</a></li>
                        </ul>
                    </div>
                </div>

                <div class="widget catagory mb-50">
                    <!-- Widget Title -->
                    <h6 class="widget-title mb-30">Gender</h6>

                    <!--  Gender  -->
                    <div class="catagories-menu">
                        <ul>
                            <li><a href="ProductListServlet?catagories=shop">All</a></li>
                            <li><a href="ProductListSexServlet?sex=Men">Men</a></li>
                            <li><a href="ProductListSexServlet?sex=Women">Women</a></li>
                        </ul>
                    </div>
                </div>

                <div class="widget catagory mb-50">
                    <!-- Widget Title -->
                    <h6 class="widget-title mb-30">Price</h6>

                    <!--  Price  -->
                    <div class="catagories-menu">
                        <ul>
                            <li><a href="ProductListPriceServlet?price=59">$29 - $59</a></li>
                            <li><a href="ProductListPriceServlet?price=99">$60 - $99</a></li>
                            <li><a href="ProductListPriceServlet?price=139">$100 - $139</a></li>
                        </ul>
                    </div>
                </div>
            </div>

            <div class="amado_product_area section-padding-100">
                <div class="container-fluid">

                    <div class="row">
                        <div class="col-12">
                            <div class="product-topbar d-xl-flex align-items-end justify-content-between">
                                <!-- Total Products -->
                                <div class="total-products">
                                    <p>Showing : ${topic}</p>
                                </div>

                            </div>
                        </div>
                    </div>

                    <div class="row">

                        <c:forEach items="${products}" var="p" varStatus="vs">
                            <!-- Single Product Area -->
                            <div class="col-12 col-sm-6 col-md-12 col-xl-6">
                                <div class="single-product-wrapper">
                                    <!-- Product Image -->
                                    <div class="product-img">
                                        <img src="PicProject/${p.productcode}.jpg" alt="">
                                    </div>

                                    <!-- Product Description -->
                                    <div class="product-description d-flex align-items-center justify-content-between">
                                        <!-- Product Meta Data -->
                                        <div class="product-meta-data">
                                            <div class="line"></div>
                                            <p class="product-price">$${p.productprice}</p>
                                            <a href="GetProductServlet?productcode=${p.productcode}">
                                                <h6>${p.productbrandname}</h6>
                                                <h6>${p.productline}</h6>
                                                <h6>${p.producttype.producttype}</h6>
                                                <h6>Gender : ${p.productsex.productsex}</h6>
                                                <label class="label--desc">Size : ${p.productsize} ML.</label>
                                            </a>
                                        </div>
                                        <!-- Ratings & Cart -->
                                        <div class="ratings-cart text-right">
                                            <div class="ratings">
                                                <i class="fa fa-star" aria-hidden="true"></i>
                                                <i class="fa fa-star" aria-hidden="true"></i>
                                                <i class="fa fa-star" aria-hidden="true"></i>
                                                <i class="fa fa-star" aria-hidden="true"></i>
                                                <i class="fa fa-star" aria-hidden="true"></i>
                                            </div>
                                            <div class="cart">
                                                <a href="AddToCartServlet?productcode=${p.productcode}" data-toggle="tooltip" data-placement="left" title="Add to Cart"><img src="img/core-img/cart.png" alt=""></a>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </div>
            </div>
            <!-- ##### Main Content Wrapper End ##### -->

            <!-- ##### Newsletter Area Start ##### -->
            <section class="newsletter-area section-padding-100-0">
                <div class="container">
                    <div class="row align-items-center">
                        <!-- Newsletter Text -->
                        <div class="col-12 col-lg-6 col-xl-7">
                            <div class="newsletter-text mb-100">
                                <h2>Subscribe for a <span>25% Discount</span></h2>
                                <p>Nulla ac convallis lorem, eget euismod nisl. Donec in libero sit amet mi vulputate consectetur. Donec auctor interdum purus, ac finibus massa bibendum nec.</p>
                            </div>
                        </div>
                        <!-- Newsletter Form -->
                        <div class="col-12 col-lg-6 col-xl-5">
                            <div class="newsletter-form mb-100">
                                <form action="#" method="post">
                                    <input type="email" name="email" class="nl-email" placeholder="Your E-mail">
                                    <input type="submit" value="Subscribe">
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
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
                                    <a href="index.html"><img src="img/core-img/logo2.png" alt=""></a>
                                </div>
                                <!-- Copywrite Text -->
                                <p class="copywrite"><!-- Link back to Colorlib can't be removed. Template is licensed under CC BY 3.0. -->
                                    Copyright &copy;<script>document.write(new Date().getFullYear());</script> All rights reserved | This template is made with <i class="fa fa-heart-o" aria-hidden="true"></i> by <a href="https://colorlib.com" target="_blank">Colorlib</a>
                                    <!-- Link back to Colorlib can't be removed. Template is licensed under CC BY 3.0. -->
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
                                                    <a class="nav-link" href="index.html">Home</a>
                                                </li>
                                                <li class="nav-item active">
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
