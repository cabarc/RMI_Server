import java.rmi.RemoteException;
import java.rmi.Remote;

public interface CallbackClientInterface extends Remote{
	public void callMe(String message) throws RemoteException;
}
