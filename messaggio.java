package company_management.bean;

/**
 * classe per la definizione di "messaggio"
 * @author Antonio Pilato
 */

public class messaggio {
    //variabili
    private String nickSorgente="";
    private String nickDestinatario="";
    private String corpo="";
    private String hashtag="";

    //metodi setter

    /**
     * metodo "setter" per il settaggio del nickname del mittente del messaggio
     * @param nickSorgente
     */
    public void setNickSorgente(String nickSorgente){ this.nickSorgente = nickSorgente; }

    /**
     * metodo "setter" per il settaggio del nickname del destinatario del messaggio
     * @param nickDestinatario
     */
    public void setNickDestinatario(String nickDestinatario){ this.nickDestinatario=nickDestinatario; }

    /**
     * metodo "setter" per il settaggio del corpo del messaggio
     * @param corpo
     */
    public void setCorpo(String corpo){ this.corpo=corpo; }

    /**
     * metodo "setter" per il settaggio dell'hashtag associato al messaggio
     * @param hashtag
     */
    public void setHashtag(String hashtag){ this.hashtag = hashtag; }

    //metodi getter

    /**
     * metodo "getter" che ritorna il nickname del mittente del messaggio
     * @return nickname mittente
     */
    public String getNickSorgente(){ return this.nickSorgente; }

    /**
     * metodo "getter" che ritorna il nickname del destinatario del messaggio
     * @return nickname destinatario
     */
    public String getNickDestinatario(){return this.nickDestinatario;}

    /**
     * metodo "getter" che ritorna il corpo del messaggio
     * @return corpo
     */
    public String getCorpo(){return this.corpo;}

    /**
     * metodo "getter" che ritorna l'hashtag associato al messaggio
     * @return hashtag
     */
    public String getHashtag(){return this.hashtag;}

}
