package smartdrive;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBConnection {

    private static final String URL =
            "jdbc:mysql://localhost:3306/smartdrive_rentals?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public boolean saveCar(Car car) {
        String sql = "INSERT INTO cars (brand, model, year, price) VALUES (?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, car.getBrand());
            pstmt.setString(2, car.getModel());
            pstmt.setInt(3, car.getYear());
            pstmt.setDouble(4, car.getPrice());

            int rowsAffected = pstmt.executeUpdate();
            System.out.println("rowsAffected = " + rowsAffected);
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error saving car: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

 public String getAllCarsAsText() {
    StringBuilder sb = new StringBuilder();
    String sql = "SELECT brand, model, year, price FROM cars";

    try (Connection conn = getConnection();
         PreparedStatement ps = conn.prepareStatement(sql);
         var rs = ps.executeQuery()) {

        while (rs.next()) {
            sb.append("Brand: ").append(rs.getString("brand"))
              .append(", Model: ").append(rs.getString("model"))
              .append(", Year: ").append(rs.getInt("year"))
              .append(", Price: ").append(rs.getDouble("price"))
              .append("\n");
        }

    } catch (SQLException e) {
        return "Error reading cars: " + e.getMessage();
    }

    if (sb.length() == 0) {
        return "No cars found in database.";
    }
    return sb.toString();
}

}