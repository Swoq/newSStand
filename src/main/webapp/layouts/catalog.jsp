<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<html>
<head>
    <title>Catalog</title>
    <link rel="canonical" href="https://getbootstrap.com/docs/4.6/examples/blog/">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css" integrity="sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l" crossorigin="anonymous">
    <link href="https://fonts.googleapis.com/css?family=Playfair&#43;Display:700,900" rel="stylesheet">
    <link href="${contextPath}/layouts/static/styles/blog.css" rel="stylesheet">
    <link href="${contextPath}/layouts/static/styles/new_catalog.css" rel="stylesheet">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/@dashboardcode/bsmultiselect@1.0.0/dist/css/BsMultiSelect.bs4.min.css">

</head>
<body>
<div class="container">
    <%@ include file="static/templates/header.jsp" %>

    <div class="container d-flex flex-column justify-content-center mt-50 mb-50">
        <form action="${contextPath}/catalog" method="get" class="mb-0">
            <div class="form-row">
                <div class="col-sm-1 mb-3">
                    <label for="records">Shown:</label>
                    <select class="form-control" id="records" name="shown">
                        <option value="5" selected>5</option>
                        <option value="10">10</option>
                        <option value="15">15</option>
                    </select>
                </div>
                <div class="col-sm-3 mb-3">
                    <label for="genreMultiSelect">Genres:</label>
                    <select class="custom-select form-control" name="genres[]" id="genreMultiSelect" multiple="multiple"
                            style="display: none" >
                        <c:forEach var="genre" items="${requestScope.genres}">
                            <option value="${genre.id}">${genre.name}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="col-sm-3 mb-3">
                    <label for="sortBy">Sort By:</label>
                    <select class="form-control" id="sortBy" name="sortBy">
                        <option value="name" selected>Title</option>
                        <option value="price">Price</option>
                        <option value="date">Publishing date</option>
                        <option value="date_before" disabled>Published before</option>
                        <option value="date_after" disabled>Published after</option>
                    </select>
                </div>
                <div class="col-sm-3 mb-3" id="hiddenBlock">
                    <label for="hiddenDate">Date:</label>
                    <input class="form-control" name="filterDate" type="date" id="hiddenDate" disabled>
                </div>
                <div class="col-sm-1 mb-3">
                    <label for="direction">Direction: </label>
                    <select class="form-control" id="direction" name="direction">
                        <option value="ASC" selected>ASC</option>
                        <option value="DESC">DESC</option>
                    </select>
                </div>
                <div class="col-sm-1 mb-3 align-text-bottom">
                    <label for="btnFilterSubmit">Apply Filter</label>
                    <input type="submit" id="btnFilterSubmit" class="btn btn-outline-dark">
                </div>
            </div>
            <div class="form-row d-flex justify-content-end">
                <div class="input-group col-sm-3 justify-content-end pr-2">
                    <div class="form-outline">
                        <input type="search" name="search" id="form1" class="form-control" placeholder="Search by Title" />
                    </div>
                    <button type="submit" class="btn btn-outline-dark">
                        <i class="fa fa-search" aria-hidden="true" ></i>
                    </button>
                </div>
            </div>
        </form>

        <div class="row">
            <div class="col-md-12">
                <c:forEach var="item" items="${requestScope.publications}">
                    <div class="card card-body mt-3">
                        <div class="media align-items-center align-items-lg-start text-center text-lg-left flex-column flex-lg-row">
                            <div class="mr-2 mb-3 mb-lg-0">
                                <img src="${contextPath}/layouts/static/pp_covers/${item.coverImg == null ? "default.svg" : item.coverImg.getName()}"
                                     class="cover_img" width="150" height="150" alt="">
                            </div>
                            <div class="media-body">
                                <h6 class="media-title font-weight-semibold">
                                    <a href="${contextPath}/publication?id=${item.id}" data-abc="true">${item.name}</a>
                                </h6>
                                <ul class="list-inline list-inline-dotted mb-3 mb-lg-2">
                                    <c:forEach var="genre" items="${item.genres}">
                                        <li class="list-inline-item">
                                            <a href="${contextPath}/catalog?genres%5B%5D=${genre.id}" class="text-muted"
                                               data-abc="true">${genre.name}</a>
                                        </li>
                                    </c:forEach>
                                </ul>
                                <p class="mb-3 description">${item.description}</p>
                                <p class="text-muted">Dated: ${item.publicationDate}</p>

                            </div>
                            <div class="mt-3 mt-lg-0 ml-lg-3 text-center">
                                <h3 class="mb-0 font-weight-semibold">$${item.shownPrice}</h3>
                                <h3 class="mb-0 font-weight-semibold">/${item.shownPeriod}</h3>
                                <div>
                                    <i class="fa fa-star"></i> <i class="fa fa-star"></i>
                                    <i class="fa fa-star"></i> <i class="fa fa-star"></i>
                                </div>
                                <div class="text-muted">1985 reviews</div>
                                <a type="button" class="btn btn-dark mt-4 text-white" href="${contextPath}/publication?id=${item.id}">
                                    subscribe
                                </a>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>

        <nav aria-label="Navigation for countries" class="mt-3 d-flex justify-content-center">
            <ul class="pagination">
                <c:if test="${requestScope.currentPage != 1}">
                    <li class="page-item">
                        <a class="page-link text-dark"
                           href="<my:replaceParam name='page' value='${requestScope.currentPage-1}' />">Previous</a>
                    </li>
                </c:if>

                <c:forEach begin="1" end="${requestScope.noOfPages}" var="i">
                    <c:choose>
                        <c:when test="${requestScope.currentPage eq i}">
                            <li class="page-item active"><a class="page-link bg-dark border-dark">
                                    ${i} <span class="sr-only">(current)</span></a>
                            </li>
                        </c:when>
                        <c:otherwise>
                            <li class="page-item">
                                <a class="page-link text-dark" href="<my:replaceParam name='page' value='${i}' />">${i}</a>
                            </li>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>

                <c:if test="${requestScope.currentPage lt requestScope.noOfPages}">
                    <li class="page-item"><a class="page-link text-dark"
                                             href="<my:replaceParam name='page' value='${requestScope.currentPage+1}' />">Next</a>
                    </li>
                </c:if>
            </ul>
        </nav>
    </div>

</div>
<%@ include file="static/templates/footer.html" %>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js" integrity="sha384-9/reFTGAW83EW2RDu2S0VKaIzap3H66lZH81PoYlFhbGU+6BZp6G7niu735Sk7lN" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.min.js" integrity="sha384-+YQ4JLhjyBLPDQt//I+STsc9iw4uQqACwlvpslubQzn4u2UU2UFM80nGisd026JF" crossorigin="anonymous"></script>
<script src="${contextPath}/layouts/static/js/BsMultiSelect.js"></script>
<script src="${contextPath}/layouts/static/js/catalog.js"></script>
</body>
</html>
