package lk.ijse.gdse.cielclothingstore.dao.custom.impl;

import lk.ijse.gdse.cielclothingstore.dao.custom.UserDAO;
import lk.ijse.gdse.cielclothingstore.db.DBConnection;
import lk.ijse.gdse.cielclothingstore.dto.UserDto;
import lk.ijse.gdse.cielclothingstore.dao.SQLUtil;
import lk.ijse.gdse.cielclothingstore.entity.UserAccount;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDAOImpl implements UserDAO{

    @Override
    public boolean save(UserAccount entity) throws SQLException {
        return SQLUtil.execute(
                "INSERT INTO UserAccount VALUES (?, ?, ?, ?, ?, ?)",
                entity.getUserID(),
                entity.getEmployeeID(),
                entity.getName(),
                entity.getRole(),
                entity.getPassword(),
                entity.getContactNo()
        );
    }

    @Override
    public ArrayList<UserAccount> getAll() throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM UserAccount");
        ArrayList<UserAccount> userDtoArrayList = new ArrayList<>();

        while (rst.next()) {
            userDtoArrayList.add(new UserAccount(
                    rst.getString("UserID"),
                    rst.getString("EmployeeID"),
                    rst.getString("Name"),
                    rst.getString("Role"),
                    rst.getString("Password"),
                    rst.getString("ContactNo")));
        }
        return userDtoArrayList;
    }

    @Override
    public boolean update(UserAccount entity) throws SQLException {
        return SQLUtil.execute(
                "UPDATE  customer SET EmployeeID=?, Name=?, Role=?, Password=?, ContactNo=? WHERE UserID=? ",
                entity.getEmployeeID(),
                entity.getName(),
                entity.getRole(),
                entity.getPassword(),
                entity.getContactNo(),
                entity.getUserID()

        );
    }

    @Override
    public boolean delete(String UserID) throws SQLException {
        return SQLUtil.execute("DELETE FROM UserAccount WHERE UserID=?", UserID);
    }

    @Override
    public ArrayList<String> getAllIds() throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT UserID FROM UserAccount");

        ArrayList<String> userIds = new ArrayList<>();

        while (rst.next()) {
            userIds.add(rst.getString(1));
        }
        return userIds;
    }

    public UserAccount findById(String selectedUserId) throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM UserAccount WHERE UserID=?", selectedUserId);
        rst.next();
        return new UserAccount(
                    rst.getString("UserID"),
                    rst.getString("EmployeeID"),
                    rst.getString("Name"),
                    rst.getString("Role"),
                    rst.getString("Password"),
                    rst.getString("ContactNo"));
    }

    public boolean isUsernameExists(String username) throws SQLException {
        String query = "SELECT COUNT(*) FROM UserAccount WHERE Name = ?";
        ResultSet resultSet = SQLUtil.execute(query, username);

        if (resultSet.next()) {
            return resultSet.getInt(1) > 0;
        }
        return false;
    }


    public boolean updatePassword(String username, String newPassword) throws SQLException {
        String query = "UPDATE UserAccount SET Password = ? WHERE Name = ?";
        return SQLUtil.execute(query, newPassword, username);
    }

    @Override
    public String getNextId() throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT UserID FROM UserAccount ORDER BY UserID DESC LIMIT 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            String substring = lastId.substring(1);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            return String.format("U%03d", newIdIndex);
        }
        return "U001";
    }

    @Override
    public boolean registeredUser(UserAccount entity) throws SQLException {
        return SQLUtil.execute(
                "INSERT INTO UserAccount (UserID, EmployeeID, Name, Role , Password , ContactNo) VALUES (?, ?, ?, ? ,? ,?)",
                entity.getUserID(),
                entity.getEmployeeID(),
                entity.getName(),
                entity.getRole(),
                entity.getPassword(),
                entity.getContactNo());
    }

    @Override
    public ArrayList<UserAccount> getCredentials(String Name, String Password) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM UserAccount WHERE Name = ? AND Password = ?";  // SQL query to select user details matching the provided username and password
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ArrayList<UserAccount> userDtoArrayList = new ArrayList<>();

        preparedStatement.setString(1, Name);
        preparedStatement.setString(2, Password);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {     // Loop through the results and create UserDto objects
            UserAccount entity = new UserAccount(); // Create a new UserDto instance
            entity.setName(resultSet.getString("Name")); // Set the username
            entity.setPassword(resultSet.getString("Password")); // Set the password
            entity.setRole(resultSet.getString("Role")); // Set the user's role (Cashier or Manager)
            userDtoArrayList.add(entity); // Add the UserDto to the list
        }
        return userDtoArrayList;   // Return the list of UserDto objects
    }
}
