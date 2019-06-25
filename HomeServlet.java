package company_management.servlet;

import company_management.bean.ChatMediator;
import company_management.bean.ChatMediatorImpl;
import company_management.bean.userBeanAbs;
import company_management.db.SavwMySQL;
import company_management.bean.userBean;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * servlet che gestisce la homepage dell'utente. Gestisce le funzionalità di aggiunta di follower e la funzionalità
 * di scrittura e visualizzazione dei messaggi.
 * @author Antonio Pilato
 */
@WebServlet(name = "Home-Servlet")
public class HomeServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nickDaRicercare = request.getParameter("barraRicerca");
        System.out.println("Devo ricercare: "+nickDaRicercare);
        SavwMySQL DB = SavwMySQL.getInstance();
        userBean user = (userBean) request.getSession().getAttribute("UTENTE");
        System.out.println("NICK UTENTE ATTUALE: "+user.getNickName());
        ServletContext sc = request.getSession().getServletContext();
        try {
            userBean userDaRicercare = DB.utente(nickDaRicercare);
            if(nickDaRicercare.equals(userDaRicercare.getNickName()))
            {
                //la persona trovata è quella che sto cercando
                System.out.println("Trovato la peronsa di nickname: "+userDaRicercare.getNickName());
                request.setAttribute("NICKDAAGGIUNGERE",nickDaRicercare);
                RequestDispatcher rd = sc.getRequestDispatcher("/HomePage.jsp");
                rd.forward(request,response);
            }else {
                //la persona trovata non è quella che sto cercando, stampo la stringa non trovata!
                System.out.println("non trovato la persona di nickname: "+nickDaRicercare);
                response.sendRedirect("/company_management_war_exploded/HomePage.jsp");
            }
        }catch (SQLException e) {
            System.out.println("ERROR: " + e.getErrorCode() + ":" + e.getMessage());
            e.printStackTrace();
            response.sendRedirect("/company_management_war_exploded/HomePage.jsp");

        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        userBean user = (userBean) request.getSession().getAttribute("UTENTE");
        String messaggio = request.getParameter("tweet-text");
        String hashtag = request.getParameter("hashtag");
        System.out.println("MESSAGGIO POSTATO: "+messaggio);
        System.out.println("HASHTAG POSTATO: "+hashtag);
        ChatMediator mediator = new ChatMediatorImpl();
        System.out.println("chatmediator creato");
        userBeanAbs utente = new userBean();
        utente.setNickName((String)request.getSession().getAttribute("NickName"));
        utente.setMediator(mediator);
        //metodo che aggiunge automaticamente tutti gli utenti della lista dei follower
        mediator.aggiungiUtenti(utente.getNickName());
        System.out.println("NICKNAME UTENTE: "+utente.getNickName());
        utente.invio(messaggio,hashtag);
        response.sendRedirect("/company_management_war_exploded/HomePage.jsp");
    }
}
