<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<html>
<head>
    <title>Account</title>
    <link href="${pageContext.request.contextPath}/layouts/static/styles/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
    <link href="https://fonts.googleapis.com/css?family=Playfair&#43;Display:700,900" rel="stylesheet">
    <link rel="canonical" href="https://getbootstrap.com/docs/4.6/examples/blog/">
    <link href="${contextPath}/layouts/static/styles/account.css" rel="stylesheet">
</head>
<body>

<div class="container">
    <%@ include file = "static/templates/header.jsp" %>

    <h1 class="text-center mt-3">${requestScope.user.firstName} ${requestScope.user.lastName}</h1>
    <p class="lead text-center">${requestScope.user.email}</p>

    <div class="row">
        <div class="col-6">
            <h2 class="mt-4">Active subscriptions</h2>
        </div>
        <div class="col-6 justify-content-end d-flex">
            <h5 class="mt-4 pr-4 align-self-center">Balance:</h5>
            <h1 class="badge badge-dark mt-4 align-self-center" style="font-size: 150%">${requestScope.user.account}$</h1>
        </div>
    </div>


    <p>Attention! Subscription fees are charged at 00:00 on the day the subscription starts.</p>


    <c:forEach var="subscription" items="${requestScope.subscriptions}">
        <div class="row mb-2">
            <div class="col-1 themed-grid-col">${subscription.id}</div>
            <div class="col-2 themed-grid-col">${subscription.publicationName}</div>
            <div class="col-2 themed-grid-col">${subscription.period.name}</div>
            <div class="col-2 themed-grid-col">${subscription.startDate}</div>
            <div class="col-2 themed-grid-col">${subscription.endDate}</div>
            <div class="col-2 themed-grid-col">${subscription.price}$</div>
            <div class="col-1 themed-grid-col">
                <a class="btn btn-sm ml-1 btn-outline-secondary" href="${contextPath}/account">cancer</a>
            </div>
        </div>
    </c:forEach>

</div>
<%@ include file = "static/templates/footer.html" %>

<script
        src="https://code.jquery.com/jquery-3.6.0.min.js"
        integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4="
        crossorigin="anonymous"></script>
<script src="<c:url value="layouts/static/js/bootstrap.bundle.min.js" />"></script>
</body>
</html>
