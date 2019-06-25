package company_management.db;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import com.mysql.cj.jdbc.SuspendableXAConnection;
import com.mysql.cj.protocol.Resultset;
import company_management.bean.userBean;
import company_management.bean.userBeanAbs;
import company_management.bean.messaggio;
import javax.jms.ConnectionConsumer;
import javax.naming.directory.SearchResult;
import javax.xml.transform.Result;


/**
 * classe di persistenza DAO. E' usata per l'interazione con il database.
 * @author Antonio Pilato
 */
public class SavwMySQL {
    private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_CONNECTION = "jdbc:mysql://localhost:3306/twitter";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD="";
    private static SavwMySQL istanza;

    //costruttore

    /**
     * il costruttore della classe è privato proprio come previsto dal pattern singleton
     */
    private SavwMySQL(){}

    //metodi

    /**
     * metodo che ritorna l'istanza della classe. Se la classe non è stata mai istanziata, allora si effettua
     * l'istanziazione e la si ritorna. Se la classe è stata già istanziata una volta, allora si ritorna l'istanza già
     * esistente
     * @return istanza della classe
     */
    public static SavwMySQL getInstance(){
        if(istanza == null){
            istanza = new SavwMySQL();
        }
        return istanza;
    }

    /**
     * metodo che effettua la connessione al database.
     * @return connessione al database
     * @throws Exception se la connessione al database falllisce o se non è stato trovato il connettore al database
     */
    private static Connection getDBConnection() throws Exception {
        System.out.println("--------MySQL JDBC Connection------------");
        System.out.println(DB_PASSWORD);
        Connection dbConnection = null;
        try{
            Class.forName(DB_DRIVER);
        }catch (ClassNotFoundException e){
            System.out.println("ERROR: MySQL JDBC Driver not found!!");
            throw  new Exception(e.getMessage());
        }
        try{
            dbConnection = DriverManager.getConnection(DB_CONNECTION,DB_USER,DB_PASSWORD);
            System.out.println("SQL Connection to Company_Management database stablished!");
        }catch (SQLException e){
            System.out.println("Connection to Company_Management database failed!");
            throw new Exception(e.getErrorCode()+":"+e.getMessage());
        }
        return dbConnection;
    }

    /**
     * metodo usato per ritornare l'intera istanza di un utente.
     * @param NickName
     * @return istanza di utente
     * @throws SQLException se la query sql fallisce
     */
    public userBean utente(String NickName) throws  SQLException{
        Statement stmt = null;
        Connection conn = null;
        userBean utenteRit = new userBean();
        try{
            conn = getDBConnection();
            stmt = conn.createStatement();
            String searchUser = "SELECT * FROM user WHERE Nickname ='"+NickName+"'";
            System.out.println("QUERY USER: "+searchUser);
            ResultSet resultset = stmt.executeQuery(searchUser);
            while (resultset.next()){
                utenteRit.setNome(resultset.getString("Nome"));
                utenteRit.setCognome(resultset.getString("Cognome"));
                utenteRit.setEmail(resultset.getString("Email"));
                utenteRit.setTelefono(resultset.getString("Telefono"));
                utenteRit.setNickName(resultset.getString("NickName"));
                utenteRit.setPassoword(resultset.getString("Password"));
            }
        }catch (SQLException sqle) {
            throw new SQLException(sqle.getErrorCode()+":"+sqle.getMessage());
        } catch (Exception e) {
            throw new SQLException(e.getMessage());
        }finally {
            if(stmt != null) stmt.close();
            if(conn != null) conn.close();
        }
        return utenteRit;
    }

