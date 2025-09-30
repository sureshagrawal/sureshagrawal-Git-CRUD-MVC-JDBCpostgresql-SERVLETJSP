<%@ page isErrorPage="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Error</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-5 text-center">
        <h1 class="text-danger">Something Went Wrong</h1>
        <p class="lead">
            <%
                if (exception != null) {
                    out.print(exception.getMessage());
                } else {
                    String errorMessage = (String) request.getAttribute("errorMessage");
                    if (errorMessage != null) {
                        out.print(errorMessage);
                    } else {
                        out.print("An internal error occurred. Please try again later or contact support.");
                    }
                }
            %>
        </p>
        <a href="list" class="btn btn-primary">Go Back</a>
    </div>
</body>
</html>
