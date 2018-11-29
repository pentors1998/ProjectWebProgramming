<%-- 
    Document   : OrderDetail
    Created on : Nov 28, 2018, 9:46:51 PM
    Author     : Admin
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <title>Table V02</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <!--===============================================================================================-->	
        <link rel="icon" type="image/png" href="table2/images/icons/favicon.ico"/>
        <!--===============================================================================================-->
        <link rel="stylesheet" type="text/css" href="table2/vendor/bootstrap/css/bootstrap.min.css">
        <!--===============================================================================================-->
        <link rel="stylesheet" type="text/css" href="table2/fonts/font-awesome-4.7.0/css/font-awesome.min.css">
        <!--===============================================================================================-->
        <link rel="stylesheet" type="text/css" href="table2/vendor/animate/animate.css">
        <!--===============================================================================================-->
        <link rel="stylesheet" type="text/css" href="table2/vendor/select2/select2.min.css">
        <!--===============================================================================================-->
        <link rel="stylesheet" type="text/css" href="table2/vendor/perfect-scrollbar/perfect-scrollbar.css">
        <!--===============================================================================================-->
        <link rel="stylesheet" type="text/css" href="table2/css/util.css">
        <link rel="stylesheet" type="text/css" href="table2/css/main.css">
        <!--===============================================================================================-->
    </head>
    <body>

        <div class="limiter">
            <div class="container-table100">
                <div class="wrap-table100">
                    <div class="table">

                        <div class="row header">
                            <div class="cell">
                                Image
                            </div>
                            <div class="cell">
                                Product Code
                            </div>
                            <div class="cell">
                                Quantity
                            </div>
                            <div class="cell">
                                Price
                            </div>
                        </div>

                        <c:forEach items="${orderDetail.historyorderdetailList}" var="oD" varStatus="vs">
                            <div class="row">
                                <div class="cell" data-title="Image">
                                    <a href="GetProductServlet?productcode=${oD.productcode.productcode}"><img src="PicProject/${oD.productcode.productcode}.jpg" alt="" style="width: 115px;"></a>
                                </div>
                                <div class="cell" data-title="Product Code">
                                    ${oD.productcode.productcode}
                                </div>
                                <div class="cell" data-title="Quantity">
                                    ${oD.productquantity}
                                </div>
                                <div class="cell" data-title="Price">
                                    $${oD.productprice}
                                </div>
                            </div>
                        </c:forEach>
                        
                    </div>
                </div>
            </div>
        </div>
        




        <!--===============================================================================================-->	
        <script src="vendor/jquery/jquery-3.2.1.min.js"></script>
        <!--===============================================================================================-->
        <script src="vendor/bootstrap/js/popper.js"></script>
        <script src="vendor/bootstrap/js/bootstrap.min.js"></script>
        <!--===============================================================================================-->
        <script src="vendor/select2/select2.min.js"></script>
        <!--===============================================================================================-->
        <script src="js/main.js"></script>

    </body>
</html>