    /**
     * metodo usato per verificare se esiste o meno un nickname, inserito come parametro di ingresso al metodo, nel
     * database.Se esiste lo ritorna altrimenti ritorna una stringa vuota.
     * @param NickName
     * @return nickname utente
     * @throws SQLException se la query sql fallisce
     */
    public String ifExistNick(String NickName) throws SQLException {
        Statement stmt=null;
        Connection conn=null;
        String nick="";
        try{
            conn = getDBConnection();
            stmt = conn.createStatement();
            String searchUserPass = "SELECT NickName FROM user WHERE NickName='"+NickName+"'";
            System.out.println("QUERY USER: "+searchUserPass);
            ResultSet resultset = stmt.executeQuery(searchUserPass);
            while(resultset.next())
                nick= resultset.getString("NickName");
        } catch (SQLException sqle) {
            throw new SQLException(sqle.getErrorCode()+":"+sqle.getMessage());
        } catch (Exception e) {
            throw new SQLException(e.getMessage());
        }finally {
            if(stmt != null) stmt.close();
            if(conn != null) conn.close();
        }
        return nick;
    }

    /**
     * metodo usato per verificare se esiste o meno una password, inserita come parametro di ingresso al metodo, nel
     * database. La query viene effettuata utilizzando il nickname dell'utente, preso come parametro di ingresso del
     * metodo. Se esiste il nickname e la password, presi come parametri di ingresso, viene ritornata la password
     * altrimenti viene ritornata una stringa vuota.
     * @param Pw
     * @param NickName
     * @return password utente
     * @throws SQLException se la query sql fallisce
     */
    public String ifExistPass(String Pw,String NickName) throws SQLException {
        Statement stmt=null;
        Connection conn=null;
        String Password="";
        try{
            conn = getDBConnection();
            stmt = conn.createStatement();
            String searchUserPass = "SELECT password FROM user WHERE nickname='"+NickName+"'";
            System.out.println("QUERY USER: "+searchUserPass);
            ResultSet resultset = stmt.executeQuery(searchUserPass);
            while(resultset.next())
                Password= resultset.getString("Password");
        } catch (SQLException sqle) {
            throw new SQLException(sqle.getErrorCode()+":"+sqle.getMessage());
        } catch (Exception e) {
            throw new SQLException(e.getMessage());
        }finally {
            if(stmt != null) stmt.close();
            if(conn != null) conn.close();
        }
        return Password;
    }

    /**
     * metodo usato per l'inserimento di un utente, preso come parametro di ingresso, nel database.
     * @param user
     * @throws SQLException se la query di inserimento fallisce
     */
    public void InsertUser(userBean user) throws SQLException{
        Statement stmt = null;
        Connection conn = null;
        try {
            conn = getDBConnection();
            conn.setAutoCommit(false);
            stmt = conn.createStatement();
            String insertUser = "INSERT INTO user(NickName,Password,nome,cognome,email,telefono) VALUES ('" + user.getNickName() + "'"+","+"'"
                    + user.getPassoword() + "'"+"," +"'" + user.getNome() + "'"+"," + "'" + user.getCognome() + "'"+","+"'" + user.getEmail() + "'"+","+"'" + user.getTelefono() + "'"+ ");";
            System.out.println("INSERT QUERY: " + insertUser);
            stmt.executeUpdate(insertUser);
            conn.commit();
        }catch (SQLException sqle){
            if(conn != null) conn.rollback();
            System.out.println("INSERT ERROR: Transaction is being rolled back");
            throw new SQLException(sqle.getErrorCode()+":"+sqle.getMessage());
        }catch (Exception err){
          if(conn != null) conn.rollback();
          System.out.println("GENERIC ERROR: Transaction is being rolled back");
          throw  new SQLException(err.getMessage());
        } finally {
            if(stmt != null) stmt.close();
            if(conn != null) conn.close();
        }
    }

