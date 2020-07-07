<%--
  Created by IntelliJ IDEA.
  User: roman
  Date: 01.07.2020
  Time: 12:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <%@include file="/templates/chunks/head-common.jsp"%>
    <title><c:out value="${form_title}"/></title>
</head>
<body>
<div class="container">
    <div class="row">
        <div class="card">
            <div class="card-header">
                <c:out value="${form_title}"/>
            </div>
            <div class="card-body">
                <form action="<c:url value="${form_action}" />" method="post">
                    <div class="form-group">
                        Are you sure that you want to delete this Item ?
                    </div>
                    <div class="form-group">
                        <button type="submit" name="btn-submit" class="btn btn-primary">Delete</button>
                        <a href="<c:url value="/index.jsp"/>" class="btn btn-secondary">Cancel</a>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>
