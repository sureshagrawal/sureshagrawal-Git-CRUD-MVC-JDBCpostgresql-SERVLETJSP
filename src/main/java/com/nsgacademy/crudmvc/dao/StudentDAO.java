package com.nsgacademy.crudmvc.dao;

import com.nsgacademy.crudmvc.exception.DAOException;
import com.nsgacademy.crudmvc.model.Student;
import com.nsgacademy.crudmvc.utils.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {
    private static final String INSERT_SQL = "INSERT INTO student(name, email, mobile) VALUES (?, ?, ?)";
    private static final String SELECT_BY_ID_SQL = "SELECT * FROM student WHERE id=?";
    private static final String SELECT_ALL_SQL = "SELECT * FROM student";
    private static final String DELETE_SQL = "DELETE FROM student WHERE id=?";
    private static final String UPDATE_SQL = "UPDATE student SET name=?, email=?, mobile=? WHERE id=?";

    public void insertStudent(Student student) throws DAOException {
        try (Connection conn = JDBCUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(INSERT_SQL)) {
            stmt.setString(1, student.getName());
            stmt.setString(2, student.getEmail());
            stmt.setString(3, student.getMobile());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error inserting student", e);
        }
    }

    public Student selectStudent(int id) throws DAOException {
        Student student = null;
        try (Connection conn = JDBCUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_BY_ID_SQL)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    student = new Student(id, rs.getString("name"), rs.getString("email"), rs.getString("mobile"));
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Error selecting student by id", e);
        }
        return student;
    }

    public List<Student> selectAllStudents() throws DAOException {
        List<Student> students = new ArrayList<>();
        try (Connection conn = JDBCUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_ALL_SQL);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                students.add(new Student(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("mobile")));
            }
        } catch (SQLException e) {
            throw new DAOException("Error selecting all students", e);
        }
        return students;
    }

    public boolean deleteStudent(int id) throws DAOException {
        try (Connection conn = JDBCUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(DELETE_SQL)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DAOException("Error deleting student", e);
        }
    }

    public boolean updateStudent(Student student) throws DAOException {
        try (Connection conn = JDBCUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(UPDATE_SQL)) {
            stmt.setString(1, student.getName());
            stmt.setString(2, student.getEmail());
            stmt.setString(3, student.getMobile());
            stmt.setInt(4, student.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DAOException("Error updating student", e);
        }
    }
}