    /**
     * metodo usato per l'inserimento del nickname del follow e il nickname del follower nel database.
     * Il follow è la persona che aggiunge mentre il follower è la persona aggiunta. Entrambi i nickname
     * sono presi come parametro di ingresso.
     * @param mioNick
     * @param nickDaInserire
     * @throws SQLException se la query di inserimento fallisce
     */
    public void insertFollower(String mioNick, String nickDaInserire) throws SQLException {
        Statement stmt = null;
        Connection conn = null;
        try{
            conn = getDBConnection();
            conn.setAutoCommit(false);
            stmt = conn.createStatement();
            String insertFollow = "INSERT INTO segue(nickname_follow, nickname_follower) VALUES ('" + mioNick + "'"+","+"'"
                    + nickDaInserire + "'"+ ");";
            System.out.println("INSERT QUERY: " + insertFollow);
            stmt.executeUpdate(insertFollow);
            conn.commit();
        }catch (SQLException sqle){
            if(conn != null) conn.rollback();
            System.out.println("INSERT FOLLOW ERROR: Transaction is being rolled back");
            throw new SQLException(sqle.getErrorCode()+":"+sqle.getMessage());
        }catch (Exception err){
            if(conn != null) conn.rollback();
            System.out.println("GENERIC ERROR: Transaction is being rolled back");
            throw  new SQLException(err.getMessage());
        } finally {
            if(stmt != null) stmt.close();
            if(conn != null) conn.close();
        }
    }

    /**
     * metodo usato per l'inserimento nel database dei messaggi che vengono scritti. Tutti i dati direttamente
     * collegati al messaggio sono presi come parametri di ingresso.
     * @param nickSorgente
     * @param nickDestinatario
     * @param messaggio
     * @param hashtag
     * @throws SQLException se la query di inserimento fallisce
     */
    public void insertMessage(String nickSorgente, String nickDestinatario,String messaggio,String hashtag)throws  SQLException{
        Statement stmt = null;
        Connection conn= null;
        try{
            conn = getDBConnection();
            conn.setAutoCommit(false);
            stmt = conn.createStatement();
            String insertMessage = "INSERT INTO invio_messaggio(nicknameMittente, nicknameDestinatario, corpo, hashtag) VALUES ('" + nickSorgente + "'"+","+"'"
                    + nickDestinatario + "'"+","+"'"+messaggio+"'"+","+"'"+hashtag+"'"+ ");";
            System.out.println("INSERT QUERY: " + insertMessage);
            stmt.executeUpdate(insertMessage);
            conn.commit();
        }catch (SQLException sqle){
            if(conn != null) conn.rollback();
            System.out.println("INSERT MESSAGE ERROR: Transaction is being rolled back");
            throw new SQLException(sqle.getErrorCode()+":"+sqle.getMessage());
        }catch (Exception err){
            if(conn != null) conn.rollback();
            System.out.println("GENERIC ERROR: Transaction is being rolled back");
            throw  new SQLException(err.getMessage());
        } finally {
            if(stmt != null) stmt.close();
            if(conn != null) conn.close();
        }
    }

    /**
     * metodo usato per reperire la lista, ArrayList, di utenti. Questa lista rappresenta la lista degli utenti Follower
     * di un follow. Questo metodo viene usato per caricare dal database la lista degli utenti dal mediator.
     * @param nickname
     * @return ArrayList di utenti
     * @throws SQLException se la query fallisce
     */
    public ArrayList<userBeanAbs> returnFollower(String nickname) throws  SQLException{
        Statement stmt = null;
        Connection conn = null;
        ArrayList<userBeanAbs> users = new ArrayList<userBeanAbs>();
        try{
            conn = getDBConnection();
            stmt = conn.createStatement();
            String searchUser = "SELECT nickname_follower FROM segue WHERE nickname_follow ='"+nickname+"'";
            System.out.println("QUERY USER: "+searchUser);
            ResultSet resultset = stmt.executeQuery(searchUser);
            while(resultset.next()){
                userBean userTemp = new userBean();
                userTemp.setNickName(resultset.getString("nickname_follower"));
                users.add(userTemp);
            }
        } catch (SQLException sqle) {
            throw new SQLException(sqle.getErrorCode()+":"+sqle.getMessage());
        } catch (Exception e) {
            throw new SQLException(e.getMessage());
        }finally {
            if(stmt != null) stmt.close();
            if(conn != null) conn.close();
        }
        return users;
    }

