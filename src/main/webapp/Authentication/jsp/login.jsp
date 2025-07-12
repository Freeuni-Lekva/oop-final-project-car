<%@ page import="org.example.car.User.Model.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>User Login</title>
    <link rel="stylesheet" href="../css/authStyles.css">
</head>
<body>
<%
    String msg = request.getParameter("msg");
    String successParam = request.getParameter("success");
    boolean isSuccess = "true".equalsIgnoreCase(successParam);

    User user = (User) session.getAttribute("user");
    Boolean isLoggedIn = (user != null);
%>

<div class="navbar">
    <div class="nav-left">
        <a href="${pageContext.request.contextPath}/HPcontroller" class="nav-btn">Home</a>
        <% if (isLoggedIn) { %>
        <a href="${pageContext.request.contextPath}/userProfile" class="nav-btn">Profile</a>
        <%if(user.isAdmin()){%>
        <a href="${pageContext.request.contextPath}/admin-dashboard" class="nav-btn">Admin Dashboard</a>
        <% } }
        else { %>
        <a href="${pageContext.request.contextPath}/login" class="nav-btn">Log In</a>
        <% } %>
    </div>
</div>

<div class="auth-wrapper">
    <div class="auth-container">
        <div class="auth-header">
            <h2>Welcome Back</h2>
            <p>Sign in to access your account and manage bookings</p>
        </div>
    <form method="post" action="<%=request.getContextPath()%>/login">
        <label for="full_name">Full Name</label>
        <input type="text" id="full_name" name="full_name" required />

        <label for="password">Password</label>
        <input type="password" id="password" name="password" required />

        <input type="hidden" name="returnUrl" value="<%= request.getParameter("returnUrl") != null ? request.getParameter("returnUrl") : "" %>" />
        <input type="hidden" name="prevPage" value="<%= request.getParameter("prevPage") != null ? request.getParameter("prevPage") : "" %>" />

        <button type="submit" class="btn">Login</button>


        <div class="message<%= isSuccess ? " success" : "" %>">
                <%= msg != null ? msg : "" %>
    </form>
        <div class="auth-footer">
            <a href="register.jsp" style="color:var(--accent-gold);text-decoration:none;">Don't have an account? Register</a>
        </div>
    </div>


    </div>
    </div>





</div>
</body>
</html>