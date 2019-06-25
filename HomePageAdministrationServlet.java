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
import java.sql.Savepoint;
import java.util.ArrayList;
import company_management.bean.userBean;
import company_management.bean.messaggio;

/**
 * servlet che gestisce la homepage dell'amministrazione. Gestisce le funzionalità che l'admin ha a disposizione.
 * in base a quale funzionalità che viene scelta, la servlet reindirizza alla giusta pagina jsp.
 * @author Antonio Pilato
 */
@WebServlet(name = "HomePageAdministration-Servlet")
public class HomePageAdministrationServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String funzione = (String)request.getParameter("funzione");
        if (funzione.equals("uno")){
            System.out.println("funzione: "+funzione);
            SavwMySQL DB = SavwMySQL.getInstance();
        ArrayList<userBean> userInDB = new ArrayList<userBean>();
        ServletContext sc = request.getSession().getServletContext();
        try {
            userInDB = DB.searchUsersByMessageSent();
            request.removeAttribute("LISTAUTENTI");
            request.setAttribute("LISTAUTENTI",userInDB);
            RequestDispatcher rd = sc.getRequestDispatcher("/funzione1.jsp");
            rd.forward(request,response);
        }catch(SQLException e){
            System.out.println("ERROR:"+e.getErrorCode()+":"+e.getMessage());
            e.printStackTrace();
            RequestDispatcher rd = sc.getRequestDispatcher("/HomePageAdministration.jsp");
            rd.forward(request,response);
        }
    } else if(funzione.equals("due")){
            System.out.println("funzione: "+funzione);
            SavwMySQL DB = SavwMySQL.getInstance();
            ArrayList<userBean> userInDB = new ArrayList<userBean>();
            ServletContext sc = request.getSession().getServletContext();
            try{
                userInDB = DB.searchUsersByMessageRecived();
                request.removeAttribute("LISTAUTENTI");
                request.setAttribute("LISTAUTENTI",userInDB);
                RequestDispatcher rd = sc.getRequestDispatcher("/funzione2.jsp");
                rd.forward(request,response);
            }catch(SQLException e){
                System.out.println("ERROR:"+e.getErrorCode()+":"+e.getMessage());
                e.printStackTrace();
                RequestDispatcher rd = sc.getRequestDispatcher("/HomePageAdministration.jsp");
                rd.forward(request,response);
            }
        }
        else if(funzione.equals("tre")){
            System.out.println("funzione: "+funzione);
            SavwMySQL DB = SavwMySQL.getInstance();
            ArrayList<messaggio> messagesInDB = new ArrayList<messaggio>();
            ServletContext sc = request.getSession().getServletContext();
            try{
                messagesInDB = DB.searchMessages();
                request.removeAttribute("LISTAMESSAGGI");
                request.setAttribute("LISTAMESSAGGI",messagesInDB);
                RequestDispatcher rd = sc.getRequestDispatcher("/funzione3.jsp");
                rd.forward(request,response);
            }catch(SQLException e){
                System.out.println("ERROR:"+e.getErrorCode()+":"+e.getMessage());
                e.printStackTrace();
                RequestDispatcher rd = sc.getRequestDispatcher("/HomePageAdministration.jsp");
                rd.forward(request,response);
            }
        }
        else if(funzione.equals("quattro")){
            System.out.println("funzione: "+funzione);
            SavwMySQL DB = SavwMySQL.getInstance();
            String word = (String)request.getParameter("valore");
            ArrayList<messaggio> messageInDB = new ArrayList<messaggio>();
            ServletContext sc = request.getSession().getServletContext();
            try{
                messageInDB = DB.searchMessageByWord(word);
                request.removeAttribute("LISTAMESSAGGI");
                request.setAttribute("LISTAMESSAGGI",messageInDB);
                RequestDispatcher rd = sc.getRequestDispatcher("/funzione4.jsp");
                rd.forward(request,response);
            }catch (SQLException e){
                System.out.println("ERROR:"+e.getErrorCode()+":"+e.getMessage());
                e.printStackTrace();
                RequestDispatcher rd = sc.getRequestDispatcher("/HomePageAdministration.jsp");
                rd.forward(request,response);
            }


        }
}

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