    /**
     * metodo usato per ritornare la lista, ArrayList, di messaggi memorizzati nel database in cui il nickname, passato
     * come parametro di ingresso, compare come destinatario.
     * @param nickname
     * @return ArrayList di messaggi
     * @throws SQLException se la query fallisce
     * @throws IOException
     */
    public ArrayList<messaggio> showTweet(String nickname) throws SQLException,IOException {
        Statement stmt = null;
        Connection conn = null;
        try {
            conn = getDBConnection();
            stmt = conn.createStatement();
            String searchTweet="SELECT distinct * FROM invio_messaggio WHERE nicknameDestinatario='"+nickname+"'";
            System.out.println("QUERY TWEET: "+searchTweet);
            ResultSet resultset = stmt.executeQuery(searchTweet);
            ArrayList<messaggio>  messaggi = new ArrayList<messaggio>();

            while(resultset.next()){
                messaggio tweet = new messaggio();
                String nickSorgente = resultset.getString("nicknameMittente");
                String nickDestinatario = resultset.getString("nicknameDestinatario");
                String corpo = resultset.getString("corpo");
                String hashtag = resultset.getString("hashtag");
                tweet.setNickSorgente(nickSorgente);
                tweet.setNickDestinatario(nickDestinatario);
                tweet.setCorpo(corpo);
                tweet.setHashtag(hashtag);
                messaggi.add(tweet);
            }

            return messaggi;
        } catch (SQLException sqle) {
            throw new SQLException(sqle.getErrorCode() + ":" + sqle.getMessage());
        } catch (Exception e) {
            throw new SQLException(e.getMessage());
        } finally {
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        }
    }
    //metodi tabella amministratore

    /**
     *  metodo che ritorna il nickname dell'utente admin dal database.
     * @param NickName
     * @return nickname dell'utente admin
     * @throws SQLException se la query fallisce
     */
    public String ifExistNickAdmin(String NickName) throws SQLException {
        Statement stmt=null;
        Connection conn=null;
        String nick="";
        try{
            conn = getDBConnection();
            stmt = conn.createStatement();
            String searchUserPassAdmin = "SELECT nickname FROM AdminLogin WHERE nickname='"+NickName+"'";
            System.out.println("QUERY USER: "+searchUserPassAdmin);
            ResultSet resultset = stmt.executeQuery(searchUserPassAdmin);
            while(resultset.next())
                nick= resultset.getString("NickName");
        } catch (SQLException sqle) {
            throw new SQLException(sqle.getErrorCode()+":"+sqle.getMessage());
        } catch (Exception e) {
            throw new SQLException(e.getMessage());
        }finally {
            if(stmt != null) stmt.close();
            if(conn != null) conn.close();
        }
        return nick;
    }

    /**
     * metodo usato per ritornare dal database la password dell'utente admin.
     * @param Pw
     * @param NickName
     * @return password utente admin
     * @throws SQLException se la query fallisce
     */
    public String ifExistPassAdmin(String Pw,String NickName) throws SQLException {
        Statement stmt=null;
        Connection conn=null;
        String Password="";
        try{
            conn = getDBConnection();
            stmt = conn.createStatement();
            String searchUserPassAdmin = "SELECT password FROM AdminLogin WHERE nickname='"+NickName+"'";
            System.out.println("QUERY USER: "+searchUserPassAdmin);
            ResultSet resultset = stmt.executeQuery(searchUserPassAdmin);
            while(resultset.next())
                Password= resultset.getString("Password");
        } catch (SQLException sqle) {
            throw new SQLException(sqle.getErrorCode()+":"+sqle.getMessage());
        } catch (Exception e) {
            throw new SQLException(e.getMessage());
        }finally {
            if(stmt != null) stmt.close();
            if(conn != null) conn.close();
        }
        return Password;
    }

