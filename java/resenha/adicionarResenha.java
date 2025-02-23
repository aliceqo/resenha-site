package resenha;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import model.DAO;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet("/adicionar")
@MultipartConfig
public class adicionarResenha extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DAO dao = new DAO();

	@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
  
        String titulo = request.getParameter("titulo");
        String idadeLeitura = request.getParameter("idade_leitura");
        String livro = request.getParameter("livro");
        String autor = request.getParameter("autor");
        String tradutor = request.getParameter("tradutor");
        String generos = request.getParameter("generos");
        String idioma = request.getParameter("idioma");
        String editora = request.getParameter("editora");
        String numeroPaginas = request.getParameter("numero_paginas");
        String fraseDestaque = request.getParameter("frase_destaque");
        String conteudoResenha = request.getParameter("conteudo_resenha");

        Part filePart = request.getPart("url_foto");
        String urlFoto = "";
        if (filePart != null && filePart.getSize() > 0) {
            String fileName = filePart.getSubmittedFileName();
            InputStream fileContent = filePart.getInputStream();
            urlFoto = processFile(fileName, fileContent); 
        }

		try (Connection connection = dao.conectar()) {
			String sql = "INSERT INTO resenhas (url_foto, titulo, idade_leitura, livro, autor, tradutor, generos, idioma, editora, numero_paginas, frase_destaque, conteudo_resenha) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			try (PreparedStatement statement = connection.prepareStatement(sql)) {
				statement.setString(1, urlFoto);
				statement.setString(2, titulo);
				statement.setString(3, idadeLeitura);
				statement.setString(4, livro);
				statement.setString(5, autor);
				statement.setString(6, tradutor);
				statement.setString(7, generos);
				statement.setString(8, idioma);
				statement.setString(9, editora);
				statement.setInt(10, Integer.parseInt(numeroPaginas));
				statement.setString(11, fraseDestaque);
				statement.setString(12, conteudoResenha);

				int rowsAffected = statement.executeUpdate();
				if (rowsAffected > 0) {
					criarPaginaHtml(titulo, urlFoto, fraseDestaque, conteudoResenha, idadeLeitura, autor, tradutor, generos, idioma, editora, Integer.parseInt(numeroPaginas));

					response.sendRedirect("/back-end/html/administrador/indexAdm.html");
				} else {
					response.sendRedirect("erro.html");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			response.sendRedirect("erro.html");
		}
	}

	private void criarPaginaHtml(String titulo, String urlFoto, String fraseDestaque, String conteudoResenha, String idadeLeitura, String autor, String tradutor, String generos, String idioma, String editora, int numeroPaginas)
			throws IOException {
		String basePath = getServletContext().getRealPath("/html/resenhas/");
		File dir = new File(basePath);

		if (!dir.exists()) {
			boolean created = dir.mkdirs();
			if (!created) {
				throw new IOException("Não foi possível criar o diretório: " + basePath);
			}
		}

		File file = new File(basePath + File.separator + titulo.toLowerCase().replaceAll("\\s+", "-") + ".html");
		try (PrintWriter writer = new PrintWriter(new FileOutputStream(file))) {
			writer.println("<!DOCTYPE html>");
			writer.println("<html lang=\"pt-br\">");
			writer.println("<head>");
			writer.println("    <meta charset=\"UTF-8\">");
			writer.println("    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">");
			writer.println(
					"    <link rel=\"shortcut icon\" href=\"../../imagens/icone/book.png\" type=\"image/x-icon\">");
			writer.println("    <link rel=\"stylesheet\" href=\"../../css/stylesResenha.css\">");
			writer.println("    <link rel=\"preconnect\" href=\"https://fonts.googleapis.com\">");
			writer.println("    <link rel=\"preconnect\" href=\"https://fonts.gstatic.com\">");
			writer.println(
					"    <link href=\"https://fonts.googleapis.com/css2?family=Dancing+Script:wght@400..700&display=swap\" rel=\"stylesheet\">");
			writer.println("    <title>" + titulo + "</title>");
			writer.println("</head>");
			writer.println("<body>");
			writer.println("    <div id=\"page-container\" class=\"page-content\">");
			writer.println("        <header>");
			writer.println("            <h1>Resenhas de Livros</h1>");
			writer.println("            <nav>");
			writer.println("                <ul>");
			writer.println("                    <li><a href=\"../index.html\">Início</a></li>");
			writer.println("                    <li><a href=\"../resenhas.html\">Resenhas</a></li>");
			writer.println("                    <li><a href=\"../administrador/login.html\">Administrador</a></li>");
			writer.println("                </ul>");
			writer.println("                <form id=\"searchForm\">");
			writer.println(
					"                    <input type=\"text\" id=\"searchInput\" name=\"searchInput\" placeholder=\"Buscar resenhas...\">");
			writer.println("                    <button type=\"submit\" id=\"submit\">Pesquisar</button>");
			writer.println("                    <div id=\"resultado\"></div>");
			writer.println("                </form>");
			writer.println("            </nav>");
			writer.println("        </header>");
			writer.println("        <section class=\"informacoes\">");
			writer.println("            <h1>" + titulo + "</h1>");
			writer.println("            <img src=\"" + urlFoto + "\" alt=\"" + titulo + "\">");
			writer.println("            <p><strong>Idade de leitura:</strong> " + idadeLeitura + "</p>");
			writer.println("            <p><strong>Autor:</strong> " + autor + "</p>");
			writer.println("            <p><strong>Tradutor:</strong> " + tradutor + "</p>");
			writer.println("            <p><strong>Gêneros:</strong> " + generos + "</p>");
			writer.println("            <p><strong>Idioma:</strong> " + idioma + "</p>");
			writer.println("            <p><strong>Editora:</strong> " + editora + "</p>");
			writer.println("            <p><strong>Número de páginas:</strong> " + numeroPaginas + "</p>");
			writer.println("        </section>");
			writer.println("        <section class=\"resenha\">");
			writer.println("            <h1>\"" + fraseDestaque + "\"</h1>");
			writer.println("            <p>" + conteudoResenha + "</p>");
			writer.println("        </section>");
			writer.println("    </div>");
			writer.println("    <script src=\"../../javaScript/scriptsResenhas.js\" defer></script>");
			writer.println("</body>");
			writer.println("</html>");
		} catch (IOException e) {
			throw new IOException("Não foi possível criar a página HTML: " + e);
		}
	}

	private String processFile(String fileName, InputStream fileContent) throws IOException {
		String basePath = getServletContext().getRealPath("/imagens/livros/");
		File dir = new File(basePath);

		if (!dir.exists()) {
			boolean created = dir.mkdirs();
			if (!created) {
				throw new IOException("Não foi possível criar o diretório: " + basePath);
			}
		}

		File file = new File(basePath + File.separator + fileName);
		if (!file.exists()) {
			try (FileOutputStream os = new FileOutputStream(file)) {
				byte[] buffer = new byte[1024];
				int bytesRead;
				while ((bytesRead = fileContent.read(buffer)) != -1) {
					os.write(buffer, 0, bytesRead);
				}
			} catch (IOException e) {
				throw new IOException("Não foi possível salvar a imagem" + e);
			}
		}

		return "/back-end/imagens/livros/" + fileName;
	}
}