<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<%@ page import="com.swoqe.newSStand.model.entity.UserRole" %>

<html>
<head>
    <title>Catalog</title>
    <link rel="canonical" href="https://getbootstrap.com/docs/4.6/examples/blog/">
    <link href="<c:url value="layouts/static/styles/bootstrap.min.css"/>" rel="stylesheet">

    <style>
        body {
            margin: 0;
            background-color: #f5f5f5
        }

        .mt-50 {
            margin-top: 50px
        }

        .mb-50 {
            margin-bottom: 50px
        }

        a {
            text-decoration: none !important
        }

        .fa {
            color: red
        }

        .description {
            overflow: hidden;
            text-overflow: ellipsis;
            display: -webkit-box;
            -webkit-line-clamp: 6;
            -webkit-box-orient: vertical;
        }

        .cover_img {
            width: 175px;
            height: 15vw;
            object-fit: cover;
        }

    </style>
    <link href="https://fonts.googleapis.com/css?family=Playfair&#43;Display:700,900" rel="stylesheet">
    <link href="<c:url value="layouts/static/styles/blog.css"/>" rel="stylesheet">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
</head>
<body>
<div class="container">
    <%--    Footer--%>
    <%@ include file="static/templates/header.jsp" %>
    <%--Content--%>
    <div class="container d-flex flex-column justify-content-center mt-50 mb-50">
        <div>Some sorting things will be here!</div>
        <div class="row">
            <div class="col-md-10">
                <%--                Template--%>
                <jsp:useBean id="publications" scope="request" type="java.util.List"/>
                <c:forEach var="item" items="${publications}">
                    <div class="card card-body mt-3">
                        <div class="media align-items-center align-items-lg-start text-center text-lg-left flex-column flex-lg-row">
                            <div class="mr-2 mb-3 mb-lg-0">
                                <img src="<c:url value="layouts/static/pp_covers/${item.getCoverImg().getName()}"/>"
                                     class="cover_img" width="150" height="150" alt="">
                            </div>
                            <div class="media-body">
                                <h6 class="media-title font-weight-semibold">
                                    <a href="#" data-abc="true">${item.getName()}</a>
                                </h6>
                                <ul class="list-inline list-inline-dotted mb-3 mb-lg-2">
                                    <li class="list-inline-item"><a href="#" class="text-muted"
                                                                    data-abc="true">Phones</a></li>
                                    <li class="list-inline-item"><a href="#" class="text-muted"
                                                                    data-abc="true">Mobiles</a></li>
                                </ul>
                                <p class="mb-3 description">${item.getDescription()}</p>
                            </div>
                            <div class="mt-3 mt-lg-0 ml-lg-3 text-center">
                                <h3 class="mb-0 font-weight-semibold">$${item.getShownPrice()}</h3>
                                <h3 class="mb-0 font-weight-semibold">/${item.getShownPeriod()}</h3>
                                <div>
                                    <i class="fa fa-star"></i> <i class="fa fa-star"></i>
                                    <i class="fa fa-star"></i> <i
                                        class="fa fa-star"></i>
                                </div>
                                <div class="text-muted">1985 reviews</div>
                                <button type="button" class="btn btn-dark mt-4 text-white">
                                    subscribe
                                </button>
                            </div>
                        </div>
                    </div>
                </c:forEach>
                <%--    Template END--%>

                <%--Pagination--%>
                <nav aria-label="Page navigation example" class="mt-2">
                    <ul class="pagination justify-content-center">
                        <li class="page-item disabled">
                            <a class="page-link" href="#" tabindex="-1">Previous</a>
                        </li>
                        <li class="page-item"><a class="page-link" href="#">1</a></li>
                        <li class="page-item active"><a class="page-link bg-dark border-dark" href="#">2</a></li>
                        <li class="page-item"><a class="page-link" href="#">3</a></li>
                        <li class="page-item">
                            <a class="page-link" href="#">Next</a>
                        </li>
                    </ul>
                </nav>
            </div>
        </div>
    </div>
</div>
<%@ include file="static/templates/footer.html" %>
<script
        src="https://code.jquery.com/jquery-3.6.0.min.js"
        integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4="
        crossorigin="anonymous"></script>
<script src="<c:url value="layouts/static/js/bootstrap.bundle.min.js" />"></script>
</body>
</html>