    /**
     * metodo usato per ritornare la lista, ArrayList, di utenti in base al numero di messaggi inviati. Gli utenti che
     * hanno inviato più messaggi saranno visualizzati per prima seguiti, mano a mano, dagli utenti che hanno inviato
     * meno messaggi. Gli utenti che non hanno inviato messaggi non sono visualizzati
     * @return ArrayList utenti
     * @throws SQLException se la query fallisce
     * @throws IOException
     */
    public ArrayList<userBean> searchUsersByMessageSent() throws SQLException,IOException{
        Statement stmt = null;
        Connection conn = null;
        try{
            conn = getDBConnection();
            stmt = conn.createStatement();
            String searchUsersByMessageSent ="select count(*),max(nickname) AS nickname,max(password) AS password,max(nome) AS nome " +
                    ",max(cognome) AS cognome,max(email) AS email,max(telefono) AS telefono " +
                    "from user join invio_messaggio  on user.nickname = invio_messaggio.nicknameMittente " +
                    "group by nickname "+
                    "order by count(*) desc;";
            System.out.println("QUERY ADMIN 1): "+searchUsersByMessageSent);
            ResultSet resultset = stmt.executeQuery(searchUsersByMessageSent);
            ArrayList<userBean> userListInDB = new ArrayList<userBean>();
            while(resultset.next()){
                userBean userTemp = new userBean();
                String nickname = resultset.getString("nickname");
                String password = resultset.getString("password");
                String nome = resultset.getString("nome");
                String cognome = resultset.getString("cognome");
                String email = resultset.getString("email");
                String telefono = resultset.getString("telefono");
                userTemp.setNickName(nickname);
                userTemp.setPassoword(password);
                userTemp.setNome(nome);
                userTemp.setCognome(cognome);
                userTemp.setEmail(email);
                userTemp.setTelefono(telefono);
                userListInDB.add(userTemp);
            }
            return userListInDB;
        }catch (SQLException sqle){
            throw  new SQLException(sqle.getErrorCode()+":"+sqle.getMessage());
        }catch(Exception err){
            throw  new SQLException(err.getMessage());
        }finally {
            if(stmt != null) stmt.close();
            if(conn != null) conn.close();
        }
    }
    /**
     * metodo usato per ritornare la lista, ArrayList, di utenti in base al numero di messaggi ricevuti. Gli utenti che
     * hanno ricevuto più messaggi saranno visualizzati per prima seguiti, mano a mano, dagli utenti che hanno ricevuto
     * meno messaggi. Gli utenti che non hanno ricevuto messaggi non sono visualizzati
     * @return ArrayList utenti
     * @throws SQLException se la query fallisce
     * @throws IOException
     */
    public ArrayList<userBean> searchUsersByMessageRecived() throws SQLException,IOException{
        Statement stmt = null;
        Connection conn = null;
        try{
            conn = getDBConnection();
            stmt = conn.createStatement();
            String searchUsersByMessageRecived ="select count(*),max(nickname) AS nickname,max(password) AS password,max(nome) AS nome " +
                    ",max(cognome) AS cognome,max(email) AS email,max(telefono) AS telefono " +
                    "from user join invio_messaggio  on user.nickname = invio_messaggio.nicknameDestinatario " +
                    "group by nickname "+
                    "order by count(*) desc;";
            System.out.println("QUERY ADMIN 2): "+searchUsersByMessageRecived);
            ResultSet resultset = stmt.executeQuery(searchUsersByMessageRecived);
            ArrayList<userBean> userListInDB = new ArrayList<userBean>();
            while(resultset.next()){
                userBean userTemp = new userBean();
                String nickname = resultset.getString("nickname");
                String password = resultset.getString("password");
                String nome = resultset.getString("nome");
                String cognome = resultset.getString("cognome");
                String email = resultset.getString("email");
                String telefono = resultset.getString("telefono");
                userTemp.setNickName(nickname);
                userTemp.setPassoword(password);
                userTemp.setNome(nome);
                userTemp.setCognome(cognome);
                userTemp.setEmail(email);
                userTemp.setTelefono(telefono);
                userListInDB.add(userTemp);
            }
            return userListInDB;
        }catch (SQLException sqle){
            throw  new SQLException(sqle.getErrorCode()+":"+sqle.getMessage());
        }catch(Exception err){
            throw  new SQLException(err.getMessage());
        }finally {
            if(stmt != null) stmt.close();
            if(conn != null) conn.close();
        }
    }

