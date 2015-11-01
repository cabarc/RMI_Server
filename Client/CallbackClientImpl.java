import java.rmi.*;	
import java.rmi.server.*;

public class CallbackClientImpl extends UnicastRemoteObject implements CallbackClientInterface{
	public CallbackClientImpl() throws RemoteException{
		super();
	}	
	
	public void callMe(String message) throws RemoteException{
		System.out.println("_____________________________________________________________________");
		System.out.println("NEW CONTENT AVAILABLE:");
		System.out.println(message);
		System.out.println("_____________________________________________________________________");
	}
}
