package pesquisa;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import model.DAO;

@WebServlet("/buscar")
public class pesquisarHTML extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private DAO dao = new DAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String query = request.getParameter("searchInput");

        response.setContentType("text/html");
        StringBuilder resultHtml = new StringBuilder();
     
        try {
            Connection connection = dao.conectar();
            String sql = "SELECT titulo FROM resenhas WHERE titulo LIKE ? LIMIT 5";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, "%" + query + "%");
            ResultSet resultSet = statement.executeQuery();
            
            
            while (resultSet.next()) {
                String titulo = resultSet.getString("titulo");
                String urlFriendlyTitle = createUrlFriendlyString(titulo);
                
                	resultHtml.append("<div><a href='resenhas/")
                	.append(urlFriendlyTitle).append(".html'>")
                	.append(titulo).append("</a></div>");
            }
            
            if (resultHtml.length() == 0) {
                resultHtml.append("<p>Nenhum resultado encontrado.</p>");
            }

            response.getWriter().println(resultHtml.toString());
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("<p>Erro ao realizar a busca.</p>");
        }
    }

    private String createUrlFriendlyString(String input) {
        String normalized = java.text.Normalizer.normalize(input, java.text.Normalizer.Form.NFD);
        String urlFriendly = normalized.replaceAll("[^\\p{ASCII}]", ""); 
        urlFriendly = urlFriendly.toLowerCase().replace(" ", "-"); 
        urlFriendly = urlFriendly.replaceAll("[^a-z0-9-]", "");
        return urlFriendly;
    }
}