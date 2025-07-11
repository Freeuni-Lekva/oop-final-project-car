<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Admin Dashboard</title>
</head>
<body>
<h1>Welcome, Admin ${admin.full_name}</h1>

<form action="${pageContext.request.contextPath}/HPcontroller" method="get">
    <button type="submit">See Car Collection</button>
</form>

<h2>All Users</h2>
<table border="1">
    <tr>
        <th>ID</th>
        <th>Full Name</th>
        <th>Actions</th>
    </tr>
    <c:forEach var="u" items="${users}">
        <tr>
            <td>${u.id}</td>
            <td>${u.full_name}</td>
            <td>
                <form action="${pageContext.request.contextPath}/userProfile" method="get" style="display:inline;">
                    <input type="hidden" name="userId" value="${u.id}" />
                    <button type="submit">See More</button>
                </form>

                <form action="${pageContext.request.contextPath}/deleteUser" method="post" style="display:inline;">
                    <input type="hidden" name="userId" value="${u.id}" />
                    <button type="submit">Delete User</button>
                </form>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
