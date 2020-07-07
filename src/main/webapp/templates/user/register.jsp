<%--
  Created by IntelliJ IDEA.
  User: roman
  Date: 04.07.2020
  Time: 8:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <%@include file="/templates/chunks/head-common.jsp"%>
    <title>Register new user</title>
</head>
<body>
<script>
    function validate() {
        let elements = $("#form-reg .form-control");
        for (let i = 0; i < elements.length; i++) {
            if (elements[i].value === "") {
                alert("Field \"" + elements[i].title + "\" shouldn't be empty");
                return false;
            }
        }
        return true;
    }
</script>
<div class="container pt-3">
    <div class="row">
        <c:if test="${message != null}">
            <div class="alert alert-dark" role="alert">
                <c:out value="${message}" />
            </div>
        </c:if>
        <div class="card" style="width: 100%">
            <div class="card-header">
                New user
            </div>
            <div class="card-body">
                <form id="form-reg" action="<c:url value="${form_action}" />" method="post">
                    <div class="form-group">
                        <label>User name</label>
                        <input type="text" class="form-control" name="name" title="User name">
                    </div>
                    <div class="form-group">
                        <label>e-mail</label>
                        <input type="text" class="form-control" name="email" title="e-mail">
                    </div>
                    <div class="form-group">
                        <label>Password</label>
                        <input type="password" class="form-control" name="password">
                    </div>
                    <button type="submit" onclick="return validate()" class="btn btn-primary">Register</button>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>
