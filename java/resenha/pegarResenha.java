package resenha;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.DAO;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;

@WebServlet("/pegarResenhas")
public class pegarResenha extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DAO dao = new DAO();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		try (Connection connection = dao.conectar();
				java.sql.Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery("SELECT * FROM resenhas")) {

			StringBuilder jsonBuilder = new StringBuilder();
			jsonBuilder.append("[");

			boolean first = true;
			while (resultSet.next()) {
				if (!first) {
					jsonBuilder.append(",");
				}

				jsonBuilder.append("{")
			    .append("\"id\":").append(resultSet.getInt("id")).append(",")
			    .append("\"url_foto\":\"").append(escapeJson(resultSet.getString("url_foto"))).append("\",")
			    .append("\"titulo\":\"").append(escapeJson(resultSet.getString("titulo"))).append("\",")
			    .append("\"idade_leitura\":\"").append(escapeJson(resultSet.getString("idade_leitura"))).append("\",")
			    .append("\"livro\":\"").append(escapeJson(resultSet.getString("livro"))).append("\",")
			    .append("\"autor\":\"").append(escapeJson(resultSet.getString("autor"))).append("\",")
			    .append("\"tradutor\":\"").append(escapeJson(resultSet.getString("tradutor"))).append("\",")
			    .append("\"generos\":\"").append(escapeJson(resultSet.getString("generos"))).append("\",")
			    .append("\"idioma\":\"").append(escapeJson(resultSet.getString("idioma"))).append("\",")
			    .append("\"editora\":\"").append(escapeJson(resultSet.getString("editora"))).append("\",")
			    .append("\"numero_paginas\":").append(resultSet.getInt("numero_paginas")).append(",")
			    .append("\"frase_destaque\":\"").append(escapeJson(resultSet.getString("frase_destaque"))).append("\",")
			    .append("\"conteudo_resenha\":\"").append(escapeJson(resultSet.getString("conteudo_resenha"))).append("\",")
			    .append("\"destaque_posicao\":").append(resultSet.getInt("destaque_posicao"))
			    .append("}");

				first = false;
			}

			jsonBuilder.append("]");

			PrintWriter out = response.getWriter();
			out.print(jsonBuilder.toString());
			out.flush();

		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}

	private String escapeJson(String value) {
		if (value == null) {
			return "";
		}
		return value.replace("\\", "\\\\") 
				.replace("\"", "\\\"") 
				.replace("\n", "\\n")
				.replace("\r", "\\r") 
				.replace("\t", "\\t"); 
	}
}