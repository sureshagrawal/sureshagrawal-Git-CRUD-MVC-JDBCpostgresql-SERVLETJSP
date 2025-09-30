<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.nsgacademy.crudmvc.model.Student" %>

<!doctype html>
<html lang="en">

<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>NSG Academy</title>
<link
    href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css"
    rel="stylesheet">
</head>

<body>
    <div class="container my-5">
        <h1 class="text-center">MVC CRUD APPLICATION</h1>
        <h3 class="text-center">Using JSP AND Servlet</h3>

        <%
            Student student = (Student) request.getAttribute("student");
            boolean isEdit = student != null;
        %>
        <h2><%= isEdit ? "Edit Student" : "Add New Student" %></h2>

        <%
            String error = (String) request.getAttribute("error");
            if (error != null) {
        %>
        	<div class="alert alert-danger"><%= error %></div>
    	<% } %>

        <form action="<%= isEdit ? "update" : "insert" %>" method="post">
            <% if (isEdit) { %>
                <input type="hidden" name="id" value="<%= student.getId() %>" />
            <% } %>

            <div class="mb-3">
                <label class="form-label">Name</label>
                <input type="text" class="form-control" placeholder="Enter your name" name="name"
                    value="<%= (student != null) ? student.getName() : "" %>" id="stnameid" required
                    pattern="[A-Za-z\s]+" title="Name should contain only letters and spaces.">
            </div>

            <div class="mb-3">
                <label class="form-label">Email</label>
                <input type="email" class="form-control" placeholder="Enter your Email" name="email"
                    value="<%= (student != null) ? student.getEmail() : "" %>" id="stemailid" required>
            </div>

            <div class="mb-3">
                <label class="form-label">Mobile</label>
                <input type="text" class="form-control" placeholder="Enter your Mobile" name="mobile"
                    value="<%= (student != null) ? student.getMobile() : "" %>" id="stmobileid" required
                    pattern="\d{10}" title="Mobile number should be 10 digits.">
            </div>

            <button type="submit" class="btn btn-primary" name="submit">Submit</button>
            <button type="reset" class="btn btn-dark">Reset</button>
        </form>
    </div>

    <script
        src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/js/bootstrap.bundle.min.js"></script>

</body>

</html>
