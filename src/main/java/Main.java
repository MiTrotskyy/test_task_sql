import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        new Main().run();
    }

    private final String DATA_BASE_URL = "jdbc:postgresql://localhost:5432/studs";
    // USERNAME and PASSWORD for database connection
    private final String USER = "*****";
    private final String PASSWORD = "*****";

    private void run() {
        try {
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(DATA_BASE_URL, USER, PASSWORD);
//            System.out.println("Successfully connected");
            connection.setAutoCommit(false);
            String sql = "SELECT DEPARTMENT, SALARY FROM EMPLOYEES;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            HashMap<String, Integer> ans = new HashMap<>();
            while (resultSet.next()) {
                String department = resultSet.getString("DEPARTMENT");
                int salary = resultSet.getInt("SALARY");
                if (ans.containsKey(department)) {
                    int cur = ans.remove(department);
                    ans.put(department, cur + salary);
                } else {
                    ans.put(department, salary);
                }
            }
            for (Map.Entry<String, Integer> entry : ans.entrySet()) {
                System.out.println(entry.getKey() + " : " + entry.getValue());
            }

        } catch (ClassNotFoundException e) {
            System.out.println("Driver not found");
		    e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("SQL exception");
e.printStackTrace();
        }
    }
}
