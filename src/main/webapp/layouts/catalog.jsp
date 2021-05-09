<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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

        .cover_img{
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
    <header class="blog-header py-3">
        <div class="row flex-nowrap justify-content-between align-items-center">
            <div class="col-4 pt-1">
                <a class="text-muted" href="<c:url value="/catalog"/>">Catalog</a>
            </div>
            <div class="col-4 text-center">
                <a class="blog-header-logo text-dark" href="<c:url value="/"/>">NewSStand</a>
            </div>
            <div class="col-4 d-flex flex-column justify-content-end align-items-end">
                <c:if test="${sessionScope.user == null}">
                    <a class="btn btn-sm btn-outline-secondary" href="<c:url value="/registration"/>">Sign up</a>
                    <a class="btn btn-sm btn-outline-secondary mt-1" href="<c:url value="/login"/>">Sign in</a>
                </c:if>
                <c:if test="${sessionScope.user != null}">

                    <h5 class="font-italic text-dark">
                        <c:out value="${sessionScope.user.firstName} ${sessionScope.user.lastName}"/>
                    </h5>
                    <div class="d-flex justify-content-center">
                        <c:if test="${sessionScope.user.userRole.equals(UserRole.ADMIN)}">
                            <button type="button" class="btn btn-sm btn-outline-secondary" data-toggle="modal"
                                    data-target="#adminModal">Admin Panel</button>
                            <%--Modal--%>
                        </c:if>
                        <a class="btn btn-sm ml-1 btn-outline-secondary" href="<c:url value="/logout"/>">Logout</a>
                    </div>
                </c:if>

            </div>
        </div>
    </header>
    <div class="nav-scroller py-1 mb-2">
        <nav class="nav d-flex justify-content-between">
            <a class="p-2 text-muted" href="<c:url value="/catalog?genre=World"/>">World</a>
            <a class="p-2 text-muted" href="<c:url value="/catalog?genre=US"/>">U.S.</a>
            <a class="p-2 text-muted" href="<c:url value="/catalog?genre=Technology"/>">Technology</a>
            <a class="p-2 text-muted" href="<c:url value="/catalog?genre=Design"/>">Design</a>
            <a class="p-2 text-muted" href="<c:url value="/catalog?genre=Culture"/>">Culture</a>
            <a class="p-2 text-muted" href="<c:url value="/catalog?genre=Business"/>">Business</a>
            <a class="p-2 text-muted" href="<c:url value="/catalog?genre=Politics"/>">Politics</a>
            <a class="p-2 text-muted" href="<c:url value="/catalog?genre=Opinion"/>">Opinion</a>
            <a class="p-2 text-muted" href="<c:url value="/catalog?genre=Science"/>">Science</a>
            <a class="p-2 text-muted" href="<c:url value="/catalog?genre=Health"/>">Health</a>
            <a class="p-2 text-muted" href="<c:url value="/catalog?genre=Style"/>">Style</a>
            <a class="p-2 text-muted" href="<c:url value="/catalog?genre=Travel"/>">Travel</a>
        </nav>
    </div>
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
                                <img src="<c:url value="layouts/static/pp_covers/${item.getImageName()}"/>" class="cover_img" width="150" height="150" alt="">
                            </div>
                            <div class="media-body">
                                <h6 class="media-title font-weight-semibold">
                                    <a href="#" data-abc="true">${item.getName()}</a>
                                </h6>
                                <ul class="list-inline list-inline-dotted mb-3 mb-lg-2">
                                    <li class="list-inline-item"><a href="#" class="text-muted" data-abc="true">Phones</a></li>
                                    <li class="list-inline-item"><a href="#" class="text-muted" data-abc="true">Mobiles</a></li>
                                </ul>
                                <p class="mb-3">${item.getDescription()}</p>
                            </div>
                            <div class="mt-3 mt-lg-0 ml-lg-3 text-center">
                                <h3 class="mb-0 font-weight-semibold">$${item.getShownLowestPrice()}</h3>
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
                <div class="card card-body mt-3">
                    <div class="media align-items-center align-items-lg-start text-center text-lg-left flex-column flex-lg-row">
                        <div class="mr-2 mb-3 mb-lg-0"><img src="https://i.imgur.com/Aj0L4Wa.jpg" width="150" height="150"
                                                            alt=""></div>
                        <div class="media-body">
                            <h6 class="media-title font-weight-semibold"><a href="#" data-abc="true">Apple iPhone XS Max
                                (Gold, 64 GB)</a></h6>
                            <ul class="list-inline list-inline-dotted mb-3 mb-lg-2">
                                <li class="list-inline-item"><a href="#" class="text-muted" data-abc="true">Phones</a></li>
                                <li class="list-inline-item"><a href="#" class="text-muted" data-abc="true">Mobiles</a></li>
                            </ul>
                            <p class="mb-3">256 GB ROM | 15.49 cm (6.1 inch) Display 12MP Rear Camera | 15MP Front Camera
                                A12 Bionic Chip Processor | Gorilla Glass with high quality display </p>
                            <ul class="list-inline list-inline-dotted mb-0">
                                <li class="list-inline-item">All items from <a href="#" data-abc="true">Mobile junction</a>
                                </li>
                                <li class="list-inline-item">Add to <a href="#" data-abc="true">wishlist</a></li>
                            </ul>
                        </div>
                        <div class="mt-3 mt-lg-0 ml-lg-3 text-center">
                            <h3 class="mb-0 font-weight-semibold">$612.99</h3>
                            <div><i class="fa fa-star"></i> <i class="fa fa-star"></i> <i class="fa fa-star"></i> <i
                                    class="fa fa-star"></i> <i class="fa fa-star"></i></div>
                            <div class="text-muted">2349 reviews</div>
                            <button type="button" class="btn btn-warning mt-4 text-white"><i class="icon-cart-add mr-2"></i>
                                Add to cart
                            </button>
                        </div>
                    </div>
                </div>
                <div class="card card-body mt-3">
                    <div class="media align-items-center align-items-lg-start text-center text-lg-left flex-column flex-lg-row">
                        <div class="mr-2 mb-3 mb-lg-0"><img src="https://i.imgur.com/5Aqgz7o.jpg" width="150" height="150"
                                                            alt=""></div>
                        <div class="media-body">
                            <h6 class="media-title font-weight-semibold"><a href="#" data-abc="true">Apple iPhone XR (Red,
                                128 GB)</a></h6>
                            <ul class="list-inline list-inline-dotted mb-3 mb-lg-2">
                                <li class="list-inline-item"><a href="#" class="text-muted" data-abc="true">Phones</a></li>
                                <li class="list-inline-item"><a href="#" class="text-muted" data-abc="true">Mobiles</a></li>
                            </ul>
                            <p class="mb-3">128 GB ROM | 15.49 cm (6.1 inch) Display 12MP Rear Camera | 7MP Front Camera A12
                                Bionic Chip Processor | Gorilla Glass with high quality display </p>
                            <ul class="list-inline list-inline-dotted mb-0">
                                <li class="list-inline-item">All items from <a href="#" data-abc="true">Mobile point</a>
                                </li>
                                <li class="list-inline-item">Add to <a href="#" data-abc="true">wishlist</a></li>
                            </ul>
                        </div>
                        <div class="mt-3 mt-lg-0 ml-lg-3 text-center">
                            <h3 class="mb-0 font-weight-semibold">$459.99</h3>
                            <div><i class="fa fa-star"></i> <i class="fa fa-star"></i> <i class="fa fa-star"></i> <i
                                    class="fa fa-star"></i></div>
                            <div class="text-muted">1985 reviews</div>
                            <button type="button" class="btn btn-warning mt-4 text-white"><i class="icon-cart-add mr-2"></i>
                                Add to cart
                            </button>
                        </div>
                    </div>
                </div>
                <div class="card card-body mt-3">
                    <div class="media align-items-center align-items-lg-start text-center text-lg-left flex-column flex-lg-row">
                        <div class="mr-2 mb-3 mb-lg-0"><img src="https://i.imgur.com/Aj0L4Wa.jpg" width="150" height="150"
                                                            alt=""></div>
                        <div class="media-body">
                            <h6 class="media-title font-weight-semibold"><a href="#" data-abc="true">Apple iPhone XS Max
                                (Gold, 64 GB)</a></h6>
                            <ul class="list-inline list-inline-dotted mb-3 mb-lg-2">
                                <li class="list-inline-item"><a href="#" class="text-muted" data-abc="true">Phones</a></li>
                                <li class="list-inline-item"><a href="#" class="text-muted" data-abc="true">Mobiles</a></li>
                            </ul>
                            <p class="mb-3">256 GB ROM | 15.49 cm (6.1 inch) Display 12MP Rear Camera | 15MP Front Camera
                                A12 Bionic Chip Processor | Gorilla Glass with high quality display </p>
                            <ul class="list-inline list-inline-dotted mb-0">
                                <li class="list-inline-item">All items from <a href="#" data-abc="true">Mobile junction</a>
                                </li>
                                <li class="list-inline-item">Add to <a href="#" data-abc="true">wishlist</a></li>
                            </ul>
                        </div>
                        <div class="mt-3 mt-lg-0 ml-lg-3 text-center">
                            <h3 class="mb-0 font-weight-semibold">$612.99</h3>
                            <div><i class="fa fa-star"></i> <i class="fa fa-star"></i> <i class="fa fa-star"></i> <i
                                    class="fa fa-star"></i> <i class="fa fa-star"></i></div>
                            <div class="text-muted">2349 reviews</div>
                            <button type="button" class="btn btn-warning mt-4 text-white"><i class="icon-cart-add mr-2"></i>
                                Add to cart
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
<%--Pagination--%>
    <nav aria-label="Page navigation example">
        <ul class="pagination justify-content-center">
            <li class="page-item disabled">
                <a class="page-link" href="#" tabindex="-1">Previous</a>
            </li>
            <li class="page-item"><a class="page-link" href="#">1</a></li>
            <li class="page-item active"><a class="page-link" href="#">2</a></li>
            <li class="page-item"><a class="page-link" href="#">3</a></li>
            <li class="page-item">
                <a class="page-link" href="#">Next</a>
            </li>
        </ul>
    </nav>
</div>
<%--Modal--%>
<div class="modal fade" id="adminModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">Modal title</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                ...
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                <button type="button" class="btn btn-primary">Save changes</button>
            </div>
        </div>
    </div>
</div>
<script
        src="https://code.jquery.com/jquery-3.6.0.min.js"
        integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4="
        crossorigin="anonymous"></script>
<script src="<c:url value="layouts/static/js/bootstrap.bundle.min.js" />"></script>
</body>
</html>
