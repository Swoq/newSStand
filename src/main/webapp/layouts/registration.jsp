<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <title>Registration</title>
    <link href="<c:url value="layouts/static/styles/bootstrap.min.css"/>" rel="stylesheet">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
    <link href="${contextPath}/layouts/static/styles/registration.css" rel="stylesheet">
</head>
<body>
<div class="signup-form">
    <form action="<c:url value="/registration"/>" method="post">
        <h2>Sign Up</h2>
        <p>Please fill in this form to create an account!</p>
        <c:if test="${requestScope.errMsg != null}">
            <p class="mb-2 text-danger text-center font-italic">${requestScope.errMsg}</p>
        </c:if>
        <hr>
        <div class="form-group">
            <div class="input-group">
                <div class="input-group-prepend">
					<span class="input-group-text">
						<span class="fa fa-user"></span>
					</span>
                </div>
                <input type="text" class="form-control" name="firstName" placeholder="First Name" required="required" value="${requestScope.prevFirst}">
                <input type="text" class="form-control" name="secondName" placeholder="Second Name" required="required" value="${requestScope.prevSecond}">
            </div>
        </div>
        <div class="form-group">
            <div class="input-group">
                <div class="input-group-prepend">
					<span class="input-group-text">
						<i class="fa fa-paper-plane"></i>
					</span>
                </div>
                <input type="email" class="form-control" name="email" placeholder="Email Address" required="required" value="${requestScope.prevEmail}">
            </div>
        </div>
        <div class="form-group">
            <div class="input-group">
                <div class="input-group-prepend">
					<span class="input-group-text">
						<i class="fa fa-lock"></i>
					</span>
                </div>
                <input type="text" class="form-control" name="password" placeholder="Password" required="required">
            </div>
        </div>
        <div class="form-group">
            <div class="input-group">
                <div class="input-group-prepend">
					<span class="input-group-text">
						<i class="fa fa-lock"></i>
						<i class="fa fa-check"></i>
					</span>
                </div>
                <input type="text" class="form-control" name="confirmPassword" placeholder="Confirm Password" required="required">
            </div>
        </div>
        <div class="form-group">
            <label class="form-check-label"><input type="checkbox" name="termOfUseCheckbox" required="required"> I accept the <a href="#">Terms of Use</a> &amp; <a href="#">Privacy Policy</a></label>
        </div>
        <div class="form-group">
            <button type="submit" class="btn btn-primary btn-lg">Sign Up</button>
        </div>
    </form>
    <div class="text-center" style="color: dimgrey">Already have an account? <a style="color: dimgrey" href="<c:url value="/login"/>">Login here</a></div>
    <div class="text-center"><a style="color: dimgrey" href="<c:url value="/"/>">Main Page</a></div>
</div>
</body>
</html>
