<%--
  Created by IntelliJ IDEA.
  User: roman
  Date: 04.07.2020
  Time: 8:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <%@include file="/templates/chunks/head-common.jsp"%>
    <title>Title</title>
</head>
<body>
    <div class="container">
        <div class="row">
            <c:import url="/templates/chunks/main-menu.jsp">
                <c:param name="user" value="${user}" />
            </c:import>
        </div>
    </div>

</body>
</html>
