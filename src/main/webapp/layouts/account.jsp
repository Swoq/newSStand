<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<%@ page import="java.time.LocalDate" %>

<html>
<head>
    <title>Account</title>
    <link href="${pageContext.request.contextPath}/layouts/static/styles/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
    <link href="https://fonts.googleapis.com/css?family=Playfair&#43;Display:700,900" rel="stylesheet">
    <link rel="canonical" href="https://getbootstrap.com/docs/4.6/examples/blog/">
    <link href="${contextPath}/layouts/static/styles/grid.css" rel="stylesheet">
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
            <button type="button" class="btn btn-sm mt-4 ml-1 mb-1 btn-outline-secondary align-self-center" data-toggle="modal" data-target="#replenishModal">replenish</button>
        </div>
    </div>

    <c:choose>
        <c:when test="${sessionScope.message != null}">
            <p class="text-success" style="text-align: center"><c:out value="${sessionScope.message}" /></p>
            <c:remove var="message" scope="session" />
        </c:when>
        <c:when test="${sessionScope.error != null}">
            <p class="text-warning" style="text-align: center"><c:out value="${sessionScope.error}" /></p>
            <c:remove var="error" scope="session" />
        </c:when>
    </c:choose>
    <p>Attention! Subscription cancels next day, after confirmation.</p>

    <div class="container mb-4">
        <div class="row mb-2">
            <div class="col-1 themed-grid-col header font-weight-bold">№</div>
            <div class="col-2 themed-grid-col header font-weight-bold">Publication name</div>
            <div class="col-2 themed-grid-col header font-weight-bold">Subscription start</div>
            <div class="col-2 themed-grid-col header font-weight-bold">Subscription end</div>
            <div class="col-2 themed-grid-col header font-weight-bold">Price</div>
            <div class="col-2 themed-grid-col header font-weight-bold">per period</div>
            <div class="col-1 themed-grid-col header font-weight-bold">
                Refuse
            </div>
        </div>

        <c:forEach var="subscription" items="${requestScope.subscriptions}">
            <c:set var="tag" value="" scope="page"/>
            <c:choose>
                <c:when test="${subscription.endDate.compareTo(LocalDate.now().plusWeeks(1)) < 0}">
                    <c:set var="tag" value="bg-warning"/>
                </c:when>
                <c:otherwise>
                    <c:set var="tag" value="bg-info"/>
                </c:otherwise>
            </c:choose>

            <div class="row mb-2 ${tag}">
                <div class="col-1 themed-grid-col">${subscription.id}</div>
                <div class="col-2 themed-grid-col"><a class="text-dark" href="${contextPath}/publication?id=${subscription.publicationId}"><u>${subscription.publicationName}</u></a></div>
                <div class="col-2 themed-grid-col">${subscription.startDate}</div>
                <div class="col-2 themed-grid-col">${subscription.endDate}</div>
                <div class="col-2 themed-grid-col">${subscription.price}$</div>
                <div class="col-2 themed-grid-col">${subscription.period.name}</div>
                <div class="col-1 themed-grid-col">
                    <button class="btn btn-sm ml-1 btn-secondary"
                            data-toggle="modal" data-target="#cancelModal" data-subscription="${subscription.id}">cancel</button>
                </div>
            </div>
        </c:forEach>
    </div>

</div>
<%@ include file = "static/templates/footer.html" %>

<div class="modal fade" id="replenishModal" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <h5 class="modal-title m-2">Replenish your account</h5>
            <form action="${contextPath}/account" method="post">
                <div class="input-group m-1 p-2">
                    <input type="number" class="form-control" placeholder="$ Amount" name="amount" step="0.01" min=0>
                    <div class="input-group-append">
                        <button class="btn btn-outline-secondary" type="submit" id="button-addon2">Submit</button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>

<!-- Modal -->
<div class="modal fade" id="cancelModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">Are you sure want to cancel subscription?</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <form id="subscriptionForm" method="post" action="${contextPath}/subscriptions/delete">
                <div class="modal-body">
                    <p>Cost of subscription <b>won't</b> be returned to your personal account.</p>
                    <input type="hidden" id="hiddenSubscriptionInput" name="subscriptionId">
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-light" data-dismiss="modal">Close</button>
                    <button type="submit" class="btn btn-dark" >Confirm</button>
                </div>
            </form>
        </div>
    </div>
</div>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js" integrity="sha384-9/reFTGAW83EW2RDu2S0VKaIzap3H66lZH81PoYlFhbGU+6BZp6G7niu735Sk7lN" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.min.js" integrity="sha384-+YQ4JLhjyBLPDQt//I+STsc9iw4uQqACwlvpslubQzn4u2UU2UFM80nGisd026JF" crossorigin="anonymous"></script>
<script src="${contextPath}/layouts/static/js/account.js"></script>
<script>

</script>
</body>
</html>
