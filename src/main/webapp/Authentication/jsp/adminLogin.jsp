<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Admin Login</title>
    <link rel="stylesheet" href="../css/authStyles.css">
</head>
<body>
<%
    String msg = request.getParameter("msg");
    String successParam = request.getParameter("success");
    boolean isSuccess = "true".equalsIgnoreCase(successParam);
%>
<div class="auth-container">
    <h2>Admin Login</h2>
    <form method="post" action="<%=request.getContextPath()%>/admin-login">
        <label for="full_name">Full Name</label>
        <input type="text" id="full_name" name="full_name" required />

        <label for="password">Password</label>
        <input type="password" id="password" name="password" required />

        <button type="submit">Login as Admin</button>
    </form>
    <div class="message<%= isSuccess ? " success" : "" %>">
        <%= msg != null ? msg : "" %>
    </div>
    <div style="text-align:center; margin-top:18px;">
        <a href="adminRegister.jsp" style="color:var(--accent-gold);text-decoration:none;">Register as Admin</a>
    </div>
</div>
</body>
</html> 