    /**
     * metodo usato per ritornare la lista, ArrayList, di messaggi in base agli hashtag. I messaggi che contengono lo
     * stesso hashtag sono categorizzati insieme.
     * @return ArrayList messaggi
     * @throws SQLException se la query fallisce
     * @throws IOException
     */
    public ArrayList<messaggio> searchMessages() throws SQLException,IOException{
        Statement stmt=null;
        Connection conn=null;
        try{
            conn= getDBConnection();
            stmt = conn.createStatement();
            String searchMessages = "select hashtag,max(nicknameMittente) AS nicknameMittente,max(nicknameDestinatario)AS nicknameDestinatario " +
                    ",max(corpo) AS corpo " +
                    "from invio_messaggio " +
                    "group by hashtag,corpo;";
            System.out.println("QUERY ADMIN 3): "+searchMessages);
            ResultSet resultset = stmt.executeQuery(searchMessages);
            ArrayList<messaggio> messagesListInDB =new ArrayList<messaggio>();
            while(resultset.next()){
                messaggio msg = new messaggio();
                String hashtag = resultset.getString("hashtag");
                String corpo = resultset.getString("corpo");
                String nicknameMittente = resultset.getString("nicknameMittente");
                String nicknameDestinatario = resultset.getString("nicknameDestinatario");
                msg.setHashtag(hashtag);
                msg.setCorpo(corpo);
                msg.setNickDestinatario(nicknameDestinatario);
                msg.setNickSorgente(nicknameMittente);
                messagesListInDB.add(msg);
            }
            return messagesListInDB;
        }catch(SQLException sqle){
            throw new SQLException(sqle.getErrorCode()+":"+sqle.getMessage());
        }catch (Exception err){
            throw new SQLException(err.getMessage());
        }finally {
            if(stmt != null) stmt.close();
            if(conn != null) conn.close();
        }
    }

    /**
     * metodo usato per ritornare tutti i messaggi che contengono una parola data come parametro di ingresso del metodo.
     * @param word
     * @return ArrayList messaggi
     * @throws SQLException se la query fallisce
     * @throws IOException
     */
    public ArrayList<messaggio> searchMessageByWord(String word) throws SQLException,IOException{
        Statement stmt=null;
        Connection conn=null;
        try{
            conn = getDBConnection();
            stmt = conn.createStatement();
            String searchMessageByWord = "select * from invio_messaggio " +
                    "where corpo like" +"'"+ "%"+word+"%"+"'"+";";
            System.out.println("QUERY ADMIN 4): "+searchMessageByWord);
            ResultSet resultset = stmt.executeQuery(searchMessageByWord);
            ArrayList<messaggio> messageListInDB = new ArrayList<messaggio>();
            while(resultset.next()) {
                messaggio msg = new messaggio();
                String hashtag = resultset.getString("hashtag");
                String corpo = resultset.getString("corpo");
                String nicknameMittente = resultset.getString("nicknameMittente");
                String nicknameDestinatario = resultset.getString("nicknameDestinatario");
                msg.setHashtag(hashtag);
                msg.setCorpo(corpo);
                msg.setNickDestinatario(nicknameDestinatario);
                msg.setNickSorgente(nicknameMittente);
                messageListInDB.add(msg);
            }
            return  messageListInDB;
        }catch(SQLException sqle){
            throw new SQLException(sqle.getErrorCode()+":"+sqle.getMessage());
        }catch (Exception err){
        throw new SQLException(err.getMessage());
    }finally {
        if(stmt != null) stmt.close();
        if(conn != null) conn.close();
        }
    }
}
