<%--
  Created by IntelliJ IDEA.
  User: roman
  Date: 29.06.2020
  Time: 18:42
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://example.com/todo/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@include file="/templates/chunks/head-common.jsp"%>
    <title>Simple TODO list</title>
</head>
<body>
    <div class="container">
        <div class="row">
            <c:import url="/templates/chunks/main-menu.jsp">
                <c:param name="user" value="${user}" />
            </c:import>
        </div>
    </div>
    <div class="container">
        <c:if test="${fn:userCanAddItem(user)}">
        <div class="row">
            <a href="<c:url value="/item.do/add"/>" class="btn btn-info add-item">Add new item</a>
        </div>
        </c:if>
        <div class="row">
            <table class="table">
                <thead>
                    <th scope="col">Author</th>
                    <th scope="col">Description</th>
                    <th scope="col">Created</th>
                    <th scope="col">Done</th>
                    <th scope="col">Actions</th>
                </thead>
                <tbody>
                <c:forEach var="item" items="${items}">
                    <tr>
                        <td><a href="<c:url value="/user/${item.author.name}/view"/>"><c:out value="${item.author.name}"/></a></td>
                        <td><c:out value="${item.descr}"/></td>
                        <td>
                            <fmt:formatDate value="${item.created}" type="both" pattern="dd.MM.yyyy HH:mm:ss" />
                        </td>
                        <td>
                            <c:if test="${item.done}">
                                Yes
                            </c:if>
                            <c:if test="${!item.done}">
                                No
                            </c:if>
                        </td>
                        <td>
                            <c:if test="${fn:userCanEditItem(user, item)}">
                                <a href="<c:url value="/item.do/${item.id}/edit" />" class="btn btn-info">Edit</a>
                            </c:if>
                            <c:if test="${fn:userCanDeleteItem(user, item)}">
                                <a href="<c:url value="/item.do/${item.id}/delete" />" class="btn btn-info">Delete</a>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</body>
</html>
