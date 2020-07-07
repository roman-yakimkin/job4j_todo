<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@include file="/templates/chunks/head-common.jsp"%>
    <script src="<c:url value="/js/todo.js"/>"></script>
    <title><c:out value="${form_title}"/></title>
</head>
<body>
    <div class="container">
        <div class="row">
            <div id="messages" class="alert alert-danger">
            </div>
            <div class="card">
                <div class="card-header">
                    <c:out value="${form_title}"/>
                </div>
                <div class="card-body">
                    <form action="<c:url value="${form_action}" />" method="post">
                        <div class="form-group">
                            <label for="descr">Description</label>
                            <textarea id="descr" name="descr" class="form-control"><c:out value="${item.descr}"/></textarea>
                        </div>
                        <div class="form-group">
                            <label for="created">Created</label>
                            <input type="datetime" name="created" id="created" class="form-control" value="<c:out value="${item.created}"/>" />
                        </div>
                        <div class="form-group">
                            <label for="done"><input type="checkbox" id="done" name="done" <c:if test="${item.done}">checked</c:if> />Done</label>
                        </div>
                        <div class="form-group">
                            <button type="submit" name="btn-submit" class="btn btn-primary">Save</button>
                            <a href="<c:url value="/index.jsp"/>" class="btn btn-secondary">Cancel</a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
