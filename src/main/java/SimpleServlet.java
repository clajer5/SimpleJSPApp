import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/SimpleServlet")
public class SimpleServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    public SimpleServlet() {
    	   // Constructor sin argumentos
    	}
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Mostrar la página index.jsp al realizar una solicitud GET
        RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
        dispatcher.forward(request, response);
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String nombre = request.getParameter("nombre");
        String email = request.getParameter("email");

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            // Establecer la conexión con la base de datos
        	Class.forName("com.mysql.cj.jdbc.Driver"); 
        	conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/simplejspdb", "root", ""); // Reemplazar con la URL de conexión, usuario y contraseña de tu base de datos
//            System.out.println(System.getProperty("java.class.path"));
            // Insertar datos en la base de datos
            String query = "INSERT INTO usuarios (nombre, email) VALUES (?, ?)";
            stmt = conn.prepareStatement(query);
            System.out.println("Conectado correctamente a la Base de Datos");
            stmt.setString(1, nombre);
            stmt.setString(2, email);
            stmt.executeUpdate();

            // Redirigir a la página de éxito
            response.sendRedirect("success.jsp");
        } catch ( SQLException e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        } catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

