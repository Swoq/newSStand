<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags" %>
<%@ page import="com.swoqe.newSStand.model.entity.UserRole" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="header.content" />

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
    <link href="https://cdnjs.cloudflare.com/ajax/libs/flag-icon-css/3.5.0/css/flag-icon.min.css" rel="stylesheet" />

</head>
<body>
<%--    Header--%>
<header class="blog-header py-3">
    <div class="row flex-nowrap justify-content-between align-items-center">
        <div class="col-4 pt-1 d-flex flex-column justify-content-start">
            <div class="dropdown mb-2">
                <button class="btn btn-outline-dark dropdown-toggle" type="button" id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    <c:set value="${sessionScope.lang}" var="sessionLocale" scope="page"/>
                    <span class="flag-icon flag-icon-${sessionLocale}"></span>
                    <c:choose>
                        <c:when test='${sessionLocale.equals("gb")}'>English</c:when>
                        <c:when test='${sessionLocale.equals("ru")}'>Russian</c:when>
                        <c:when test='${sessionLocale.equals("ua")}'>Ukrainian</c:when>
                    </c:choose>
                </button>
                <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                    <a class="dropdown-item" href="<my:replaceParam name="locale" value="gb"/>"><span class="flag-icon flag-icon-gb"></span> English</a>
                    <a class="dropdown-item" href="<my:replaceParam name="locale" value="ru"/>"><span class="flag-icon flag-icon-ru"></span> Russian</a>
                    <a class="dropdown-item" href="<my:replaceParam name="locale" value="ua"/>"><span class="flag-icon flag-icon-ua"></span> Ukrainian</a>
                </div>
            </div>
            <a class="btn btn-outline-dark col-4" href="${contextPath}/catalog"><fmt:message key="header.placeholder.catalogBtn"/></a>
        </div>
        <div class="col-4 text-center">
            <a class="blog-header-logo text-dark" href="${contextPath}/">NewSStand</a>
        </div>
        <div class="col-4 d-flex flex-column justify-content-end align-items-end">
            <c:if test="${sessionScope.user == null}">
                <a class="btn btn-sm btn-outline-secondary" href="${contextPath}/registration"><fmt:message key="header.placeholder.signUpBtn"/></a>
                <a class="btn btn-sm btn-outline-secondary mt-1" href="${contextPath}/login"><fmt:message key="header.placeholder.signInBtn"/></a>
            </c:if>
            <c:if test="${sessionScope.user != null}">

                <h5 class="font-italic text-dark">
                    <c:out value="${sessionScope.user.firstName} ${sessionScope.user.lastName}"/>
                </h5>
                <div class="d-flex justify-content-center">
                    <c:if test="${sessionScope.user.userRole.equals(UserRole.ADMIN)}">
                        <button type="button" class="btn btn-sm btn-outline-secondary" data-toggle="modal"
                                data-target="#adminModal"><fmt:message key="header.placeholder.adminPanelBtn"/></button>
                    </c:if>
                    <a class="btn btn-sm ml-1 btn-outline-secondary" href="${contextPath}/account"><fmt:message key="header.placeholder.accountBtn"/></a>
                    <a class="btn btn-sm ml-1 btn-outline-secondary" href="${contextPath}/logout"><fmt:message key="header.placeholder.logoutBtn"/></a>
                </div>
            </c:if>

        </div>
    </div>
