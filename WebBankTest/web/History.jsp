<%-- 
    Document   : History
    Created on : Oct 26, 2018, 3:03:07 AM
    Author     : Mild-TN
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>History</h1>
        <table>
            <tr>
                <th>Count</th>
                <th>Time</th>
                <th>Method</th>
                <th>Money</th>
                <th>Amount</th>
            </tr>
            <c:forEach items="${histories}" var="h" varStatus="hc">
                <tr>
                    <td>${hc.count}</td>
                    <td>${h.createdate}</td>
                    <td>${h.method}</td>
                    <td>${h.balance}</td>
                    <td>${h.amount}</td>
                </tr> 
            </c:forEach>
        </table>
    </body>
</html>
