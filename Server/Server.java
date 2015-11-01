import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Map; 
import java.util.HashMap;
import java.util.Vector;

public class Server implements systemFunctions{
	private connectionDB con;
	private static Vector callbackObjects;

    public Server() throws RemoteException{	
		this.con = new connectionDB();
		this.callbackObjects = new Vector();
	}

    public String sayHello(){
        return "Hello, world!";
    }

	public int setDescription(String description) throws RemoteException{
		int i = this.con.setDescription(description);
		this.callback(i, description);
		return i;

	}

    public String getDescriptionFromKey(int key) throws RemoteException{
		return this.con.getDescriptionFromKey(key);
	}

	public Map getContents() throws RemoteException{
		return this.con.getContents("");
	}

	public Map getContentsValue(String match) throws RemoteException{
		if(match.length() == 0){
			return new HashMap();
		} else {
			return this.con.getContentsValue(match);
		}
	}

	public Map getContentsDescription(String match) throws RemoteException{
		return this.con.getContentsDescription(match);
	}

	public void addCallback(CallbackClientInterface callbackObject) throws RemoteException{
		this.callbackObjects.addElement(callbackObject);
	
	}

	private static void callback(int i, String description) throws RemoteException{
		for(int x = 0; x < callbackObjects.size(); x++){
			CallbackClientInterface client = (CallbackClientInterface) callbackObjects.elementAt(x);
			client.callMe("Description added: "+description+", key: "+i);
		}
	}	



    private static Registry startRegistry(int RMIPortNum) throws RemoteException{
		Registry registry;
		try{
            registry = LocateRegistry.getRegistry(RMIPortNum);
            registry.list();
            System.out.println("RMI registry running at port " + RMIPortNum);
        } catch (RemoteException ex){ 
            System.out.println("RMI registry cannot be located at port " + RMIPortNum);
            registry = LocateRegistry.createRegistry(RMIPortNum);
            System.out.println("RMI registry created at port " + RMIPortNum);
        }
		return registry;
    }

    public static void main(String args[]) throws RemoteException{
	try{
	if (args.length != 2){
		System.out.println("java -classpath \".:sqlite-jdbc-3.7.2.jar\" -Djava.security.policy=rmi.policy Server <localhost> <port>");
		System.exit(0);
	}
	int port = Integer.parseInt(args[1]);
	String host = args[0];
		 System.out.println("Host " + host);
	    System.setProperty("java.rmi.server.hostname", host);
            Registry registry = startRegistry(port);
            Server obj = new Server();
            systemFunctions stub = (systemFunctions) UnicastRemoteObject.exportObject(obj, 0);
			
            // Bind the remote object's stub in the registry
            registry.bind("systemFunctions", stub);
            System.err.println("Server ready");
        } catch (Exception e){
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }
}

