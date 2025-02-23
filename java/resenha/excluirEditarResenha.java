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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet("/excluirEditar")
@MultipartConfig
public class excluirEditarResenha extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private DAO dao = new DAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("id");

        if (id == null || id.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID da resenha não fornecido");
            return;
        }

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            conn = dao.conectar(); 
            String query = "SELECT * FROM resenhas WHERE id = ?";
            pstmt = conn.prepareStatement(query); 
            pstmt.setString(1, id);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                StringBuilder json = new StringBuilder();
                json.append("{");
                json.append("\"url_foto\":\"").append(escapeJson(rs.getString("url_foto"))).append("\",");
                json.append("\"titulo\":\"").append(escapeJson(rs.getString("titulo"))).append("\",");
                json.append("\"idade_leitura\":\"").append(escapeJson(rs.getString("idade_leitura"))).append("\",");
                json.append("\"livro\":\"").append(escapeJson(rs.getString("livro"))).append("\",");
                json.append("\"autor\":\"").append(escapeJson(rs.getString("autor"))).append("\",");
                json.append("\"tradutor\":\"").append(escapeJson(rs.getString("tradutor"))).append("\",");
                json.append("\"generos\":\"").append(escapeJson(rs.getString("generos"))).append("\",");
                json.append("\"idioma\":\"").append(escapeJson(rs.getString("idioma"))).append("\",");
                json.append("\"editora\":\"").append(escapeJson(rs.getString("editora"))).append("\",");
                json.append("\"numero_paginas\":").append(rs.getInt("numero_paginas")).append(",");
                json.append("\"frase_destaque\":\"").append(escapeJson(rs.getString("frase_destaque"))).append("\",");
                json.append("\"conteudo_resenha\":\"").append(escapeJson(rs.getString("conteudo_resenha"))).append("\"");
                json.append("}");

                response.getWriter().write(json.toString());
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("{\"error\": \"Resenha não encontrada\"}");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\": \"Erro interno do servidor\"}");
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private String escapeJson(String value) {
        if (value == null) return "";
        return value.replace("\\", "\\\\") 
                .replace("\"", "\\\"") 
                .replace("\b", "\\b")  
                .replace("\f", "\\f")   
                .replace("\n", "\\n")   
                .replace("\r", "\\r")   
                .replace("\t", "\\t"); 
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("doPost chamada");

        String idParam = request.getParameter("resenhaId");

        if (idParam == null || idParam.isEmpty()) {
            System.out.println("resenhaId é nulo ou vazio");
            response.setContentType("text/plain");
            response.getWriter().write("ID da resenha não fornecido.");
            return;
        }

        int id = Integer.parseInt(idParam);

        String titulo = request.getParameter("titulo");
        String idade_leitura = request.getParameter("idade_leitura");
        String livro = request.getParameter("livro");
        String autor = request.getParameter("autor");
        String tradutor = request.getParameter("tradutor");
        String generos = request.getParameter("generos");
        String idioma = request.getParameter("idioma");
        String editora = request.getParameter("editora");

        int numero_paginas;
        try {
            numero_paginas = Integer.parseInt(request.getParameter("numero_paginas"));
        } catch (NumberFormatException e) {
            response.setContentType("text/plain");
            response.getWriter().write("Número de páginas inválido.");
            return;
        }

        Part filePart = request.getPart("url_foto");
        String url_foto = "";
        if (filePart != null && filePart.getSize() > 0) {
            String fileName = filePart.getSubmittedFileName();
            InputStream fileContent = filePart.getInputStream();
            url_foto = processFile(fileName, fileContent);
        } 

        String frase_destaque = request.getParameter("frase_destaque");
        String conteudo_resenha = request.getParameter("conteudo_resenha");

        try (Connection connection = dao.conectar()) {
            String sql = "UPDATE resenhas SET url_foto = ?, titulo = ?, idade_leitura = ?, livro = ?, autor = ?, tradutor = ?, "
                    + "generos = ?, idioma = ?, editora = ?, numero_paginas = ?, frase_destaque = ?, conteudo_resenha = ? WHERE id = ?";

            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, url_foto);
                statement.setString(2, titulo);
                statement.setString(3, idade_leitura);
                statement.setString(4, livro);
                statement.setString(5, autor);
                statement.setString(6, tradutor);
                statement.setString(7, generos);
                statement.setString(8, idioma);
                statement.setString(9, editora);
                statement.setInt(10, numero_paginas);
                statement.setString(11, frase_destaque);
                statement.setString(12, conteudo_resenha);
                statement.setInt(13, id);

                int rowsAffected = statement.executeUpdate();
                if (rowsAffected > 0) {
                    response.setContentType("text/plain");
                    response.getWriter().write("Resenha editada com sucesso.");
                } else {
                    response.setContentType("text/plain");
                    response.getWriter().write("Nenhuma resenha encontrada para o ID fornecido.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.setContentType("text/plain");
            response.getWriter().write("Erro ao editar a resenha.");
        }
    }


    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idParam = request.getParameter("id");
        
        if (idParam == null || idParam.isEmpty()) {
            response.setContentType("text/plain");
            response.getWriter().write("ID da resenha não fornecido.");
            return;
        }

        int id = Integer.parseInt(idParam);

        try (Connection connection = dao.conectar()) {
            String sql = "DELETE FROM resenhas WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, id);
                int rowsAffected = statement.executeUpdate();
                if (rowsAffected > 0) {
                    response.setContentType("text/plain");
                    response.getWriter().write("Resenha excluída com sucesso.");
                } else {
                    response.setContentType("text/plain");
                    response.getWriter().write("Nenhuma resenha encontrada para o ID fornecido.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.setContentType("text/plain");
            response.getWriter().write("Erro ao excluir a resenha.");
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