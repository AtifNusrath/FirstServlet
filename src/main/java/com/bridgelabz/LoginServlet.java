package com.bridgelabz;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Pattern;

@WebServlet(
        description = "Login Servlet Testing",
        urlPatterns = {"/LoginServlet"},
        initParams = {
                @WebInitParam(name="user", value="Atif"),
                @WebInitParam(name="password",value = "Mohammed@22")
        }
)
public class LoginServlet extends HttpServlet {
    private static final String FIRST_NAME_PATTERN="^[A-Z]{1}[a-zA-Z]{2}[a-zA-Z]$";
    private static final String passwordRegex="^(?=.*[@#$%^&+=])(?=.*[0-9])(?=.*[A-Z]).{8,}$";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {

        String user = req.getParameter("user");
        String pwd = req.getParameter("pwd");
        boolean validateUserName = validateFirstName(user);
        boolean userFirstName = userFirstName(req, res, validateUserName);
        boolean validatePassword = validatePassword(pwd);
        boolean userPassword = userPassword(req, res, validatePassword);
        String userID = getServletConfig().getInitParameter("user");
        String password = getServletConfig().getInitParameter("password");
        if (userFirstName == true &&  userPassword == true) {
            if (userID.equals(user) && password.equals(pwd)) {
                req.setAttribute("user", user);
                req.getRequestDispatcher("LoginSuccess.jsp").forward(req, res);
            } else {
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/login.jsp");
                PrintWriter out = res.getWriter();
                out.println("<font color= red>Incorrect Credentials</font>");
                rd.include(req, res);
            }
        }
    }

    private boolean userFirstName(HttpServletRequest request, HttpServletResponse response, boolean validateUserName)
            throws IOException, ServletException {
        if (validateUserName == false) {
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/login.jsp");
            PrintWriter out = response.getWriter();
            out.println("<font color= red>Invalid name: start with capital and minimum 3 character</font>");
            rd.include(request, response);
            return false;
        }
        return true;
    }

    private boolean userPassword(HttpServletRequest request, HttpServletResponse response, boolean validatePassword)
            throws IOException, ServletException {
        if (validatePassword == false) {
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/login.jsp");
            PrintWriter out = response.getWriter();
            out.println("<font color= red>Incorrect Password:</font>");
            rd.include(request, response);
            return false;
        }
        return true;
    }

    public boolean validateFirstName(String userName) {
        Pattern check = Pattern.compile(FIRST_NAME_PATTERN);
        boolean value = check.matcher(userName).matches();
        return value;
    }

    public boolean validatePassword(String password) {
        Pattern check = Pattern.compile(passwordRegex);
        boolean value = check.matcher(password).matches();
        return value;
    }
}

