/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author c0587637
 */
@WebServlet("/post")
public class PostServlet extends HttpServlet {
    // /post --> Get Whole List
    // /post?id=XXX --> Get Just ID
    // /post?author=XXX --> Get By UserID
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) {
        PrintWriter out = null;
        try {
            String strId = req.getParameter("id");
            String strUserId = req.getParameter("author");
            out = res.getWriter();
            if (strId != null) {
                // Filter By Id
                int id = Integer.parseInt(strId);
                Post post = new PostController().getPostById(id);
                out.println(post);
            }
            else if (strUserId != null) {
                // Display the Whole List
                int userId = Integer.parseInt(strUserId);
                List<Post> posts = new PostController().getPostsByUserId(userId);
                for (Post p : posts) {
                    out.println(p);
                }
            }
            else {
                // Display the Whole List
                List<Post> posts = new PostController().getPosts();
                for (Post p : posts) {
                    out.println(p);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(PostServlet.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            out.close();
        }
    }
}
