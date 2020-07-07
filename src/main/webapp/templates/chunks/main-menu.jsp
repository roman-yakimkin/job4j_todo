<%--
  Created by IntelliJ IDEA.
  User: roman
  Date: 06.07.2020
  Time: 13:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<ul class="nav">
    <li class="nav-item">
        <a class="nav-link" href='<c:url value="/index.jsp" />' >TODO List</a>
    </li>
    <c:if test="${user == null}">
        <li class="nav-item">
            <a class="nav-link" href="<c:url value="/user.do/login"/>">Login</a>
        </li>
    </c:if>
    <c:if test="${user != null}">
        <li class="nav-item">
            <a class="nav-link" href="<c:url value="/user.do/${user.id}/view"/>"><c:out value="${user.name}"/></a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="<c:url value="/user.do/logout" />">Logout</a>
        </li>
    </c:if>
</ul>
