package company_management.servlet;

import company_management.bean.userBean;
import company_management.db.SavwMySQL;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * servlet che gestisce la funzionalità di login dell'amministrazione
 * @author Antonio Pilato
 */
@WebServlet(name = "LoginAdmin-Servlet")
public class LoginAdminServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String NickName = request.getParameter("NickName");
        String Password = request.getParameter("Password");
        System.out.println("NICKNAME AMMINISTRATORE: " + NickName);
        System.out.println("PASSWORD AMMINISTRATORE: " + Password);
        //controllo se esiste un nickname nel database uguale a quello inserito
        SavwMySQL DB = SavwMySQL.getInstance();
        ServletContext sc = request.getSession().getServletContext();
        try {
            String nickRes = DB.ifExistNickAdmin(NickName);
            String passRes = DB.ifExistPassAdmin(Password, NickName);
            if (nickRes.equals(NickName) && passRes.equals(Password) && !NickName.equals("") && !Password.equals("")) {
                System.out.println("NICKNAME ADMIN: " + nickRes + " PASSWORD ADMIN: " + passRes);
                System.out.println("NICKNAME ADMIN -INS: " + NickName + "PASSOWRD ADMIN -INS:" + Password);
                userBean utente = new userBean();
                utente = DB.utente(NickName);
                request.getSession().setAttribute("UTENTEADMIN", utente);
                request.getSession().setAttribute("NickNameAdmin",utente.getNickName());
                response.sendRedirect("/company_management_war_exploded/HomePageAdministration.jsp");
                System.out.println("verifica account amministratore andata a buon fine, rimandare alla pagina iniziale");
            } else {
                System.out.println("non esiste un account amministratore  con questo nickname");
                response.sendRedirect("/company_management_war_exploded/administrationLogin.jsp");
            }
        } catch (SQLException e) {
            System.out.println("ERROR: " + e.getErrorCode() + ":" + e.getMessage());
            e.printStackTrace();
            response.sendRedirect("/company_management_war_exploded/administrationLogin.jsp");

        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
