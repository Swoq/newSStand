<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <title>Registration</title>
    <link href="<c:url value="layouts/static/styles/bootstrap.min.css"/>" rel="stylesheet">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
    <style>
        body {
            color: #fff;
            background: #f5f5f5;
            font-family: 'Roboto', sans-serif;
        }
        .form-control {
            font-size: 15px;
        }
        .form-control, .form-control:focus, .input-group-text {
            border-color: #e1e1e1;
        }
        .form-control, .btn {
            border-radius: 3px;
        }
        .signup-form {
            width: 400px;
            margin: 0 auto;
            padding: 30px 0;
        }
        .signup-form form {
            color: #999;
            border-radius: 3px;
            margin-bottom: 15px;
            background: #fff;
            box-shadow: 0 2px 2px rgba(0, 0, 0, 0.3);
            padding: 30px;
        }
        .signup-form h2 {
            color: #333;
            font-weight: bold;
            margin-top: 0;
        }
        .signup-form hr {
            margin: 0 -30px 20px;
        }
        .signup-form .form-group {
            margin-bottom: 20px;
        }
        .signup-form label {
            font-weight: normal;
            font-size: 15px;
        }
        .signup-form .form-control {
            min-height: 38px;
            box-shadow: none !important;
        }

        .signup-form .btn, .signup-form .btn:active {
            font-size: 16px;
            font-weight: bold;
            background: dimgrey !important;
            border: none;
            min-width: 140px;
        }
        .signup-form .btn:hover, .signup-form .btn:focus {
            background: gray !important;
        }
        .signup-form a {
            color: #fff;
            text-decoration: underline;
        }
        .signup-form a:hover {
            text-decoration: none;
        }
        .signup-form form a {
            color: dimgrey;
            text-decoration: none;
        }
        .signup-form form a:hover {
            text-decoration: underline;
        }
        .signup-form .fa {
            font-size: 21px;
        }
        .signup-form .fa-paper-plane {
            font-size: 18px;
        }
        .signup-form .fa-check {
            color: #fff;
            left: 17px;
            top: 18px;
            font-size: 7px;
            position: absolute;
        }
    </style>
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
