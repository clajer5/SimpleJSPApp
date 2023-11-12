<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Simple JSP App</title>
</head>
<body>
    <h2>Registrar Usuario</h2>
    <form action="SimpleServlet" method="post">
        Nombre: <input type="text" name="nombre" required><br>
        Email: <input type="email" name="email" required><br>
        <input type="submit" value="Guardar">
    </form>
    
     <h2>Listado de Usuarios Registrados</h2>
    <% 
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Reemplazar "nombre_del_driver" con el nombre del driver de tu base de datos
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/simplejspdb", "root", ""); // Reemplazar con la URL de conexión, usuario y contraseña de tu base de datos

            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM usuarios");

            while (rs.next()) {
                out.println("ID: " + rs.getInt("id") + "<br>");
                out.println("Nombre: " + rs.getString("nombre") + "<br>");
                out.println("Email: " + rs.getString("email") + "<br><br>");
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
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
    %>
</body>
</html>