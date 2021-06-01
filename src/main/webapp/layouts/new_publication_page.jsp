<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<html>
<head>
    <title>New Publication</title>

    <link href="${contextPath}/layouts/static/styles/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">

    <link href="https://fonts.googleapis.com/css?family=Playfair&#43;Display:700,900" rel="stylesheet">
    <link rel="canonical" href="https://getbootstrap.com/docs/4.6/examples/blog/">

    <link href="${contextPath}/layouts/static/styles/new_publication_page.css" rel="stylesheet">
</head>
<body>
<div class="container">
<%--    Header--%>
    <%@ include file = "static/templates/header.jsp" %>

    <div class="new-publication-form">
        <a href="${contextPath}/catalog" class="font-weight-light text-dark mb-3">Back to catalog</a>
        <form action="${contextPath}/catalog/${requestScope.publication.id == null ? "add" : "edit"}"
              method="post" enctype="multipart/form-data">
<%--            Title--%>
            <input type="hidden" value="${requestScope.publication.id}" name="id">
            <div class="input-group mb-3">
                <div class="input-group-prepend">
                    <span class="input-group-text" id="basic-addon1">Title</span>
                </div>
                <input value="${requestScope.publication.name}"
                       type="text" class="form-control" name="title" required>
            </div>
<%--            Publisher--%>
            <div class="input-group mb-3">
                <div class="input-group-prepend">
                    <span class="input-group-text" id="basic-addon2">Publisher</span>
                </div>
                <input value="${requestScope.publication.publisher}"
                       type="text" class="form-control" name="publisher" required>
            </div>

<%--    Genres Multiselect--%>
            <div class="input-group mb-3">
                <div class="input-group-prepend">
                    <span class="input-group-text">Genres</span>
                </div>
                <div class="col-sm-10">
                    <select class="custom-select form-control" name="genres" id="genreMultiSelect" multiple="multiple" style="display: none">
                        <c:forEach var="genre" items="${requestScope.genres}">
                            <c:set var="contains" value="false" />
                            <c:forEach var="item" items="${requestScope.publication.genres}">
                                <c:if test="${item.id == genre.id}">
                                    <c:set var="contains" value="true" />
                                </c:if>
                            </c:forEach>

                            <option value="${genre.name}" ${contains ? "selected" : ""}>${genre.name}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>

<%--            Publication Date--%>
            <div class="input-group mb-3">
                <div class="input-group-prepend">
                    <span class="input-group-text" id="basic-addon3">Publ. date</span>
                </div>
                <input value="${requestScope.publication.publicationDate.toString()}"
                       type="date" class="form-control mr-2" name="publication_date" placeholder="Date">
                <div class="input-group-prepend">
                    <span class="input-group-text">Cover</span>
                </div>
<%--                File upload--%>
                <div class="custom-file">
                    <input type="file" name="cover_file" class="custom-file-input" id="inputGroupFile01" accept=".jpg,.png">
                    <label class="custom-file-label text-truncate" for="inputGroupFile01">Choose file</label>
                </div>
            </div>
            <%--    Description--%>
            <div class="input-group mb-3">
                <div class="input-group-prepend">
                    <span class="input-group-text">Description</span>
                </div>
                <textarea class="form-control" name="description">${requestScope.publication.description}</textarea>
            </div>

<%--            Period and price--%>
            <div class="dynamic-stuff">
                <c:if test="${requestScope.publication.id != null}">
                    <c:forEach items="${requestScope.publication.pricesPerPeriods}" var="entry">
                        <div class="form-group dynamic-element">
                            <div class="row d-flex flex-row justify-content-center">
                                <div class="col-md-4">
                                    <div class="input-group mb-3 my-auto">
                                        <input value="${entry.value}" type="number" name="prices" class="form-control" placeholder="Price">
                                        <div class="input-group-prepend">
                                            <span class="input-group-text">$</span>
                                        </div>
                                    </div>
                                </div>
                                <p class="my-auto">per</p>
                                <div class="input-group mb-3 col-md-3 my-auto">
                                    <select class="custom-select form-control" name="periods">
                                        <c:forEach var="period" items="${requestScope.periods}">
                                            <c:choose>
                                                <c:when test="${period.name.equalsIgnoreCase(entry.key)}">
                                                    <option value="${period.name}" selected>${period.name}</option>
                                                </c:when>
                                                <c:otherwise>
                                                    <option value="${period.name}">${period.name}</option>
                                                </c:otherwise>
                                            </c:choose>

                                        </c:forEach>
                                    </select>
                                    <div class="input-group-append">
                                        <label class="input-group-text">Options</label>
                                    </div>
                                </div>

                                <!-- End of fields-->
                                <button type="button" class="close delete" aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                            </div>
                        </div>
                    </c:forEach>
                </c:if>
            </div>
            <div class="d-flex flex-column">
                <p class="btn btn-sm btn-outline-dark add-one ">Add new paid period</p>
                <button type="submit" class="btn btn-outline-dark btn-lg">${requestScope.publication.id == null ? 'Add new' : 'Update'} publication</button>
                <c:if test="${requestScope.errMsg != null}">
                    <p class="mb-2 text-danger text-center font-italic">${requestScope.errMsg}</p>
                </c:if>
            </div>

        </form>
    </div>
</div>


<!-- HIDDEN DYNAMIC ELEMENT TO CLONE -->
<div class="form-group dynamic-element" style="display:none">
    <div class="row d-flex flex-row justify-content-center">
        <div class="col-md-4">
            <div class="input-group mb-3 my-auto">
                <input type="number" name="prices" class="form-control" placeholder="Price" aria-label="Username" aria-describedby="basic-addon1">
                <div class="input-group-prepend">
                    <span class="input-group-text" id="basic-addon4">$</span>
                </div>
            </div>
        </div>
        <p class="my-auto">per</p>
        <div class="input-group mb-3 col-md-3 my-auto">
            <select class="custom-select form-control" id="rol" name="periods">
                <c:forEach var="period" items="${requestScope.periods}">
                    <option value="${period.name}">${period.name}</option>
                </c:forEach>
            </select>
            <div class="input-group-append">
                <label class="input-group-text" for="rol">Options</label>
            </div>
        </div>

        <!-- End of fields-->
        <button type="button" class="close delete" aria-label="Close">
            <span aria-hidden="true">&times;</span>
        </button>
    </div>
</div>
<!-- END OF HIDDEN ELEMENT -->
<%@ include file = "static/templates/footer.html" %>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js" integrity="sha384-9/reFTGAW83EW2RDu2S0VKaIzap3H66lZH81PoYlFhbGU+6BZp6G7niu735Sk7lN" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.min.js" integrity="sha384-+YQ4JLhjyBLPDQt//I+STsc9iw4uQqACwlvpslubQzn4u2UU2UFM80nGisd026JF" crossorigin="anonymous"></script>
<script src="${contextPath}/layouts/static/js/BsMultiSelect.js"></script>
<script src="${contextPath}/layouts/static/js/new_publication_page.js"></script>
</body>
</html>