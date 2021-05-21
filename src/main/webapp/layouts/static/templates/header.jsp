<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.swoqe.newSStand.model.entity.UserRole" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<html>
<head>
    <title></title>
    <style>
        .blog-header {
            line-height: 1;
            border-bottom: 1px solid #e5e5e5;
        }

        .blog-header-logo {
            font-family: "Playfair Display", Georgia, "Times New Roman", serif;
            font-size: 2.25rem;
        }

        .blog-header-logo:hover {
            text-decoration: none;
        }

        h1, h2, h3, h4, h5, h6 {
            font-family: "Playfair Display", Georgia, "Times New Roman", serif;
        }

        .nav-scroller {
            position: relative;
            z-index: 2;
            height: 2.75rem;
            overflow-y: hidden;
        }

        .nav-scroller .nav {
            display: -ms-flexbox;
            display: flex;
            -ms-flex-wrap: nowrap;
            flex-wrap: nowrap;
            padding-bottom: 1rem;
            margin-top: -1px;
            overflow-x: auto;
            text-align: center;
            white-space: nowrap;
            -webkit-overflow-scrolling: touch;
        }

        .nav-scroller {
            padding-top: .75rem;
            padding-bottom: .75rem;
            font-size: .875rem;
        }
    </style>
</head>
<body>
<%--    Footer--%>
<header class="blog-header py-3">
    <div class="row flex-nowrap justify-content-between align-items-center">
        <div class="col-4 pt-1">
            <a class="text-muted" href="${contextPath}/catalog">Catalog</a>
        </div>
        <div class="col-4 text-center">
            <a class="blog-header-logo text-dark" href="${contextPath}/">NewSStand</a>
        </div>
        <div class="col-4 d-flex flex-column justify-content-end align-items-end">
            <c:if test="${sessionScope.user == null}">
                <a class="btn btn-sm btn-outline-secondary" href="${contextPath}/registration">Sign up</a>
                <a class="btn btn-sm btn-outline-secondary mt-1" href="${contextPath}/login">Sign in</a>
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
                    <a class="btn btn-sm ml-1 btn-outline-secondary" href="${contextPath}/account">Account</a>
                    <a class="btn btn-sm ml-1 btn-outline-secondary" href="${contextPath}/logout">Logout</a>
                </div>
            </c:if>

        </div>
    </div>
</header>
<div class="nav-scroller py-1 mb-2">
    <nav class="nav d-flex justify-content-between">
        <a class="p-2 text-muted" href="${contextPath}/catalog?genres%5B%5D=1">World</a>
        <a class="p-2 text-muted" href="${contextPath}/catalog?genres%5B%5D=2">U.S.</a>
        <a class="p-2 text-muted" href="${contextPath}/catalog?genres%5B%5D=3">Technology</a>
        <a class="p-2 text-muted" href="${contextPath}/catalog?genres%5B%5D=4">Design</a>
        <a class="p-2 text-muted" href="${contextPath}/catalog?genres%5B%5D=5">Culture</a>
        <a class="p-2 text-muted" href="${contextPath}/catalog?genres%5B%5D=6">Business</a>
        <a class="p-2 text-muted" href="${contextPath}/catalog?genres%5B%5D=7">Politics</a>
        <a class="p-2 text-muted" href="${contextPath}/catalog?genres%5B%5D=8">Opinion</a>
        <a class="p-2 text-muted" href="${contextPath}/catalog?genres%5B%5D=9">Science</a>
        <a class="p-2 text-muted" href="${contextPath}/catalog?genres%5B%5D=10">Health</a>
        <a class="p-2 text-muted" href="${contextPath}/catalog?genres%5B%5D=11">Style</a>
        <a class="p-2 text-muted" href="${contextPath}/catalog?genres%5B%5D=12">Travel</a>
    </nav>
</div>

<%--Modal--%>
<div class="modal fade" id="adminModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title align-content-center" id="exampleModalLabel">Admin Panel</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <div class="d-flex flex-column">
                    <a class="btn btn-sm mb-4 btn-outline-secondary" href="${contextPath}/catalog/add">Add new publication</a>
                    <a class="btn btn-sm mb-4 btn-outline-secondary addPeriodBtn">Add new period</a>
                    <a class="btn btn-sm mb-4 btn-outline-secondary addGenreBtn">Add new genre</a>

                    <form action="${contextPath}/periods/add" method="post" >
                        <div class="input-group mb-3">
                            <input type="hidden" class="form-control addPeriodForm" placeholder="Period name e.g 'week'"
                                   name="name">
                            <input type="hidden" class="form-control addPeriodForm" placeholder="Description"
                                   name="description">
                            <div class="input-group-append">
                                <input type="hidden" class="btn btn-outline-secondary" id="submitPeriodBtn" value="Submit">
                            </div>
                        </div>
                    </form>

                    <form action="${contextPath}/genres/add" method="post" >
                        <div class="input-group mb-3">
                            <input type="hidden" class="form-control addGenreForm" placeholder="Genre name e.g 'horror'"
                                   name="name">
                            <input type="hidden" class="form-control addGenreForm" placeholder="Description"
                                   name="description">
                            <div class="input-group-append">
                                <input type="hidden" class="btn btn-outline-secondary" id="submitGenreBtn" value="Submit">
                            </div>
                        </div>
                    </form>

                    <form action="#" method="get">
                        <div class="input-group mb-3">
                            <input type="text" class="form-control" placeholder="Email to block" aria-label="Recipient's username" aria-describedby="basic-addon2">
                            <div class="input-group-append">
                                <button class="btn btn-outline-secondary" type="button">Block</button>
                            </div>
                        </div>
                    </form>
                    <form action="#" method="get">
                        <div class="input-group mb-3">
                            <input type="text" class="form-control" placeholder="Email to unblock" aria-label="Recipient's username" aria-describedby="basic-addon2">
                            <div class="input-group-append">
                                <button class="btn btn-outline-secondary" type="button">Unblock</button>
                            </div>
                        </div>

                    </form>

                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>

<script
        src="https://code.jquery.com/jquery-3.6.0.min.js"
        integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4="
        crossorigin="anonymous"></script>
<script src="${contextPath}/layouts/static/js/bootstrap.bundle.min.js"></script>

<script>
    $('.addPeriodBtn').on('click', addPeriod);
    $('.addGenreBtn').on('click', addGenre);

    function addPeriod() {
        $('.addPeriodForm').attr("type", "text");
        $('#submitPeriodBtn').attr("type", "submit");
    }

    function addGenre() {
        $('.addGenreForm').attr("type", "text");
        $('#submitGenreBtn').attr("type", "submit");
    }

</script>

</body>
</html>
