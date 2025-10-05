<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.List" %>
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
<link rel="stylesheet"
    href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css" />
</head>

<body>

    <div class="container my-5">

        <h1 class="text-center">MVC CRUD APPLICATION</h1>
        <h3 class="text-center">Using JSP AND Servlet and Postgresql of Supabase</h3>

        <a href="new" class="btn btn-primary mb-5"><i
            class="fa-solid fa-user-plus m-1"></i>Add Student</a>

        <%
            String success = request.getParameter("success");
            if (success != null) {
        %>
            <div class="alert alert-success text-center"><%= success %></div>
        <%
            }
        %>

        <table class="table table-hover table-striped ">
            <thead class="bg-dark text-light">
                <tr>
                    <th scope="col">#</th>
                    <th scope="col">Name</th>
                    <th scope="col">Email</th>
                    <th scope="col">Contact</th>
                    <th scope="col">Operations</th>
                </tr>
            </thead>
            <tbody>

                <%
                int cnt = 1;
List<Student> listStudents = (List<Student>) request.getAttribute("listStudents");
                if (listStudents != null) {
                    for (Student s : listStudents) {
                %>
                    <tr>
                        <th scope="row"><%= cnt++ %></th>
                        <td><%= s.getName() %></td>
                        <td><%= s.getEmail() %></td>
                        <td><%= s.getMobile() %></td>
                        <td>
                            <a href="edit?id=<%= s.getId() %>" class="btn btn-info">
                                <i class="fa-solid fa-pen-to-square mx-1"></i>Update
                            </a>
                            <a href="delete?id=<%= s.getId() %>" class="btn btn-danger" onclick="return confirm('Are you sure to delete?');">
                                <i class="fa-solid fa-trash-can mx-1"></i>Delete
                            </a>
                        </td>
                    </tr>
                <%
                    }
                } else {
                %>
                    <tr><td colspan="5" class="text-center">No students found.</td></tr>
                <%
                }
                %>
            </tbody>
        </table>
    </div>

    <script
        src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>