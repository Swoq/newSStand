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
    <link href="${contextPath}/layouts/static/styles/pricing.css" rel="stylesheet">
    <style>
        .cover_img {
            width: 350px;
            height: 30vw;
            object-fit: cover;
        }
    </style>
</head>
<body>
<div class="container">
    <%@ include file = "static/templates/header.jsp" %>
        <div class="col-md-12">
            <div class="card card-body mt-2 flex-row mb-3">
                <div class="mr-2 mb-3 mb-lg-0">
                    <img src="${contextPath}/layouts/static/pp_covers/${requestScope.publication.coverImg == null ? "default.svg" : requestScope.publication.coverImg.name}"
                         class="cover_img " width="300" height="300" alt="">
                </div>
                <div class="pricing-header pl-3">
                    <p class="text-muted" style="text-align: end; margin-bottom: 0">Dated: ${requestScope.publication.publicationDate}</p>
                    <h1 class="display-4">${requestScope.publication.name}</h1>
                    <hr/>
                    <ul class="list-inline list-inline-dotted mb-3 mb-lg-2">
                        <c:forEach var="genre" items="${requestScope.publication.genres}">
                            <li class="list-inline-item">
                                <a href="${contextPath}/catalog$genre=${genre.name}" class="text-muted"
                                   data-abc="true">${genre.name}</a>
                            </li>
                        </c:forEach>
                    </ul>
                    <p class="lead">${requestScope.publication.description}</p>

                </div>
            </div>
        </div>

        <div class="container">
            <div class="card-deck mb-3 text-center">
                <c:forEach var="rate" items="${requestScope.rates}">
                        <div class="card mb-4 shadow-sm">
                            <div class="card-header">
                                <h4 class="my-0 font-weight-normal">${rate.period.name.toUpperCase()}</h4>
                            </div>
                            <div class="card-body">
                                <h1 class="card-title pricing-card-title">$${rate.price}
                                    <small class="text-muted">/ ${rate.period.name}</small></h1>
                                <p class="mt-3 mb-4">${rate.period.description}</p>
                                <button type="button" class="btn btn-lg btn-block btn-outline-dark"
                                        data-toggle="modal" data-target="#exampleModal" data-rate="${rate.id}">Subscribe</button>
                            </div>
                        </div>
                </c:forEach>
            </div>
        </div>
</div>

<%@ include file = "static/templates/footer.html" %>

<!-- Modal -->
<div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">Are you sure want to subscribe?</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <form id="rateForm" method="post" action="${contextPath}/subscriptions/add">
                <div class="modal-body">
                    <p>Cost of subscription will be withdrawn from you account.</p>
                    <input type="hidden" id="hiddenRateInput" name="rateId">
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-light" data-dismiss="modal">Close</button>
                    <button type="submit" class="btn btn-dark" >Confirm</button>
                </div>
            </form>
        </div>
    </div>
</div>

<script
        src="https://code.jquery.com/jquery-3.6.0.min.js"
        integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4="
        crossorigin="anonymous"></script>
<script src="<c:url value="layouts/static/js/bootstrap.bundle.min.js" />"></script>

<script>
    $('#exampleModal').on('show.bs.modal', function (event) {
        let button = $(event.relatedTarget)
        let rateId = button.data('rate')
        let modal = $(this)
        modal.find('#hiddenRateInput').attr("value", rateId);
    })
</script>
</body>
</html>
