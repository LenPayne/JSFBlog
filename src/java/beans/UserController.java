/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author c0587637
 */
@ApplicationScoped
@ManagedBean
public class UserController {

    private List<User> users;

    public UserController() {
        updateUsersFromDatabase();
    }

    public List<User> getUsers() {
        return users;
    }
    
    public String getUsernameById(int id) {
        for (User u : users) {
            if (u.getId() == id)
                return u.getUsername();
        }
        return null;
    }

    private void updateUsersFromDatabase() {
        try {            
            users = new ArrayList<>();
            // Make a Connection
            Connection conn = Utils.getConnection();

            // Build a Query
            String sql = "SELECT * FROM users";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            // Parse the Results
            while (rs.next()) {
                User u = new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("passhash")
                );
                users.add(u);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
