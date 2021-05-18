<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<html>
<head>
    <title>New Publication</title>

    <link href="${pageContext.request.contextPath}/layouts/static/styles/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">

    <link href="https://fonts.googleapis.com/css?family=Playfair&#43;Display:700,900" rel="stylesheet">
    <link rel="canonical" href="https://getbootstrap.com/docs/4.6/examples/blog/">
    <link href="${contextPath}/layouts/static/styles/bootstrap-multiselect.min.css" rel="stylesheet" type="text/css">

    <style>
        .new-publication-form {
            width: 800px;
            margin: 0 auto;
            padding: 30px 0;
        }


    </style>


</head>
<body>
<div class="container">
<%--    Header--%>
    <%@ include file = "static/templates/header.jsp" %>

    <div class="new-publication-form">
        <a href="${pageContext.request.contextPath}/catalog" class="font-weight-light text-dark mb-3">Back to catalog</a>
        <form action="${pageContext.request.contextPath}/catalog/add" method="post" enctype="multipart/form-data">
<%--            Title--%>
            <div class="input-group mb-3">
                <div class="input-group-prepend">
                    <span class="input-group-text" id="basic-addon1">Title</span>
                </div>
                <input type="text" class="form-control" name="title" aria-describedby="basic-addon1" value="${requestScope.prevTitle}">
            </div>
<%--            Publisher--%>
            <div class="input-group mb-3">
                <div class="input-group-prepend">
                    <span class="input-group-text" id="basic-addon2">Publisher</span>
                </div>
                <input type="text" class="form-control" name="publisher" aria-describedby="basic-addon2" value="${requestScope.prevPublisher}">
            </div>

<%--    Genres Multiselect--%>
            <div class="input-group mb-3">
                <div class="input-group-prepend">
                    <span class="input-group-text">Genres</span>
                </div>
                <div class="col-sm-10">
                    <select class="custom-select form-control" name="genres" id="genreMultiSelect" multiple="multiple" style="display: none">
                        <c:forEach var="genre" items="${requestScope.genres}">
                            <option value="${genre.name}">${genre.name}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>

<%--            Publication Date--%>
            <div class="input-group mb-3">
                <div class="input-group-prepend">
                    <span class="input-group-text" id="basic-addon3">Publ. date</span>
                </div>
                <input type="date" class="form-control mr-2" name="publication_date" placeholder="Username" aria-label="Username" aria-describedby="basic-addon1">
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
                <textarea class="form-control" name="description" aria-label="With textarea" >${requestScope.prevDescription}</textarea>
            </div>

<%--            Period and price--%>
            <div class="dynamic-stuff">

            </div>
            <div class="d-flex flex-column">
                <p class="btn btn-sm btn-outline-dark add-one ">Add new paid period</p>
                <button type="submit" class="btn btn-outline-dark btn-lg">Add new publication</button>
                <c:if test="${requestScope.errMsg != null}">
                    <p class="mb-2 text-danger text-center font-italic">${requestScope.errMsg}</p>
                </c:if>
            </div>

        </form>
    </div>
</div>

<%@ include file = "static/templates/footer.html" %>

<!-- HIDDEN DYNAMIC ELEMENT TO CLONE -->
<!-- you can replace it with any other elements -->
<div class="form-group dynamic-element" style="display:none">
    <div class="row d-flex flex-row justify-content-center">
        <!-- Replace these fields -->
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

<script
        src="https://code.jquery.com/jquery-3.6.0.min.js"
        integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4="
        crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.11.0/umd/popper.min.js"></script>
<script src="${contextPath}/layouts/static/js/bootstrap.bundle.min.js"></script>
<script src="${contextPath}/layouts/static/js/BsMultiSelect.js"></script>
<script>
    $(document).ready(function () {
        $('.add-one').click();
        $('#genreMultiSelect').bsMultiSelect();
    });

    $('.custom-file-input').on('change', function() {
        let fileName = $(this).val().split('\\').pop();
        $(this).next('.custom-file-label').addClass("selected").html(fileName);
    });

    //Clone the hidden element and shows it
    $('.add-one').click(function(){
        $('.dynamic-element').first().clone().appendTo('.dynamic-stuff').show();
        attach_delete();
    });


    //Attach functionality to delete buttons
    function attach_delete(){
        $('.delete').off();
        $('.delete').click(function(){
            console.log("click");
            $(this).closest('.form-group').remove();
        });
    }
</script>
</body>
</html>