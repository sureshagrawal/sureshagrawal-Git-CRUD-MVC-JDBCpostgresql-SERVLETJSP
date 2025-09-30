package com.nsgacademy.crudmvc.web;

import com.nsgacademy.crudmvc.dao.StudentDAO;
import com.nsgacademy.crudmvc.exception.DAOException;
import com.nsgacademy.crudmvc.model.Student;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/")
public class StudentServlet extends HttpServlet {
    private StudentDAO studentDAO;

    public void init() {
        studentDAO = new StudentDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getServletPath();
        try {
            switch (action) {
                case "/new": showNewForm(request, response); break;
                case "/insert": insertStudent(request, response); break;
                case "/delete": deleteStudent(request, response); break;
                case "/edit": showEditForm(request, response); break;
                case "/update": updateStudent(request, response); break;
                default: listStudents(request, response); break;
            }
        } catch (DAOException ex) {
            ex.printStackTrace();
            request.setAttribute("errorMessage", ex.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }

    private void listStudents(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, DAOException {
        List<Student> students = studentDAO.selectAllStudents();
        request.setAttribute("listStudents", students);
        request.getRequestDispatcher("student-list.jsp").forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("student-form.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, DAOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Student existingStudent = studentDAO.selectStudent(id);
        request.setAttribute("student", existingStudent);
        request.getRequestDispatcher("student-form.jsp").forward(request, response);
    }

//    private void insertStudent(HttpServletRequest request, HttpServletResponse response) throws IOException, DAOException {
//        String name = request.getParameter("name");
//        String email = request.getParameter("email");
//        String mobile = request.getParameter("mobile");
//
//        if (validateStudent(name, email, mobile)) {
//            studentDAO.insertStudent(new Student(name, email, mobile));
//            response.sendRedirect("list?success=Added Successfully");
//        } else {
//            response.sendRedirect("new?error=Validation Failed");
//        }
//    }

    private void insertStudent(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, DAOException {

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String mobile = request.getParameter("mobile");

        Student student = new Student(name, email, mobile);
        String error = validateForm(name, email, mobile);

        if (error == null) {
            studentDAO.insertStudent(student);
            response.sendRedirect("list?success=Added Successfully");
        } else {
            request.setAttribute("error", error);
            request.setAttribute("student", student);
            request.getRequestDispatcher("student-form.jsp").forward(request, response);
        }
    }


//    private void updateStudent(HttpServletRequest request, HttpServletResponse response) throws IOException, DAOException {
//        int id = Integer.parseInt(request.getParameter("id"));
//        String name = request.getParameter("name");
//        String email = request.getParameter("email");
//        String mobile = request.getParameter("mobile");
//
//        if (validateStudent(name, email, mobile)) {
//            studentDAO.updateStudent(new Student(id, name, email, mobile));
//            response.sendRedirect("list?success=Updated Successfully");
//        } else {
//            response.sendRedirect("edit?id=" + id + "&error=Validation Failed");
//        }
//    }


    private void updateStudent(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, DAOException {

        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String mobile = request.getParameter("mobile");

        Student student = new Student(id, name, email, mobile);
        String error = validateForm(name, email, mobile);

        if (error == null) {
            studentDAO.updateStudent(student);
            response.sendRedirect("list?success=Updated Successfully");
        } else {
            request.setAttribute("error", error);
            request.setAttribute("student", student);
            request.getRequestDispatcher("student-form.jsp").forward(request, response);
        }
    }


    private void deleteStudent(HttpServletRequest request, HttpServletResponse response) throws IOException, DAOException {
        int id = Integer.parseInt(request.getParameter("id"));
        studentDAO.deleteStudent(id);
        response.sendRedirect("list?success=Deleted Successfully");
    }

    private String validateForm(String name, String email, String mobile) {
        if (name == null || name.trim().isEmpty()) {
            return "Name is required.";
        } else if (email == null || !email.contains("@")) {
            return "Valid email is required.";
        } else if (mobile == null || !mobile.matches("\\d{10}")) {
            return "Mobile number must be 10 digits.";
        }
        return null;
    }


//    private boolean validateStudent(String name, String email, String mobile) {
//        return name != null && !name.trim().isEmpty()
//                && email != null && email.contains("@")
//                && mobile != null && mobile.matches("\\d{10}");
//    }
}
