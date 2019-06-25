package company_management.servlet;

import company_management.db.SavwMySQL;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import company_management.bean.messaggio;

/**
 * servlet che gestisce il reperimento dei messaggi dal database e li invia alla pagina home dell'utente in modo che
 * possa visualizzarli.
 * @author Antonio Pilato
 */
@WebServlet(name = "message-Servlet")
public class messageServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SavwMySQL DB = SavwMySQL.getInstance();
        ArrayList<messaggio> messaggi = new ArrayList<messaggio>();
        ServletContext sc = request.getSession().getServletContext();
        try{
            messaggi = DB.showTweet((String) request.getSession().getAttribute("NickName"));
            request.setAttribute("MESSAGGI",messaggi);
            RequestDispatcher rd = sc.getRequestDispatcher("/HomePage.jsp");
            rd.forward(request,response);
        }catch(SQLException e){
            System.out.println("ERROR: " + e.getErrorCode() + ":" + e.getMessage());
            e.printStackTrace();
            RequestDispatcher rd = sc.getRequestDispatcher("/error.jsp");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
