<%-- 
    Document   : MyAccount
    Created on : Oct 24, 2018, 11:51:27 PM
    Author     : Mild-TN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>My Account</h1>
        <h3>Hello : ${account.name}</h3>
        <h3>Balance : ${account.balance}</h3>
        <h4 style="color: ${massage == 'Deposit successful !!' || massage == 'Withdraw successful !!' ? 'green' : 'red'}">${massage}</h4>
        <a href="DepositForward">Deposit</a><br>
        <a href="WithdrawForward">Withdraw</a><br>
        <a href="HistoryForward">History</a><br>
        <br>
        <br>
        <a href="Logout">Logout</a>
    </body>
</html>
