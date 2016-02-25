/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author c0587637
 */
@ApplicationScoped
@ManagedBean
public class PostController {
    private List<Post> posts;

    public PostController() {
        updatePostsFromDatabase();
    }

    public List<Post> getPosts() {
        return posts;
    }

    private void updatePostsFromDatabase() {
        try {            
            posts = new ArrayList<>();
            // Make a Connection
            Connection conn = Utils.getConnection();

            // Build a Query
            String sql = "SELECT * FROM posts";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            // Parse the Results
            while (rs.next()) {
                Post p = new Post(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getString("title"),
                        rs.getTimestamp("created_time"),
                        rs.getString("contents")
                );
                posts.add(p);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PostController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
