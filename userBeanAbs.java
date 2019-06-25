package company_management.bean;

/**
 * classe che astrae il concetto di utente
 * @author Antonio Pilato
 */
public abstract class userBeanAbs {
    //variabili
    protected ChatMediator mediator;
    protected String NickName;
    protected String Passoword;
    protected String nome;
    protected String cognome;
    protected String email;
    protected String telefono;
    //costruttore con parametri

    /**
     * Costruttore con parametri che setta solo il nickname dell'utente e il mediator.
     * Le altre variabili di classe devono essere settate con i metodi "setter".
     * @param mediator
     * @param NickName
     */
    public userBeanAbs(ChatMediator mediator, String NickName){
        this.mediator = mediator;
       this.NickName = NickName;
    }
    //costruttore senza parametri

    /**
     * costruttore standard definito implicitamente per l'utilizzo(data l'esistenza di un ulteriore costruttore)
     */
    public  userBeanAbs(){};

    //metodi setter

    /**
     * definizione del metodo setNickName che è un metodo "setter" ed è usato per settare il nickname dell'utente.
     * Il nickname dell'utente è usato come identificazione tra le varie istanze di utente.
     * @param nickName
     */
    public abstract void setNickName(String nickName);

    /**
     * definizione del metodo setPassword che è un metodo "setter" ed è usato per settare la password dell'utente.
     * La password dell'utente è usata per l'accesso alle funzionalità del servizio.
     * @param passoword
     */
    public abstract void setPassoword(String passoword);

    /**
     * definizione del metodo setNome che è un metodo "setter" ed è usato per settare il nome dell'utente.
     * il nome è un dato informativo dell'utente.
     * @param nome
     */
    public abstract void setNome(String nome);

    /**
     * definizione del metodo setCognome che è un metodo "setter" ed è usato per settare il cognome dell'utente
     * il cognome è un dato informativo dell'utente.
     * @param cognome
     */
    public abstract void setCognome(String cognome);

    /**
     * definizione del metodo setEmail che è un metodo "setter" ed è usato per settare l'email dell'utente.
     * l'email è un dato informativo dell'utente ma può essere usato per sviluppi futuri di altre funzionalità
     * @param email
     */
    public abstract void setEmail(String email);

    /**
     * definizione del metodo setTelefono che è un metodo "setter" ed è usato per settare il numero di telefono
     * dell'utente. Il numero di telefono è un dato informativo dell'utente ma può essere usato per sviluppi futuri di
     * altre funzionalità
     * @param telefono
     */
    public abstract void setTelefono(String telefono);

    // metodi getter

    /**
     * definizione del metodo getNickName che è un metodo "getter" ed è usato per ritornare il nickname dell'utente.
     * @return nickname utente
     */
    public abstract String getNickName();

    /**
     * definizione del metodo getPassword che è un metodo "getter" ed è usato per ritornare la password dell'utente.
     * @return password utente
     */
    public abstract String getPassoword();

    /**
     * definizione del metodo getNome che è un metodo "getter" ed è usato per ritornare il nome dell'utente.
     * @return nome utente
     */
    public abstract String getNome();

    /**
     * definizione del metodo getCognome che è un metodo "getter" ed è usato per ritornare il cognome dell'utente.
     * @return cognome utente
     */
    public abstract String getCognome();

    /**
     * definizione del metodo getEmail che è un metodo "getter" ed è usato per ritornare l'email dell'utente.
     * @return email utente
     */
    public abstract String getEmail();

    /**
     * definizione del metodo getTelefono che è un metodo "getter" ed è usato per ritornare il numero di telefono
     * dell'utente.
     * @return numero di telefono dell'utente
     */
    public abstract String getTelefono();

    // altri metodi

    /**
     * definizione del metodo invio che è un metodo  usato per l'invio dei messaggi al mediator, che provvederà a
     * inviare i messaggi ai vari destinatari.
     * @param messaggio
     * @param hashtag
     */
    public abstract void invio(String messaggio, String hashtag);

    /**
     * definizione del metodo ricevi che è un metodo usato per la ricezione dei messaggi.
     * @param messaggio
     * @param hashtag
     * @param nickNameutente
     */
    public abstract void ricevi(String messaggio, String hashtag,String nickNameutente);

    /**
     * definizione del metodo setMediator che è un metodo "setter" ed è usato per settare il mediator dell'utente.
     * @param mediator
     */
    public abstract void setMediator(ChatMediator mediator);
}
