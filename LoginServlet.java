package company_management.servlet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import company_management.bean.messaggio;
import company_management.bean.userBean;
import java.lang.Boolean;
import java.lang.Object;
import java.util.ArrayList;
import company_management.db.SavwMySQL;

/**
 * servlet che gestisce la funzionalità di login dell'utenza
 * @author Antonio Pilato
 */
@WebServlet(name = "Login-Servlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String NickName = request.getParameter("NickName");
        String Password = request.getParameter("Password");
        System.out.println("NICKNAME: " + NickName);
        System.out.println("PASSWORD: " + Password);
        //controllo se esiste un nickname nel database uguale a quello inserito
        SavwMySQL DB = SavwMySQL.getInstance();
        ServletContext sc = request.getSession().getServletContext();
            try {
                String nickRes = DB.ifExistNick(NickName);
                String passRes = DB.ifExistPass(Password, NickName);
                if (nickRes.equals(NickName) && passRes.equals(Password) && !NickName.equals("") && !Password.equals("")) {
                    System.out.println("NICKNAME: " + nickRes + " PASSWORD: " + passRes);
                    System.out.println("NICKNAME-INS: " + NickName + "PASSOWRD-INS:" + Password);
                    userBean utente = new userBean();
                    utente = DB.utente(NickName);
                    request.getSession().setAttribute("UTENTE", utente);
                    request.getSession().setAttribute("NickName",utente.getNickName());
                    response.sendRedirect("/company_management_war_exploded/OpSuccesfully.jsp");
                    System.out.println("verifica account andata a buon fine, rimandare alla pagina iniziale");
                } else {
                    System.out.println("non esiste un account con questo nickname, rimandare al modulo di registrazione");
                    response.sendRedirect("/company_management_war_exploded/registrazione.jsp");
                }
            } catch (SQLException e) {
                System.out.println("ERROR: " + e.getErrorCode() + ":" + e.getMessage());
                e.printStackTrace();
                response.sendRedirect("/company_management_war_exploded/error.jsp");

        }
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
