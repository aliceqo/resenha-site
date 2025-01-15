package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.DAO;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private DAO dao = new DAO();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        response.setContentType("text/html");
        response.getWriter();

        if (validateUser(username, password)) {
            response.setStatus(HttpServletResponse.SC_OK); 
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); 
        }
    }

    private boolean validateUser(String username, String password) {
        String query = "SELECT * FROM adm WHERE usuario = ? AND senha = ?";
        try (Connection connection = dao.conectar();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next(); 
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}