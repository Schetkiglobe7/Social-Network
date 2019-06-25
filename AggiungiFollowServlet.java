package company_management.servlet;

import company_management.db.SavwMySQL;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * servlet che gestisce l'aggiunta di un follower.
 * @author Antonio Pilato
 */
@WebServlet(name = "AggiungiFollow-Servlet")
public class AggiungiFollowServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SavwMySQL DB = SavwMySQL.getInstance();
        String MioNick = (String)request.getParameter("mioNick");
        String nickRicercato = (String)request.getParameter("nickRicercato");
        System.out.println("AGGIUNGI_FOLLOW_SERVLET: Nick ricercato: "+nickRicercato);
        System.out.println("AGGIUNGI FOLLOW SERVLET: Nick Del profilo in uso: "+MioNick);
        try {
            DB.insertFollower(MioNick, nickRicercato);
            System.out.println("utente: " + nickRicercato + " aggiunto alla lista follower di: " + MioNick);
            response.sendRedirect("/company_management_war_exploded/HomePage.jsp");
        }catch (SQLException e) {
            System.out.println("ERROR: " + e.getErrorCode() + ":" + e.getMessage());
            e.printStackTrace();
            response.sendRedirect("/company_management_war_exploded/HomePage.jsp");

        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
