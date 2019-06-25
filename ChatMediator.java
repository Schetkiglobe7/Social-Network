package company_management.bean;

/**
 * Interfaccia del mediator.
 * @author Antonio Pilato
 */
public interface ChatMediator {
    /**
     * Il metodo InvioMessaggio è usato per l'invio del messaggio. Oltre al messaggio, il mediator invia,
     * a tutti i partecipanti alla comunicazione, l'hashtag associato al messaggio nonchè il suo autore.
     * @param messaggio
     * @param utente
     * @param hashtag
     */
    public void InvioMessaggio(String messaggio, userBeanAbs utente, String hashtag);

    /**
     * Il metodo aggiungiUtenti è usato per aggiungere all'ArrayList tutti gli utenti collegati ad un utente dato.
     * @param nickname
     */
    public void aggiungiUtenti(String nickname);
}
