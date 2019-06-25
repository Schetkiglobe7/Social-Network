package company_management.bean;

import company_management.db.SavwMySQL;
import java.sql.SQLException;

/** classe per la definizione dell' utente
 * @author Antonio Pilato
 */
public class userBean extends userBeanAbs {
    //costruttore con parametri

    /**
     * questo costruttore ha due parametri ed è usato principalmente per la creazione di un'istanza della classe
     * che definisce anche un mediator. L'utente ha associato un mediator che inserisce in una lista tutti gli utenti
     * che segue. In questo modo ogni utente ha un'istanza della classe mediator che contiene una lista di utenti.
     * Ogni volta che l'utente invia il messaggio, lo invia a tutti gli utenti che segue.
     * @param mediator
     * @param nickName
     */
    public userBean(ChatMediator mediator, String nickName){
        super(mediator,nickName);
    }
    //costruttore senza parametri

    /**
     * costruttore standard. Dato che si è definito un costruttore con parametri, per utilizzare il costruttore
     * standard è necessario ridefinirlo.
     */
    public userBean(){};
    //metodi setter

    /**
     * metodo "setter" per settare il nickname dell'utente.E' usato come chiave primaria per la distinzione delle varie
     * istanze di utente all'interno dell'applicativo.
     * @param nickName
     */
    @Override
    public void setNickName(String nickName) { this.NickName = nickName; }

    /**
     * metodo "setter" per settare la password dell'utente. E' usata per l'accesso alle funzionalità dell'applicativo
     * da parte dell'utente che si è iscritto al servizio.
     * @param passoword
     */
    @Override
    public void setPassoword(String passoword) { this.Passoword = passoword; }

    /**
     * metodo "setter" per settare il nome dell'utente. E' il nome proprio della persona che si registra come utente.
     * @param nome
     */
    @Override
    public void setNome(String nome){ this.nome=nome; }

    /**
     * metodo "setter" per settare il cognome dell'utente. E' il cognome proprio della persona che si registra come
     * utente.
     * @param cognome
     */
    @Override
    public void setCognome(String cognome){ this.cognome=cognome;}

    /**
     * metodo "setter" per settare l'email dell'utente. E' l'email propria della persona che si registra come utente.
     * E' memorizzato come dato descrittivo dell'utente ma può essere utilizzato per sviluppi futuri di altre
     * funzionalità.
     * @param email
     */
    @Override
    public void setEmail(String email){ this.email = email; }

    /**
     * metodo "setter" per settare il numero di telefono dell'utente. E' il numero di telefono proprio della persona
     * che si registra come utente. E' memorizzato come dato descrittivo dell'utente ma può essere utilizzato per
     * sviluppi futuri di altre funzionalità.
     * @param telefono
     */
    @Override
    public void setTelefono(String telefono){ this.telefono=telefono;}

    //metodi getter

    /**
     * metodo "getter" che ritorna il nickname dell'utente. E' utilizzato principalmente nei metodi della classe del
     * database per ritornare, all'occorrenza, il nickname dell'utente.
     * @return nickname dell'utente
     */
    @Override
    public String getNickName() { return this.NickName; }

    /**
     * metodo "getter" che ritorna la password dell'utente. E' utilizzato principalmente nei metodi della classe del
     * database per ritornare, all'occorenza, la password dell'utente (la password dell'utente dovrebbe essere
     * usata solamente per la verifica del login)
     * @return password dell'utente
     */
    @Override
    public String getPassoword() { return this.Passoword; }

    /**
     *  metodo "getter" che ritorna il nome proprio dell'utente. E' utilizzato principalmente nei metodi della classe
     *  del database per ritornare, all'occorrenza, il nome dell'utente.E' un dato informativo dell'utente che si
     *  registra al servizio e la sua memorizzazione non ha nessuno scopo preciso.
     * @return nome dell'utente
     */
    @Override
    public String getNome(){ return this.nome; }

    /**
     * metodo "getter" che ritorna il cognome proprio dell'utente. E' utilizzato principalmente nei metodi della
     * classe del database per ritornare, all'occorenza, il cognome dell'utente. E' un dato informativo dell'utente che
     * si registra al servizio e la sua memorizzazione non ha nessuno scopo preciso.
     * @return cognome dell'utente
     */
    @Override
    public String getCognome(){ return this.cognome; }

    /**
     * metodo "getter" che ritorna l'email dell'utente. E' utilizzato principalmente nei metodi della classe database
     * per ritornare, all'occorrenza, l'email dell'utente. E' un dato informativo dell'utente che si registra al
     * servizio. Può essere usato per eventuali sviluppi futuri di funzionalità aggiuntive.
     * @return email dell'utente
     */
    @Override
    public String getEmail(){ return this.email; }

    /**
     * metodo "getter" che ritorna il numero di telefono dell'utente. E' utilizzato principalmente nei metodi della classe
     * database per ritornare, all'occorenza, il numero di telefono dell'utente. E' un dato informativo dell'utente che
     * si registra al servizio. Può essere usato per eventuali sviluppi futuri di funzionalità aggiuntive.
     * @return telefono dell'utente
     */
    @Override
    public String getTelefono(){ return this.telefono; }

    //altri metodi

    /**
     * metodo per l'invio dei messaggi. L'utente invia il messaggio al mediator che invia il messaggio a tutti gli utenti
     * che si trovano nell'ArrayList degli utenti seguiti.
     * @param messaggio
     * @param hashtag
     */
    @Override
    public void invio(String messaggio, String hashtag){
        System.out.println(this.getNickName()+": Sending message = "+messaggio+" #"+hashtag);
        this.mediator.InvioMessaggio(messaggio,this,hashtag);
    }

    /**
     * metodo per la ricezione dei messaggi. Il mediator "invia" all'utente destinatario il messaggio. L'utente
     * destinatario provvede a memorizzare il messaggio sul database tramite la chiamata al metodo "insertMessage".
     * @param messaggio
     * @param hashtag
     * @param nickNameUtente
     */
    @Override
    public void ricevi(String messaggio, String hashtag,String nickNameUtente) {
        System.out.println(this.NickName+": Recived message= "+messaggio+" #"+hashtag+" from: "+nickNameUtente);
        SavwMySQL DB = SavwMySQL.getInstance();
        try {
            DB.insertMessage(nickNameUtente, this.getNickName(), messaggio, hashtag);
        }catch (SQLException e){
            System.out.println("ERROR: " + e.getErrorCode() + ":" + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * metodo "setter" per settare il mediator dell'utente. Ogni utente "ha" un'istanza di mediator che provvede a
     * mantenere un'ArrayList di utenti seguiti.
     * @param mediator
     */
    @Override
    public void setMediator(ChatMediator mediator){
        this.mediator = mediator;
    }
}
