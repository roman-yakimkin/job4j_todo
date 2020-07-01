<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
    <script src="https://code.jquery.com/jquery-3.4.1.min.js" ></script>
    <link rel="stylesheet" href="<c:url value="/css/todo.css"/>" />
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
