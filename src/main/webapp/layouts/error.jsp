<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Error</title>

    <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <script src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

    <link href="${contextPath}/layouts/static/styles/error.css" rel="stylesheet">
</head>
<body>
<div class="container">
    <%@ include file = "static/templates/header.jsp" %>
    <c:set var="code" value="${requestScope.statusCode}"/>
    <div class="d-flex justify-content-center align-items-center" id="main">
        <h1 class="mr-3 pr-3 align-top border-right inline-block align-content-center">${code}</h1>
        <div class="inline-block align-middle">
            <h2 class="font-weight-normal lead" id="desc">
                <c:choose>
                    <c:when test="${code == 404}">
                        The page you requested was not found.
                    </c:when>
                    <c:when test="${code >= 400 && code <= 499}">
                        There are some problems on your side. Contact us and we will try to help you.
                    </c:when>
                    <c:when test="${code >= 500 && code <= 599}">
                        Sorry, we have some problems with our site. We will establish connection as soon as possible.
                    </c:when>
                </c:choose>
            </h2>
        </div>
    </div>
</div>
</body>
</html>