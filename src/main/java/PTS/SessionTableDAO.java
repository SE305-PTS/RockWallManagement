package PTS;

/**
 * Created by Mitchell Petit on 3/26/2017.
 */
public class SessionTableDAO {
    public static void init() {
        DBInterface.executeUpdate("INSERT INTO Patron (id,firstname,lastname,gender,email,subscriber,belaycert,leadcert,suspension)"+
                " VALUES (0,\"Zach\",\"Needham\",\"M\",NULL,1,1,0,NULL)");
    }
}
