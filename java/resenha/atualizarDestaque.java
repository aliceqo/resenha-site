package resenha;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.DAO;

@WebServlet("/atualizarDestaque")
public class atualizarDestaque extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String livroId = request.getParameter("livroId");
        String posicao = request.getParameter("posicao");

        if (livroId == null || livroId.isEmpty() || posicao == null || posicao.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Parâmetros inválidos.");
            return;
        }

        DAO dao = new DAO();
        try (Connection conn = dao.conectar()) {
            if (conn == null) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write("Falha na conexão com o banco de dados.");
                return;
            }
            
            String sql = "UPDATE resenhas SET destaque_posicao = ? WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                if (posicao.matches("\\d+")) { 
                    stmt.setInt(1, Integer.parseInt(posicao)); 
                } else {
                    stmt.setString(1, posicao); 
                }
                stmt.setString(2, livroId); 

                int rowsUpdated = stmt.executeUpdate();

                if (rowsUpdated > 0) {
                    response.setStatus(HttpServletResponse.SC_OK);
                    response.getWriter().write("Destaque atualizado com sucesso.");
                } else {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    response.getWriter().write("Erro ao atualizar destaque.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace(); 
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Erro no servidor: " + e.getMessage());
        }
    }
}