</header>
<div class="nav-scroller py-1 mb-2">
    <nav class="nav d-flex justify-content-between">
        <a class="p-2 text-muted" href="${contextPath}/catalog?genres%5B%5D=1"><fmt:message key="genres.1"/></a>
        <a class="p-2 text-muted" href="${contextPath}/catalog?genres%5B%5D=2"><fmt:message key="genres.2"/></a>
        <a class="p-2 text-muted" href="${contextPath}/catalog?genres%5B%5D=3"><fmt:message key="genres.3"/></a>
        <a class="p-2 text-muted" href="${contextPath}/catalog?genres%5B%5D=4"><fmt:message key="genres.4"/></a>
        <a class="p-2 text-muted" href="${contextPath}/catalog?genres%5B%5D=5"><fmt:message key="genres.5"/></a>
        <a class="p-2 text-muted" href="${contextPath}/catalog?genres%5B%5D=6"><fmt:message key="genres.6"/></a>
        <a class="p-2 text-muted" href="${contextPath}/catalog?genres%5B%5D=7"><fmt:message key="genres.7"/></a>
        <a class="p-2 text-muted" href="${contextPath}/catalog?genres%5B%5D=8"><fmt:message key="genres.8"/></a>
        <a class="p-2 text-muted" href="${contextPath}/catalog?genres%5B%5D=9"><fmt:message key="genres.9"/></a>
        <a class="p-2 text-muted" href="${contextPath}/catalog?genres%5B%5D=10"><fmt:message key="genres.10"/></a>
        <a class="p-2 text-muted" href="${contextPath}/catalog?genres%5B%5D=11"><fmt:message key="genres.11"/></a>
        <a class="p-2 text-muted" href="${contextPath}/catalog?genres%5B%5D=12"><fmt:message key="genres.12"/></a>
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
                    <a class="btn btn-sm mb-4 btn-outline-secondary" href="${contextPath}/catalog/add"><fmt:message key="admin.panel.addNewPublication"/></a>
                    <a class="btn btn-sm mb-4 btn-outline-secondary addPeriodBtn"><fmt:message key="admin.panel.addNewPeriod"/></a>
                    <a class="btn btn-sm mb-4 btn-outline-secondary addGenreBtn"><fmt:message key="admin.panel.addNewGenre"/></a>

                    <form action="${contextPath}/periods/add" method="post" >
                        <div class="input-group mb-3">
                            <input type="hidden" class="form-control addPeriodForm" placeholder="<fmt:message key="admin.panel.placeholder.newPeriodName"/>"
                                   name="name">
                            <input type="hidden" class="form-control addPeriodForm" placeholder="<fmt:message key="admin.panel.placeholder.newPeriodDescription"/>"
                                   name="description">
                            <div class="input-group-append">
                                <input type="hidden" class="btn btn-outline-secondary" id="submitPeriodBtn" value="Submit">
                            </div>
                        </div>
                    </form>

                    <form action="${contextPath}/genres/add" method="post" >
                        <div class="input-group mb-3">
                            <input type="hidden" class="form-control addGenreForm" placeholder="<fmt:message key="admin.panel.placeholder.newGenreName"/>"
                                   name="name">
                            <input type="hidden" class="form-control addGenreForm" placeholder="<fmt:message key="admin.panel.placeholder.newGenreDescription"/>"
                                   name="description">
                            <div class="input-group-append">
                                <input type="hidden" class="btn btn-outline-secondary" id="submitGenreBtn" value="Submit">
                            </div>
                        </div>
                    </form>

                    <form action="#" method="get">
                        <div class="input-group mb-3">
                            <input type="text" class="form-control" placeholder="<fmt:message key="admin.panel.placeholder.emailToBlock"/>" aria-label="Recipient's username" aria-describedby="basic-addon2">
                            <div class="input-group-append">
                                <button class="btn btn-outline-secondary" type="button"><fmt:message key="admin.panel.placeholder.blockBtn"/></button>
                            </div>
                        </div>
                    </form>
                    <form action="#" method="get">
                        <div class="input-group mb-3">
                            <input type="text" class="form-control" placeholder="<fmt:message key="admin.panel.placeholder.emailToUnblock"/>" aria-label="Recipient's username" aria-describedby="basic-addon2">
                            <div class="input-group-append">
                                <button class="btn btn-outline-secondary" type="button"><fmt:message key="admin.panel.placeholder.unblockBtn"/></button>
                            </div>
                        </div>

                    </form>

                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal"><fmt:message key="admin.panel.placeholder.closeBtn"/></button>
            </div>
        </div>
    </div>
</div>


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
