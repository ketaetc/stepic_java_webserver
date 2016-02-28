package servlets;

import accounts.AccountService;
import accounts.UserProfile;
import com.google.gson.Gson;
import dbService.DBException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * edited by ketaetc
 */
public class SignUpServlet extends HttpServlet {
    private final AccountService accountService;

    public SignUpServlet(AccountService accountService) {
        this.accountService = accountService;
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("login");
        String pass = request.getParameter("pass");

        if (login == null) {
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().println("Login is empty");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        UserProfile newUserProfile = new UserProfile(login);
        try {
            accountService.addNewUser(newUserProfile);
        } catch (DBException e) {
            e.printStackTrace();
        }
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().println("User: " + newUserProfile.getLogin() + " registered successfully!");
        response.setStatus(HttpServletResponse.SC_OK);

    }

}
