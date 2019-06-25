package company_management.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import company_management.bean.userBean;
import company_management.db.SavwMySQL;

/**
 * servlet che gestisce la funzionalità di registrazione dell'utenza.
 * @author Antonio Pilato
 */
@WebServlet(name = "registrazione-Servlet")
public class registrazioneServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String NickName = request.getParameter("NickName");
        String Password = request.getParameter("Password");
        String Nome = request.getParameter("Nome");
        String Cognome = request.getParameter("Cognome");
        String Email = request.getParameter("Email");
        String Telefono = request.getParameter("Telefono");
        System.out.println("NICKNAME: " + NickName);
        System.out.println("PASSWORD: " + Password);
        System.out.println("NOME: "+Nome);
        System.out.println("COGNOME: "+Cognome);
        System.out.println("EMAIL: "+Email);
        System.out.println("TELEFONO "+Telefono);
        userBean user = new userBean();
        user.setNickName(NickName);
        user.setPassoword(Password);
        user.setNome(Nome);
        user.setCognome(Cognome);
        user.setEmail(Email);
        user.setTelefono(Telefono);
        SavwMySQL DB =SavwMySQL.getInstance();
        try {
            DB.InsertUser(user);
            DB.insertFollower(NickName, NickName);
        }catch (SQLException sqle){
            System.out.println("ERROR:"+sqle.getErrorCode()+":"+sqle.getMessage());
            sqle.printStackTrace();
        }
        response.sendRedirect("/company_management_war_exploded/login.jsp");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
