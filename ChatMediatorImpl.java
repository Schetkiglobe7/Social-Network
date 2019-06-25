package company_management.bean;

import company_management.db.SavwMySQL;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe che implementa l'interfaccia del mediator. con ChatMediatorImpl si va ad implementare l'invio e la ricezione
 * dei messaggi utilizzando il sito stesso, è possibile aggiungere altri mediator che possono gestire l'invio e la
 * ricezione dei messaggi sfruttando altre tecnologie, esempio tramite email o sms.
 * @author Antonio Pilato
 */

public class ChatMediatorImpl implements ChatMediator {
    //variabili
    private List<userBeanAbs> utenti;
    //costruttore
    public ChatMediatorImpl() {
        this.utenti = new ArrayList<>();
    }
    //metodi

    /**
     * Il metodo aggiungiUtenti prende un nickname dato come paramentro di ingresso del metodo stesso e lo inserisce nel
     * database.
     * @param nickname
     */
    @Override
    public void aggiungiUtenti(String nickname) {
        SavwMySQL DB = SavwMySQL.getInstance();
        try {
            utenti = DB.returnFollower(nickname);
        } catch (SQLException e) {
            System.out.println("ERROR: " + e.getErrorCode() + ":" + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * il metodo InvioMessaggio prende come parametri di ingresso il messaggio, l'hashtag associato e il creatore
     * del messaggio, e, per ogni utente trovato nella lista degli utenti "collegati" all'utente associato al mediator,
     * e "invia" il messaggio ai follower.
     * @param messaggio
     * @param utente
     * @param hashtag
     */
    @Override
    public void InvioMessaggio(String messaggio, userBeanAbs utente, String hashtag) {
        for (userBeanAbs u : this.utenti) {
            if (u != utente) {
                u.ricevi(messaggio, hashtag,utente.getNickName());
            }
        }
    }
}
