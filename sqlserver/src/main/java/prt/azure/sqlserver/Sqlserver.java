/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package prt.azure.sqlserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import prt.azure.sqlserver.config.SimpleConnectionProvider;

import java.sql.*;

/**
 * @author gunda
 */
public class Sqlserver {

    private static Logger log = LoggerFactory.getLogger(Sqlserver.class);

    public static void main(String[] args) {

        String USER_SQL = "select * from employee;";
        String insertStatement = "insert into employee values (?, ?, ?)";
        try (Connection connection = SimpleConnectionProvider.getConnection();
             Statement statement = connection.createStatement()) {
            PreparedStatement preparedStatement = connection.prepareStatement(insertStatement);
            statement.execute(USER_SQL);
            ResultSet resultSet = statement.getResultSet();
            long i = 0;
            while (resultSet.next()) {
                i = resultSet.getLong(1);
                System.out.println(
                String.format("Row: %d, %s , %s ",
                        i ,
                        resultSet.getString(2),
                        resultSet.getString(3)));
            }
            i++;
            preparedStatement.setLong(1, i);
            preparedStatement.setString(2, "fname"+ i);
            preparedStatement.setString(3, "123-456-7890");
            preparedStatement.execute();
        } catch (Exception e) {
            log.error("Exception occurred ", e);
            e.printStackTrace();
        }
    }
}
