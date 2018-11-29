<%-- 
    Document   : MyOrder
    Created on : Nov 14, 2018, 3:28:07 PM
    Author     : Admin
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <title>Table V01</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <!--===============================================================================================-->	
        <link rel="icon" type="image/png" href="images/icons/favicon.ico"/>
        <!--===============================================================================================-->
        <link rel="stylesheet" type="text/css" href="vendor/bootstrap/css/bootstrap.min.css">
        <!--===============================================================================================-->
        <link rel="stylesheet" type="text/css" href="fonts/font-awesome-4.7.0/css/font-awesome.min.css">
        <!--===============================================================================================-->
        <link rel="stylesheet" type="text/css" href="vendor/animate/animate.css">
        <!--===============================================================================================-->
        <link rel="stylesheet" type="text/css" href="vendor/select2/select2.min.css">
        <!--===============================================================================================-->
        <link rel="stylesheet" type="text/css" href="vendor/perfect-scrollbar/perfect-scrollbar.css">
        <!--===============================================================================================-->
        <link rel="stylesheet" type="text/css" href="css/util.css">
        <link rel="stylesheet" type="text/css" href="css/main_1.css">
        <!--===============================================================================================-->
    </head>
    <body>

        <div class="limiter">
            <div class="container-table100">
                <div class="wrap-table100">
                    <div class="table100">
                        <table>
                            <thead>
                                <tr class="table100-head">
                                    <th class="column1">Date</th>
                                    <th class="column2">Order ID</th>
                                    <th class="column4">Method</th>
                                    <th class="column5">Quantity</th>
                                    <th class="column6">Total Price</th>
                                </tr>
                            </thead>


                            <tbody>
                                <c:forEach items="${order}" var="o" varStatus="vs">
                                    <tr>
                                        <td class="column1">${o.timedate}</td>
                                        <td class="column2"><a href="OrderDetailServlet?orderId=${o.orderid}">${o.orderid}</a></td>
                                        <td class="column4">${o.method}</td>
                                        <td class="column5">${o.amount}</td>
                                        <td class="column6">$${o.price}</td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                            <a href="index.jsp" style="color: #cccccc">Back</a>
                        </table>
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
