import java.sql.*;
import java.util.Map; 
import java.util.HashMap;

public class connectionDB
{
    private Connection con;
    public connectionDB()
    {
        this.con = null;
        try {
            Class.forName("org.sqlite.JDBC");       
            this.con = DriverManager.getConnection("jdbc:sqlite:db/serverRMI");
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Opened database successfully");
    }

    private int checkExistsDescription(String description){
        String query = "SELECT u.KEY FROM UPLOAD_DATA u WHERE u.DESCRIPTION = '"+description+"'";
        try {
            ResultSet executeQuery = this.con.createStatement().executeQuery(query);
            
            while (executeQuery.next()) {
                return executeQuery.getInt("KEY");
            }
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return 0;
    }

    public String getDescriptionFromKey(int key){
        String query = "SELECT u.DESCRIPTION FROM UPLOAD_DATA u WHERE u.KEY = "+key;
        try {
            ResultSet executeQuery = this.con.createStatement().executeQuery(query);
            while (executeQuery.next()) {
                return executeQuery.getString("DESCRIPTION");
            }
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return null;
    }

    public int setDescription(String description){
        try{
            if (checkExistsDescription(description) == 0){
                String query = "INSERT INTO UPLOAD_DATA ('DESCRIPTION') VALUES ('"+description+"')";
                this.con.createStatement().executeUpdate(query);
                return checkExistsDescription(description);
            }
        } catch (Exception e){
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    return 0;
    }

	public Map getContents(String query1){ 
	    String query = "SELECT * FROM UPLOAD_DATA u " + query1; 
	    Map<Integer,String> content = new HashMap<Integer,String>(); 
	    try{ 
		    ResultSet executeQuery = this.con.createStatement().executeQuery(query); 
		    while (executeQuery.next()) { 
			    content.put(executeQuery.getInt("KEY"), executeQuery.getString("DESCRIPTION")); 
		    } 
	    } catch(Exception e){ 
		    System.err.println(e.getClass().getName() + ": " + e.getMessage());
	    } 
	    return content; 
    } 

	public Map getContentsDescription(String match){ 
	    String query = "WHERE u.DESCRIPTION = '"+match+"'"; 
	    return getContents(query); 
    }

    public Map getContentsValue(String match){ 
	    String query = "WHERE u.DESCRIPTION LIKE '%"+match+"%'"; 
	    return getContents(query); 
    }
}
